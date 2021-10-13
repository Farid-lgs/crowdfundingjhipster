import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import {UserProfileComponent} from "./account/user-profile/user-profile.component";
import {CommunityUpdateComponent} from "./entities/community/update/community-update.component";
import {ProjectComponent} from "./entities/project/list/project.component";
import {CreditCardUpdateComponent} from "./entities/credit-card/update/credit-card-update.component";
import {AddressUpdateComponent} from "./entities/address/update/address-update.component";
import {UserManagementDetailComponent} from "./admin/user-management/detail/user-management-detail.component";
import {UserManagementResolve} from "./admin/user-management/user-management.route";
import {UserResolve} from "./account/user-resolve";
import {AddressRoutingResolveService} from "./entities/address/route/address-routing-resolve.service";
import {CreditCardRoutingResolveService} from "./entities/credit-card/route/credit-card-routing-resolve.service";
import {UserInfosUpdateComponent} from "./entities/user-infos/update/user-infos-update.component";
import {UserInfosRoutingResolveService} from "./entities/user-infos/route/user-infos-routing-resolve.service";

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'profile',
          component: UserProfileComponent,
          resolve: {
            user: UserManagementResolve,
          },
          children: [
            {path: 'creditCard',
              canActivate: [UserRouteAccessService],
              children: [
                {path : ':userId/new', component: CreditCardUpdateComponent,},
                {path : ':id/edit', component: CreditCardUpdateComponent,
                  resolve: {
                    creditCard: CreditCardRoutingResolveService,
                  },
                }
              ]
            },
            {path: 'address/:userId/new', component: AddressUpdateComponent,
              resolve: {
                address: AddressRoutingResolveService,
              },
              canActivate: [UserRouteAccessService],
            },
            {path: 'address/:id/edit', component: AddressUpdateComponent,
              resolve: {
                address: AddressRoutingResolveService,
              },
              canActivate: [UserRouteAccessService],
            },
            {path: 'project/:userId', component: ProjectComponent,
              data: {
                defaultSort: 'id,asc',
              },
              canActivate: [UserRouteAccessService],
            },
            {path: 'community', component: CommunityUpdateComponent},
            {path: ':login', component: UserManagementDetailComponent,
              resolve: {
                user: UserResolve,
              }
            },
            {
              path: ':id/edit', component: UserInfosUpdateComponent,
              resolve: {
                userInfos: UserInfosRoutingResolveService,
              },
              canActivate: [UserRouteAccessService],
            },
          ]
        },

        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        ...LAYOUT_ROUTES,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
