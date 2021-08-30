import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectAccountComponent } from '../list/project-account.component';
import { ProjectAccountDetailComponent } from '../detail/project-account-detail.component';
import { ProjectAccountUpdateComponent } from '../update/project-account-update.component';
import { ProjectAccountRoutingResolveService } from './project-account-routing-resolve.service';

const projectAccountRoute: Routes = [
  {
    path: '',
    component: ProjectAccountComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectAccountDetailComponent,
    resolve: {
      projectAccount: ProjectAccountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectAccountUpdateComponent,
    resolve: {
      projectAccount: ProjectAccountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectAccountUpdateComponent,
    resolve: {
      projectAccount: ProjectAccountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectAccountRoute)],
  exports: [RouterModule],
})
export class ProjectAccountRoutingModule {}
