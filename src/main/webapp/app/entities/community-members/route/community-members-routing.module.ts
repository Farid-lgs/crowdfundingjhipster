import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CommunityMembersComponent } from '../list/community-members.component';
import { CommunityMembersDetailComponent } from '../detail/community-members-detail.component';
import { CommunityMembersUpdateComponent } from '../update/community-members-update.component';
import { CommunityMembersRoutingResolveService } from './community-members-routing-resolve.service';

const communityMembersRoute: Routes = [
  {
    path: '',
    component: CommunityMembersComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommunityMembersDetailComponent,
    resolve: {
      communityMembers: CommunityMembersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommunityMembersUpdateComponent,
    resolve: {
      communityMembers: CommunityMembersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommunityMembersUpdateComponent,
    resolve: {
      communityMembers: CommunityMembersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(communityMembersRoute)],
  exports: [RouterModule],
})
export class CommunityMembersRoutingModule {}
