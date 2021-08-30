import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICommunity, Community } from '../community.model';
import { CommunityService } from '../service/community.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICommunityMembers } from 'app/entities/community-members/community-members.model';
import { CommunityMembersService } from 'app/entities/community-members/service/community-members.service';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';

@Component({
  selector: 'jhi-community-update',
  templateUrl: './community-update.component.html',
})
export class CommunityUpdateComponent implements OnInit {
  isSaving = false;

  communityMembersCollection: ICommunityMembers[] = [];
  adminsCollection: IUserInfos[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    nameFr: [],
    createdAt: [],
    about: [null, [Validators.required]],
    coverImage: [],
    coverImageContentType: [],
    communityMembers: [],
    admin: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected communityService: CommunityService,
    protected communityMembersService: CommunityMembersService,
    protected userInfosService: UserInfosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ community }) => {
      if (community.id === undefined) {
        const today = dayjs().startOf('day');
        community.createdAt = today;
      }

      this.updateForm(community);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('crowdFundingJHipsterApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const community = this.createFromForm();
    if (community.id !== undefined) {
      this.subscribeToSaveResponse(this.communityService.update(community));
    } else {
      this.subscribeToSaveResponse(this.communityService.create(community));
    }
  }

  trackCommunityMembersById(index: number, item: ICommunityMembers): number {
    return item.id!;
  }

  trackUserInfosById(index: number, item: IUserInfos): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommunity>>): void {
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

  protected updateForm(community: ICommunity): void {
    this.editForm.patchValue({
      id: community.id,
      name: community.name,
      nameFr: community.nameFr,
      createdAt: community.createdAt ? community.createdAt.format(DATE_TIME_FORMAT) : null,
      about: community.about,
      coverImage: community.coverImage,
      coverImageContentType: community.coverImageContentType,
      communityMembers: community.communityMembers,
      admin: community.admin,
    });

    this.communityMembersCollection = this.communityMembersService.addCommunityMembersToCollectionIfMissing(
      this.communityMembersCollection,
      community.communityMembers
    );
    this.adminsCollection = this.userInfosService.addUserInfosToCollectionIfMissing(this.adminsCollection, community.admin);
  }

  protected loadRelationshipsOptions(): void {
    this.communityMembersService
      .query({ filter: 'community-is-null' })
      .pipe(map((res: HttpResponse<ICommunityMembers[]>) => res.body ?? []))
      .pipe(
        map((communityMembers: ICommunityMembers[]) =>
          this.communityMembersService.addCommunityMembersToCollectionIfMissing(
            communityMembers,
            this.editForm.get('communityMembers')!.value
          )
        )
      )
      .subscribe((communityMembers: ICommunityMembers[]) => (this.communityMembersCollection = communityMembers));

    this.userInfosService
      .query({ filter: 'community-is-null' })
      .pipe(map((res: HttpResponse<IUserInfos[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfos[]) =>
          this.userInfosService.addUserInfosToCollectionIfMissing(userInfos, this.editForm.get('admin')!.value)
        )
      )
      .subscribe((userInfos: IUserInfos[]) => (this.adminsCollection = userInfos));
  }

  protected createFromForm(): ICommunity {
    return {
      ...new Community(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      nameFr: this.editForm.get(['nameFr'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      about: this.editForm.get(['about'])!.value,
      coverImageContentType: this.editForm.get(['coverImageContentType'])!.value,
      coverImage: this.editForm.get(['coverImage'])!.value,
      communityMembers: this.editForm.get(['communityMembers'])!.value,
      admin: this.editForm.get(['admin'])!.value,
    };
  }
}
