import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RewardComponent } from '../list/reward.component';
import { RewardDetailComponent } from '../detail/reward-detail.component';
import { RewardUpdateComponent } from '../update/reward-update.component';
import { RewardRoutingResolveService } from './reward-routing-resolve.service';

const rewardRoute: Routes = [
  {
    path: '',
    component: RewardComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RewardUpdateComponent,
    resolve: {
      reward: RewardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':rewardId',
    component: RewardDetailComponent,
    resolve: {
      reward: RewardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':rewardId/edit',
    component: RewardUpdateComponent,
    resolve: {
      reward: RewardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rewardRoute)],
  exports: [RouterModule],
})
export class RewardRoutingModule {}
