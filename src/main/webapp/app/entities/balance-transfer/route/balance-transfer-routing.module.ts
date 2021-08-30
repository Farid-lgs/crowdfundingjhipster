import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BalanceTransferComponent } from '../list/balance-transfer.component';
import { BalanceTransferDetailComponent } from '../detail/balance-transfer-detail.component';
import { BalanceTransferUpdateComponent } from '../update/balance-transfer-update.component';
import { BalanceTransferRoutingResolveService } from './balance-transfer-routing-resolve.service';

const balanceTransferRoute: Routes = [
  {
    path: '',
    component: BalanceTransferComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BalanceTransferDetailComponent,
    resolve: {
      balanceTransfer: BalanceTransferRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BalanceTransferUpdateComponent,
    resolve: {
      balanceTransfer: BalanceTransferRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BalanceTransferUpdateComponent,
    resolve: {
      balanceTransfer: BalanceTransferRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(balanceTransferRoute)],
  exports: [RouterModule],
})
export class BalanceTransferRoutingModule {}
