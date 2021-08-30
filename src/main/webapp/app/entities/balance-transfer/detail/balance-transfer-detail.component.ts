import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBalanceTransfer } from '../balance-transfer.model';

@Component({
  selector: 'jhi-balance-transfer-detail',
  templateUrl: './balance-transfer-detail.component.html',
})
export class BalanceTransferDetailComponent implements OnInit {
  balanceTransfer: IBalanceTransfer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ balanceTransfer }) => {
      this.balanceTransfer = balanceTransfer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
