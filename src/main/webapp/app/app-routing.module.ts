import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import {UserProfileComponent} from "./account/user-profile/user-profile.component";
import {PasswordComponent} from "./account/password/password.component";
import {CreditCardComponent} from "./entities/credit-card/list/credit-card.component";
import {AddressComponent} from "./entities/address/list/address.component";
import {CommunityComponent} from "./entities/community/list/community.component";
import {CommunityDetailComponent} from "./entities/community/detail/community-detail.component";
import {CommunityUpdateComponent} from "./entities/community/update/community-update.component";
import {ProjectUpdateComponent} from "./entities/project/update/project-update.component";
import {Project} from "./entities/project/project.model";
import {ProjectComponent} from "./entities/project/list/project.component";
import {CreditCardUpdateComponent} from "./entities/credit-card/update/credit-card-update.component";
import {UserInfosDetailComponent} from "./entities/user-infos/detail/user-infos-detail.component";
import {AddressUpdateComponent} from "./entities/address/update/address-update.component";
import {UserManagementDetailComponent} from "./admin/user-management/detail/user-management-detail.component";
import {UserManagementResolve} from "./admin/user-management/user-management.route";
import {UserResolve} from "./account/user-resolve";

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
          // loadChildren: () => import('./account/user-profile/user-profile.module').then(m => m.UserProfileModule),
          // loadChildren: () => import('./account/user-profile/user-profile.route').then(m => m.UserProFileRoute),
          component: UserProfileComponent,
          children: [
            {path: 'details/:login/view', component: UserManagementDetailComponent,
              resolve: {
                user: UserResolve,
              },
            },
            // {path: 'authentication', component: AuthenticationComponent},
            {path: 'password', component: PasswordComponent},
            {path: 'creditCard', component: CreditCardUpdateComponent},
            {path: 'address', component: AddressUpdateComponent},
            {path: 'project', component: ProjectComponent},
            {path: 'community', component: CommunityUpdateComponent},
            // {path: 'communities/subscription', component: Category},
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
