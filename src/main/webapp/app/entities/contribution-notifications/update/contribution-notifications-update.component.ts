import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IContributionNotifications, ContributionNotifications } from '../contribution-notifications.model';
import { ContributionNotificationsService } from '../service/contribution-notifications.service';

@Component({
  selector: 'jhi-contribution-notifications-update',
  templateUrl: './contribution-notifications-update.component.html',
})
export class ContributionNotificationsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    content: [],
    sentAt: [],
  });

  constructor(
    protected contributionNotificationsService: ContributionNotificationsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contributionNotifications }) => {
      if (contributionNotifications.id === undefined) {
        const today = dayjs().startOf('day');
        contributionNotifications.sentAt = today;
      }

      this.updateForm(contributionNotifications);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contributionNotifications = this.createFromForm();
    if (contributionNotifications.id !== undefined) {
      this.subscribeToSaveResponse(this.contributionNotificationsService.update(contributionNotifications));
    } else {
      this.subscribeToSaveResponse(this.contributionNotificationsService.create(contributionNotifications));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContributionNotifications>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(contributionNotifications: IContributionNotifications): void {
    this.editForm.patchValue({
      id: contributionNotifications.id,
      content: contributionNotifications.content,
      sentAt: contributionNotifications.sentAt ? contributionNotifications.sentAt.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IContributionNotifications {
    return {
      ...new ContributionNotifications(),
      id: this.editForm.get(['id'])!.value,
      content: this.editForm.get(['content'])!.value,
      sentAt: this.editForm.get(['sentAt'])!.value ? dayjs(this.editForm.get(['sentAt'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
