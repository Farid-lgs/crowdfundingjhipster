import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BalanceTransferComponent } from './list/balance-transfer.component';
import { BalanceTransferDetailComponent } from './detail/balance-transfer-detail.component';
import { BalanceTransferUpdateComponent } from './update/balance-transfer-update.component';
import { BalanceTransferDeleteDialogComponent } from './delete/balance-transfer-delete-dialog.component';
import { BalanceTransferRoutingModule } from './route/balance-transfer-routing.module';

@NgModule({
  imports: [SharedModule, BalanceTransferRoutingModule],
  declarations: [
    BalanceTransferComponent,
    BalanceTransferDetailComponent,
    BalanceTransferUpdateComponent,
    BalanceTransferDeleteDialogComponent,
  ],
  entryComponents: [BalanceTransferDeleteDialogComponent],
})
export class BalanceTransferModule {}
