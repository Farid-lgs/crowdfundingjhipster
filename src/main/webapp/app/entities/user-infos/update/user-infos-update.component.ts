import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IUserInfos, UserInfos } from '../user-infos.model';
import { UserInfosService } from '../service/user-infos.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import {IUser, User} from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ICommunityMembers } from 'app/entities/community-members/community-members.model';
import { CommunityMembersService } from 'app/entities/community-members/service/community-members.service';

@Component({
  selector: 'jhi-user-infos-update',
  templateUrl: './user-infos-update.component.html',
})
export class UserInfosUpdateComponent implements OnInit {
  isSaving = false;
  user: IUser | undefined;
  usersSharedCollection: IUser[] = [];
  communityMembersSharedCollection: ICommunityMembers[] = [];

  editForm = this.fb.group({
    id: [],
    publicName: [null, [Validators.required]],
    birthDate: [],
    twitter: [],
    facebook: [],
    linkedIn: [],
    description: [],
    coverImage: [],
    coverImageContentType: [],
    commonId: [],
    user: [],
    communityMembers: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected userInfosService: UserInfosService,
    protected userService: UserService,
    protected communityMembersService: CommunityMembersService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userInfos }) => {
      this.user = new User(userInfos.user?.id)
      this.updateForm(userInfos);

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
    const userInfos = this.createFromForm();
    if (userInfos.id !== undefined) {
      this.subscribeToSaveResponse(this.userInfosService.update(userInfos));
    } else {
      this.subscribeToSaveResponse(this.userInfosService.create(userInfos));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  trackCommunityMembersById(index: number, item: ICommunityMembers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserInfos>>): void {
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

  protected updateForm(userInfos: IUserInfos): void {
    this.editForm.patchValue({
      id: userInfos.id,
      publicName: userInfos.publicName,
      birthDate: userInfos.birthDate,
      twitter: userInfos.twitter,
      facebook: userInfos.facebook,
      linkedIn: userInfos.linkedIn,
      description: userInfos.description,
      coverImage: userInfos.coverImage,
      coverImageContentType: userInfos.coverImageContentType,
      commonId: userInfos.commonId,
      user: userInfos.user,
      communityMembers: userInfos.communityMembers,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, userInfos.user);
    this.communityMembersSharedCollection = this.communityMembersService.addCommunityMembersToCollectionIfMissing(
      this.communityMembersSharedCollection,
      userInfos.communityMembers
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.communityMembersService
      .query()
      .pipe(map((res: HttpResponse<ICommunityMembers[]>) => res.body ?? []))
      .pipe(
        map((communityMembers: ICommunityMembers[]) =>
          this.communityMembersService.addCommunityMembersToCollectionIfMissing(
            communityMembers,
            this.editForm.get('communityMembers')!.value
          )
        )
      )
      .subscribe((communityMembers: ICommunityMembers[]) => (this.communityMembersSharedCollection = communityMembers));
  }

  protected createFromForm(): IUserInfos {
    return {
      ...new UserInfos(),
      id: this.editForm.get(['id'])!.value,
      publicName: this.editForm.get(['publicName'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      twitter: this.editForm.get(['twitter'])!.value,
      facebook: this.editForm.get(['facebook'])!.value,
      linkedIn: this.editForm.get(['linkedIn'])!.value,
      description: this.editForm.get(['description'])!.value,
      coverImageContentType: this.editForm.get(['coverImageContentType'])!.value,
      coverImage: this.editForm.get(['coverImage'])!.value,
      commonId: this.editForm.get(['commonId'])!.value,
      user: this.editForm.get(['user'])!.value,
      communityMembers: this.editForm.get(['communityMembers'])!.value,
    };
  }
}
