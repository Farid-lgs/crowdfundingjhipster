import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectComponent } from '../list/project.component';
import { ProjectDetailComponent } from '../detail/project-detail.component';
import { ProjectUpdateComponent } from '../update/project-update.component';
import { ProjectRoutingResolveService } from './project-routing-resolve.service';
import {MenuComponent} from "../menu/menu.component";

const projectRoute: Routes = [
  {
    path: '',
    component: ProjectComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'list',
    component: ProjectComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectUpdateComponent,
    resolve: {
      project: ProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id',
    component: MenuComponent,
    resolve: {
      project: ProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: 'comment',
        data: { pageTitle: 'crowdFundingJHipsterApp.projectComment.home.title' },
        loadChildren: () => import('../../project-comment/project-comment.module').then(m => m.ProjectCommentModule),
      },
      {
        path: '',
        component: ProjectDetailComponent,
        resolve: {
          project: ProjectRoutingResolveService,
        },
        canActivate: [UserRouteAccessService],

        loadChildren: () => import('../../reward/reward.module').then(m => m.RewardModule),
      },
    ]
  },
  {
    path: ':id/edit',
    component: ProjectUpdateComponent,
    resolve: {
      project: ProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectRoute)],
  exports: [RouterModule],
})
export class ProjectRoutingModule {}
