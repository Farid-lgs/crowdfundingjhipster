import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProject } from '../project.model';
import { DataUtils } from 'app/core/util/data-util.service';
import {CreatorService} from "../../../shared/service/creator.service";
import {ProjectService} from "../service/project.service";

@Component({
  selector: 'jhi-project-detail',
  templateUrl: './project-detail.component.html',
})
export class ProjectDetailComponent implements OnInit {
  project: IProject | null = null;
  creator = false;
  amount = 0;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute, protected creatorService: CreatorService, protected projectService: ProjectService) {}

  ngOnInit(): void {
    this.amount = this.projectService.amount;
    this.activatedRoute.data.subscribe(({ project }) => {
      this.project = project;

      this.creator = this.creatorService.isCreator();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
