import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBalanceTransfer, getBalanceTransferIdentifier } from '../balance-transfer.model';

export type EntityResponseType = HttpResponse<IBalanceTransfer>;
export type EntityArrayResponseType = HttpResponse<IBalanceTransfer[]>;

@Injectable({ providedIn: 'root' })
export class BalanceTransferService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/balance-transfers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(balanceTransfer: IBalanceTransfer): Observable<EntityResponseType> {
    return this.http.post<IBalanceTransfer>(this.resourceUrl, balanceTransfer, { observe: 'response' });
  }

  update(balanceTransfer: IBalanceTransfer): Observable<EntityResponseType> {
    return this.http.put<IBalanceTransfer>(
      `${this.resourceUrl}/${getBalanceTransferIdentifier(balanceTransfer) as number}`,
      balanceTransfer,
      { observe: 'response' }
    );
  }

  partialUpdate(balanceTransfer: IBalanceTransfer): Observable<EntityResponseType> {
    return this.http.patch<IBalanceTransfer>(
      `${this.resourceUrl}/${getBalanceTransferIdentifier(balanceTransfer) as number}`,
      balanceTransfer,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBalanceTransfer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBalanceTransfer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBalanceTransferToCollectionIfMissing(
    balanceTransferCollection: IBalanceTransfer[],
    ...balanceTransfersToCheck: (IBalanceTransfer | null | undefined)[]
  ): IBalanceTransfer[] {
    const balanceTransfers: IBalanceTransfer[] = balanceTransfersToCheck.filter(isPresent);
    if (balanceTransfers.length > 0) {
      const balanceTransferCollectionIdentifiers = balanceTransferCollection.map(
        balanceTransferItem => getBalanceTransferIdentifier(balanceTransferItem)!
      );
      const balanceTransfersToAdd = balanceTransfers.filter(balanceTransferItem => {
        const balanceTransferIdentifier = getBalanceTransferIdentifier(balanceTransferItem);
        if (balanceTransferIdentifier == null || balanceTransferCollectionIdentifiers.includes(balanceTransferIdentifier)) {
          return false;
        }
        balanceTransferCollectionIdentifiers.push(balanceTransferIdentifier);
        return true;
      });
      return [...balanceTransfersToAdd, ...balanceTransferCollection];
    }
    return balanceTransferCollection;
  }
}
