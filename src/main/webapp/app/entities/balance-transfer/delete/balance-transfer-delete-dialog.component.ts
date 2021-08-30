import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBalanceTransfer } from '../balance-transfer.model';
import { BalanceTransferService } from '../service/balance-transfer.service';

@Component({
  templateUrl: './balance-transfer-delete-dialog.component.html',
})
export class BalanceTransferDeleteDialogComponent {
  balanceTransfer?: IBalanceTransfer;

  constructor(protected balanceTransferService: BalanceTransferService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.balanceTransferService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
