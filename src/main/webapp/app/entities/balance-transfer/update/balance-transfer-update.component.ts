import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBalanceTransfer, BalanceTransfer } from '../balance-transfer.model';
import { BalanceTransferService } from '../service/balance-transfer.service';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

@Component({
  selector: 'jhi-balance-transfer-update',
  templateUrl: './balance-transfer-update.component.html',
})
export class BalanceTransferUpdateComponent implements OnInit {
  isSaving = false;

  userInfosCollection: IUserInfos[] = [];
  projectsCollection: IProject[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required]],
    userInfos: [],
    project: [],
  });

  constructor(
    protected balanceTransferService: BalanceTransferService,
    protected userInfosService: UserInfosService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ balanceTransfer }) => {
      this.updateForm(balanceTransfer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const balanceTransfer = this.createFromForm();
    if (balanceTransfer.id !== undefined) {
      this.subscribeToSaveResponse(this.balanceTransferService.update(balanceTransfer));
    } else {
      this.subscribeToSaveResponse(this.balanceTransferService.create(balanceTransfer));
    }
  }

  trackUserInfosById(index: number, item: IUserInfos): number {
    return item.id!;
  }

  trackProjectById(index: number, item: IProject): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBalanceTransfer>>): void {
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

  protected updateForm(balanceTransfer: IBalanceTransfer): void {
    this.editForm.patchValue({
      id: balanceTransfer.id,
      amount: balanceTransfer.amount,
      userInfos: balanceTransfer.userInfos,
      project: balanceTransfer.project,
    });

    this.userInfosCollection = this.userInfosService.addUserInfosToCollectionIfMissing(this.userInfosCollection, balanceTransfer.userInfos);
    this.projectsCollection = this.projectService.addProjectToCollectionIfMissing(this.projectsCollection, balanceTransfer.project);
  }

  protected loadRelationshipsOptions(): void {
    this.userInfosService
      .query({ filter: 'balancetransfer-is-null' })
      .pipe(map((res: HttpResponse<IUserInfos[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfos[]) =>
          this.userInfosService.addUserInfosToCollectionIfMissing(userInfos, this.editForm.get('userInfos')!.value)
        )
      )
      .subscribe((userInfos: IUserInfos[]) => (this.userInfosCollection = userInfos));

    this.projectService
      .query({ filter: 'balancetransfer-is-null' })
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing(projects, this.editForm.get('project')!.value))
      )
      .subscribe((projects: IProject[]) => (this.projectsCollection = projects));
  }

  protected createFromForm(): IBalanceTransfer {
    return {
      ...new BalanceTransfer(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      userInfos: this.editForm.get(['userInfos'])!.value,
      project: this.editForm.get(['project'])!.value,
    };
  }
}
