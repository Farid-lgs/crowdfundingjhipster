import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IContribution, Contribution } from '../contribution.model';
import { ContributionService } from '../service/contribution.service';
import { IContributionNotifications } from 'app/entities/contribution-notifications/contribution-notifications.model';
import { ContributionNotificationsService } from 'app/entities/contribution-notifications/service/contribution-notifications.service';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

@Component({
  selector: 'jhi-contribution-update',
  templateUrl: './contribution-update.component.html',
})
export class ContributionUpdateComponent implements OnInit {
  isSaving = false;

  contributionNotificationsCollection: IContributionNotifications[] = [];
  userInfosSharedCollection: IUserInfos[] = [];
  projectsSharedCollection: IProject[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required]],
    payerName: [null, [Validators.required]],
    createdAt: [],
    updatedAt: [],
    anonymous: [null, [Validators.required]],
    rewarded: [null, [Validators.required]],
    contributionNotifications: [],
    userInfos: [],
    project: [],
  });

  constructor(
    protected contributionService: ContributionService,
    protected contributionNotificationsService: ContributionNotificationsService,
    protected userInfosService: UserInfosService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contribution }) => {
      if (contribution.id === undefined) {
        const today = dayjs().startOf('day');
        contribution.createdAt = today;
        contribution.updatedAt = today;
      }

      this.updateForm(contribution);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contribution = this.createFromForm();
    if (contribution.id !== undefined) {
      this.subscribeToSaveResponse(this.contributionService.update(contribution));
    } else {
      this.subscribeToSaveResponse(this.contributionService.create(contribution));
    }
  }

  trackContributionNotificationsById(index: number, item: IContributionNotifications): number {
    return item.id!;
  }

  trackUserInfosById(index: number, item: IUserInfos): number {
    return item.id!;
  }

  trackProjectById(index: number, item: IProject): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContribution>>): void {
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

  protected updateForm(contribution: IContribution): void {
    this.editForm.patchValue({
      id: contribution.id,
      amount: contribution.amount,
      payerName: contribution.payerName,
      createdAt: contribution.createdAt ? contribution.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: contribution.updatedAt ? contribution.updatedAt.format(DATE_TIME_FORMAT) : null,
      anonymous: contribution.anonymous,
      rewarded: contribution.rewarded,
      contributionNotifications: contribution.contributionNotifications,
      userInfos: contribution.userInfos,
      project: contribution.project,
    });

    this.contributionNotificationsCollection = this.contributionNotificationsService.addContributionNotificationsToCollectionIfMissing(
      this.contributionNotificationsCollection,
      contribution.contributionNotifications
    );
    this.userInfosSharedCollection = this.userInfosService.addUserInfosToCollectionIfMissing(
      this.userInfosSharedCollection,
      contribution.userInfos
    );
    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing(
      this.projectsSharedCollection,
      contribution.project
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contributionNotificationsService
      .query({ filter: 'contribution-is-null' })
      .pipe(map((res: HttpResponse<IContributionNotifications[]>) => res.body ?? []))
      .pipe(
        map((contributionNotifications: IContributionNotifications[]) =>
          this.contributionNotificationsService.addContributionNotificationsToCollectionIfMissing(
            contributionNotifications,
            this.editForm.get('contributionNotifications')!.value
          )
        )
      )
      .subscribe(
        (contributionNotifications: IContributionNotifications[]) => (this.contributionNotificationsCollection = contributionNotifications)
      );

    this.userInfosService
      .query()
      .pipe(map((res: HttpResponse<IUserInfos[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfos[]) =>
          this.userInfosService.addUserInfosToCollectionIfMissing(userInfos, this.editForm.get('userInfos')!.value)
        )
      )
      .subscribe((userInfos: IUserInfos[]) => (this.userInfosSharedCollection = userInfos));

    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing(projects, this.editForm.get('project')!.value))
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));
  }

  protected createFromForm(): IContribution {
    return {
      ...new Contribution(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      payerName: this.editForm.get(['payerName'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      anonymous: this.editForm.get(['anonymous'])!.value,
      rewarded: this.editForm.get(['rewarded'])!.value,
      contributionNotifications: this.editForm.get(['contributionNotifications'])!.value,
      userInfos: this.editForm.get(['userInfos'])!.value,
      project: this.editForm.get(['project'])!.value,
    };
  }
}
