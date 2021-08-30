import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBalanceTransfer, BalanceTransfer } from '../balance-transfer.model';
import { BalanceTransferService } from '../service/balance-transfer.service';

@Injectable({ providedIn: 'root' })
export class BalanceTransferRoutingResolveService implements Resolve<IBalanceTransfer> {
  constructor(protected service: BalanceTransferService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBalanceTransfer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((balanceTransfer: HttpResponse<BalanceTransfer>) => {
          if (balanceTransfer.body) {
            return of(balanceTransfer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BalanceTransfer());
  }
}
