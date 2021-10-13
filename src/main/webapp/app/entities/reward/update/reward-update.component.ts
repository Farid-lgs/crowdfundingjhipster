import {Component, OnInit, ElementRef, Input} from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IReward, Reward } from '../reward.model';
import { RewardService } from '../service/reward.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import {IProject, Project} from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

@Component({
  selector: 'jhi-reward-update',
  templateUrl: './reward-update.component.html',
})
export class RewardUpdateComponent implements OnInit {
  isSaving = false;
  id: number | null = null;
  project: IProject = new Project();

  projectsSharedCollection: IProject[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    description: [null, [Validators.required]],
    minimumValue: [null, [Validators.required]],
    deliverAt: [],
    expiresAt: [],
    maximumContributions: [],
    image: [],
    imageContentType: [],
    project: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected rewardService: RewardService,
    protected projectService: ProjectService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    // Récupère l'url + split la chaine et retourne le 3è fragment (équivalent au projectId)
    const projectId = Number(this.router.url.split('/')[2]);

    this.activatedRoute.data.subscribe(({ reward }) => {
      if(projectId > 0) {
        this.project = new Project(projectId);

        reward.project = this.project;
      }

      if (reward.id === undefined) {
        const today = dayjs().startOf('day');
        reward.deliverAt = today;
        reward.expiresAt = today;
      }

      this.updateForm(reward);

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
    const reward = this.createFromForm();

    if (reward.id !== undefined) {
      this.subscribeToSaveResponse(this.rewardService.update(reward));
    } else {
      this.subscribeToSaveResponse(this.rewardService.create(reward));
    }
  }

  trackProjectById(index: number, item: IProject): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReward>>): void {
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

  protected updateForm(reward: IReward): void {
    this.editForm.patchValue({
      id: reward.id,
      title: reward.title,
      description: reward.description,
      minimumValue: reward.minimumValue,
      deliverAt: reward.deliverAt ? reward.deliverAt.format(DATE_TIME_FORMAT) : null,
      expiresAt: reward.expiresAt ? reward.expiresAt.format(DATE_TIME_FORMAT) : null,
      maximumContributions: reward.maximumContributions,
      image: reward.image,
      imageContentType: reward.imageContentType,
      project: reward.project,
    });

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing(this.projectsSharedCollection, reward.project);
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

  protected createFromForm(): IReward {
    return {
      ...new Reward(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      minimumValue: this.editForm.get(['minimumValue'])!.value,
      deliverAt: this.editForm.get(['deliverAt'])!.value ? dayjs(this.editForm.get(['deliverAt'])!.value, DATE_TIME_FORMAT) : undefined,
      expiresAt: this.editForm.get(['expiresAt'])!.value ? dayjs(this.editForm.get(['expiresAt'])!.value, DATE_TIME_FORMAT) : undefined,
      maximumContributions: this.editForm.get(['maximumContributions'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      project: this.editForm.get(['project'])!.value,
    };
  }
}
