import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectImages } from '../project-images.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-project-images-detail',
  templateUrl: './project-images-detail.component.html',
})
export class ProjectImagesDetailComponent implements OnInit {
  projectImages: IProjectImages | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectImages }) => {
      this.projectImages = projectImages;
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
