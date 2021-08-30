import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CommunityNotificationsComponent } from '../list/community-notifications.component';
import { CommunityNotificationsDetailComponent } from '../detail/community-notifications-detail.component';
import { CommunityNotificationsUpdateComponent } from '../update/community-notifications-update.component';
import { CommunityNotificationsRoutingResolveService } from './community-notifications-routing-resolve.service';

const communityNotificationsRoute: Routes = [
  {
    path: '',
    component: CommunityNotificationsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommunityNotificationsDetailComponent,
    resolve: {
      communityNotifications: CommunityNotificationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommunityNotificationsUpdateComponent,
    resolve: {
      communityNotifications: CommunityNotificationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommunityNotificationsUpdateComponent,
    resolve: {
      communityNotifications: CommunityNotificationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(communityNotificationsRoute)],
  exports: [RouterModule],
})
export class CommunityNotificationsRoutingModule {}
