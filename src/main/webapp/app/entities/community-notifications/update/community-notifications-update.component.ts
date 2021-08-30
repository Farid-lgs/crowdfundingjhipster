import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICommunityNotifications, CommunityNotifications } from '../community-notifications.model';
import { CommunityNotificationsService } from '../service/community-notifications.service';
import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';

@Component({
  selector: 'jhi-community-notifications-update',
  templateUrl: './community-notifications-update.component.html',
})
export class CommunityNotificationsUpdateComponent implements OnInit {
  isSaving = false;

  communitiesSharedCollection: ICommunity[] = [];

  editForm = this.fb.group({
    id: [],
    content: [],
    sentAt: [],
    community: [],
  });

  constructor(
    protected communityNotificationsService: CommunityNotificationsService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ communityNotifications }) => {
      if (communityNotifications.id === undefined) {
        const today = dayjs().startOf('day');
        communityNotifications.sentAt = today;
      }

      this.updateForm(communityNotifications);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const communityNotifications = this.createFromForm();
    if (communityNotifications.id !== undefined) {
      this.subscribeToSaveResponse(this.communityNotificationsService.update(communityNotifications));
    } else {
      this.subscribeToSaveResponse(this.communityNotificationsService.create(communityNotifications));
    }
  }

  trackCommunityById(index: number, item: ICommunity): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommunityNotifications>>): void {
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

  protected updateForm(communityNotifications: ICommunityNotifications): void {
    this.editForm.patchValue({
      id: communityNotifications.id,
      content: communityNotifications.content,
      sentAt: communityNotifications.sentAt ? communityNotifications.sentAt.format(DATE_TIME_FORMAT) : null,
      community: communityNotifications.community,
    });

    this.communitiesSharedCollection = this.communityService.addCommunityToCollectionIfMissing(
      this.communitiesSharedCollection,
      communityNotifications.community
    );
  }

  protected loadRelationshipsOptions(): void {
    this.communityService
      .query()
      .pipe(map((res: HttpResponse<ICommunity[]>) => res.body ?? []))
      .pipe(
        map((communities: ICommunity[]) =>
          this.communityService.addCommunityToCollectionIfMissing(communities, this.editForm.get('community')!.value)
        )
      )
      .subscribe((communities: ICommunity[]) => (this.communitiesSharedCollection = communities));
  }

  protected createFromForm(): ICommunityNotifications {
    return {
      ...new CommunityNotifications(),
      id: this.editForm.get(['id'])!.value,
      content: this.editForm.get(['content'])!.value,
      sentAt: this.editForm.get(['sentAt'])!.value ? dayjs(this.editForm.get(['sentAt'])!.value, DATE_TIME_FORMAT) : undefined,
      community: this.editForm.get(['community'])!.value,
    };
  }
}
