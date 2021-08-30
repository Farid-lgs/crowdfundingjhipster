import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContributionNotificationsComponent } from '../list/contribution-notifications.component';
import { ContributionNotificationsDetailComponent } from '../detail/contribution-notifications-detail.component';
import { ContributionNotificationsUpdateComponent } from '../update/contribution-notifications-update.component';
import { ContributionNotificationsRoutingResolveService } from './contribution-notifications-routing-resolve.service';

const contributionNotificationsRoute: Routes = [
  {
    path: '',
    component: ContributionNotificationsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContributionNotificationsDetailComponent,
    resolve: {
      contributionNotifications: ContributionNotificationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContributionNotificationsUpdateComponent,
    resolve: {
      contributionNotifications: ContributionNotificationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContributionNotificationsUpdateComponent,
    resolve: {
      contributionNotifications: ContributionNotificationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contributionNotificationsRoute)],
  exports: [RouterModule],
})
export class ContributionNotificationsRoutingModule {}
