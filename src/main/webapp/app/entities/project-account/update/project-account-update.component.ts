import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProjectAccount, ProjectAccount } from '../project-account.model';
import { ProjectAccountService } from '../service/project-account.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

@Component({
  selector: 'jhi-project-account-update',
  templateUrl: './project-account-update.component.html',
})
export class ProjectAccountUpdateComponent implements OnInit {
  isSaving = false;

  projectsSharedCollection: IProject[] = [];

  editForm = this.fb.group({
    id: [],
    balance: [],
    number: [],
    bank: [],
    project: [],
  });

  constructor(
    protected projectAccountService: ProjectAccountService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectAccount }) => {
      this.updateForm(projectAccount);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectAccount = this.createFromForm();
    if (projectAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.projectAccountService.update(projectAccount));
    } else {
      this.subscribeToSaveResponse(this.projectAccountService.create(projectAccount));
    }
  }

  trackProjectById(index: number, item: IProject): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectAccount>>): void {
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

  protected updateForm(projectAccount: IProjectAccount): void {
    this.editForm.patchValue({
      id: projectAccount.id,
      balance: projectAccount.balance,
      number: projectAccount.number,
      bank: projectAccount.bank,
      project: projectAccount.project,
    });

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing(
      this.projectsSharedCollection,
      projectAccount.project
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing(projects, this.editForm.get('project')!.value))
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));
  }

  protected createFromForm(): IProjectAccount {
    return {
      ...new ProjectAccount(),
      id: this.editForm.get(['id'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      number: this.editForm.get(['number'])!.value,
      bank: this.editForm.get(['bank'])!.value,
      project: this.editForm.get(['project'])!.value,
    };
  }
}
