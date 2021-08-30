import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectCommentComponent } from './list/project-comment.component';
import { ProjectCommentDetailComponent } from './detail/project-comment-detail.component';
import { ProjectCommentUpdateComponent } from './update/project-comment-update.component';
import { ProjectCommentDeleteDialogComponent } from './delete/project-comment-delete-dialog.component';
import { ProjectCommentRoutingModule } from './route/project-comment-routing.module';

@NgModule({
  imports: [SharedModule, ProjectCommentRoutingModule],
  declarations: [
    ProjectCommentComponent,
    ProjectCommentDetailComponent,
    ProjectCommentUpdateComponent,
    ProjectCommentDeleteDialogComponent,
  ],
  entryComponents: [ProjectCommentDeleteDialogComponent],
})
export class ProjectCommentModule {}
