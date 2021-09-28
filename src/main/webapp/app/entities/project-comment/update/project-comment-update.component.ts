import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProjectComment, ProjectComment } from '../project-comment.model';
import { ProjectCommentService } from '../service/project-comment.service';
import {IUserInfos, UserInfos} from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import {IProject, Project} from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import {CreatorService} from "../../../shared/service/creator.service";

@Component({
  selector: 'jhi-project-comment-update',
  templateUrl: './project-comment-update.component.html',
})
export class ProjectCommentUpdateComponent implements OnInit {
  isSaving = false;
  project: IProject = new Project();
  userInfos: IUserInfos = new UserInfos();

  userInfosSharedCollection: IUserInfos[] = [];
  projectsSharedCollection: IProject[] = [];

  editForm = this.fb.group({
    id: [],
    comment: [null, [Validators.minLength(5), Validators.maxLength(255), Validators.required]],
    userInfos: [],
    project: [],
  });

  constructor(
    protected projectCommentService: ProjectCommentService,
    protected userInfosService: UserInfosService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected creatorService: CreatorService,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.creatorService.idUser();
    const userId = this.creatorService.id;
    // Récupère l'url + split la chaine et retourne le 3è fragment (équivalent au projectId)
    const projectId = Number(this.router.url.split('/')[2]);

    this.activatedRoute.data.subscribe(({ projectComment }) => {
      if(projectId > 0) {
        this.project = new Project(projectId);
        this.userInfos = new UserInfos(userId)

        console.log(userId);

        projectComment.project = this.project;
        projectComment.userInfos = this.userInfos;
      }

      this.updateForm(projectComment);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectComment = this.createFromForm();
    if (projectComment.id !== undefined) {
      this.subscribeToSaveResponse(this.projectCommentService.update(projectComment));
    } else {
      this.subscribeToSaveResponse(this.projectCommentService.create(projectComment));
    }
  }

  trackUserInfosById(index: number, item: IUserInfos): number {
    return item.id!;
  }

  trackProjectById(index: number, item: IProject): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectComment>>): void {
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

  protected updateForm(projectComment: IProjectComment): void {

    this.editForm.patchValue({
      id: projectComment.id,
      comment: projectComment.comment,
      userInfos: projectComment.userInfos,
      project: projectComment.project,
    });

    this.userInfosSharedCollection = this.userInfosService.addUserInfosToCollectionIfMissing(
      this.userInfosSharedCollection,
      projectComment.userInfos
    );
    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing(
      this.projectsSharedCollection,
      projectComment.project
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IProjectComment {
    return {
      ...new ProjectComment(),
      id: this.editForm.get(['id'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      userInfos: this.editForm.get(['userInfos'])!.value,
      project: this.editForm.get(['project'])!.value,
    };
  }
}
