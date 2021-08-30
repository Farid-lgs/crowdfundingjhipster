import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectCommentComponent } from '../list/project-comment.component';
import { ProjectCommentDetailComponent } from '../detail/project-comment-detail.component';
import { ProjectCommentUpdateComponent } from '../update/project-comment-update.component';
import { ProjectCommentRoutingResolveService } from './project-comment-routing-resolve.service';

const projectCommentRoute: Routes = [
  {
    path: '',
    component: ProjectCommentComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectCommentDetailComponent,
    resolve: {
      projectComment: ProjectCommentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectCommentUpdateComponent,
    resolve: {
      projectComment: ProjectCommentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectCommentUpdateComponent,
    resolve: {
      projectComment: ProjectCommentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectCommentRoute)],
  exports: [RouterModule],
})
export class ProjectCommentRoutingModule {}
