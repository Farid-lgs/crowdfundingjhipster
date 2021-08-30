import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectImagesComponent } from '../list/project-images.component';
import { ProjectImagesDetailComponent } from '../detail/project-images-detail.component';
import { ProjectImagesUpdateComponent } from '../update/project-images-update.component';
import { ProjectImagesRoutingResolveService } from './project-images-routing-resolve.service';

const projectImagesRoute: Routes = [
  {
    path: '',
    component: ProjectImagesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectImagesDetailComponent,
    resolve: {
      projectImages: ProjectImagesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectImagesUpdateComponent,
    resolve: {
      projectImages: ProjectImagesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectImagesUpdateComponent,
    resolve: {
      projectImages: ProjectImagesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectImagesRoute)],
  exports: [RouterModule],
})
export class ProjectImagesRoutingModule {}
