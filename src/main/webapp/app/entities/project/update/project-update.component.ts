import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProject, Project } from '../project.model';
import { ProjectService } from '../service/project.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html',
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;

  communitiesSharedCollection: ICommunity[] = [];
  userInfosSharedCollection: IUserInfos[] = [];
  categoriesSharedCollection: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    goal: [null, [Validators.required]],
    headline: [null, [Validators.required]],
    videoUrl: [],
    location: [],
    createdAt: [],
    updatedAt: [],
    description: [],
    moreLinks: [],
    budgetDescription: [],
    duration: [null, [Validators.required]],
    adminNotes: [],
    coverImage: [],
    coverImageContentType: [],
    status: [],
    commonId: [],
    community: [],
    userInfos: [],
    category: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected projectService: ProjectService,
    protected communityService: CommunityService,
    protected userInfosService: UserInfosService,
    protected categoryService: CategoryService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      if (project.id === undefined) {
        const today = dayjs().startOf('day');
        project.createdAt = today;
        project.updatedAt = today;
      }

      this.updateForm(project);

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
    const project = this.createFromForm();
    if (project.id !== undefined) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  trackCommunityById(index: number, item: ICommunity): number {
    return item.id!;
  }

  trackUserInfosById(index: number, item: IUserInfos): number {
    return item.id!;
  }

  trackCategoryById(index: number, item: ICategory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>): void {
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

  protected updateForm(project: IProject): void {
    this.editForm.patchValue({
      id: project.id,
      title: project.title,
      goal: project.goal,
      headline: project.headline,
      videoUrl: project.videoUrl,
      location: project.location,
      createdAt: project.createdAt ? project.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: project.updatedAt ? project.updatedAt.format(DATE_TIME_FORMAT) : null,
      description: project.description,
      moreLinks: project.moreLinks,
      budgetDescription: project.budgetDescription,
      duration: project.duration,
      adminNotes: project.adminNotes,
      coverImage: project.coverImage,
      coverImageContentType: project.coverImageContentType,
      status: project.status,
      commonId: project.commonId,
      community: project.community,
      userInfos: project.userInfos,
      category: project.category,
    });

    this.communitiesSharedCollection = this.communityService.addCommunityToCollectionIfMissing(
      this.communitiesSharedCollection,
      project.community
    );
    this.userInfosSharedCollection = this.userInfosService.addUserInfosToCollectionIfMissing(
      this.userInfosSharedCollection,
      project.userInfos
    );
    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing(
      this.categoriesSharedCollection,
      project.category
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

    this.userInfosService
      .query()
      .pipe(map((res: HttpResponse<IUserInfos[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfos[]) =>
          this.userInfosService.addUserInfosToCollectionIfMissing(userInfos, this.editForm.get('userInfos')!.value)
        )
      )
      .subscribe((userInfos: IUserInfos[]) => (this.userInfosSharedCollection = userInfos));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing(categories, this.editForm.get('category')!.value)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));
  }

  protected createFromForm(): IProject {
    return {
      ...new Project(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      goal: this.editForm.get(['goal'])!.value,
      headline: this.editForm.get(['headline'])!.value,
      videoUrl: this.editForm.get(['videoUrl'])!.value,
      location: this.editForm.get(['location'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description'])!.value,
      moreLinks: this.editForm.get(['moreLinks'])!.value,
      budgetDescription: this.editForm.get(['budgetDescription'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      adminNotes: this.editForm.get(['adminNotes'])!.value,
      coverImageContentType: this.editForm.get(['coverImageContentType'])!.value,
      coverImage: this.editForm.get(['coverImage'])!.value,
      status: this.editForm.get(['status'])!.value,
      commonId: this.editForm.get(['commonId'])!.value,
      community: this.editForm.get(['community'])!.value,
      userInfos: this.editForm.get(['userInfos'])!.value,
      category: this.editForm.get(['category'])!.value,
    };
  }
}
