import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectImagesComponent } from './list/project-images.component';
import { ProjectImagesDetailComponent } from './detail/project-images-detail.component';
import { ProjectImagesUpdateComponent } from './update/project-images-update.component';
import { ProjectImagesDeleteDialogComponent } from './delete/project-images-delete-dialog.component';
import { ProjectImagesRoutingModule } from './route/project-images-routing.module';

@NgModule({
  imports: [SharedModule, ProjectImagesRoutingModule],
  declarations: [ProjectImagesComponent, ProjectImagesDetailComponent, ProjectImagesUpdateComponent, ProjectImagesDeleteDialogComponent],
  entryComponents: [ProjectImagesDeleteDialogComponent],
})
export class ProjectImagesModule {}
