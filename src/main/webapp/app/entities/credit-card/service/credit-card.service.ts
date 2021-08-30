import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICreditCard, getCreditCardIdentifier } from '../credit-card.model';

export type EntityResponseType = HttpResponse<ICreditCard>;
export type EntityArrayResponseType = HttpResponse<ICreditCard[]>;

@Injectable({ providedIn: 'root' })
export class CreditCardService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/credit-cards');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(creditCard: ICreditCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creditCard);
    return this.http
      .post<ICreditCard>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(creditCard: ICreditCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creditCard);
    return this.http
      .put<ICreditCard>(`${this.resourceUrl}/${getCreditCardIdentifier(creditCard) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(creditCard: ICreditCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creditCard);
    return this.http
      .patch<ICreditCard>(`${this.resourceUrl}/${getCreditCardIdentifier(creditCard) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICreditCard>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICreditCard[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCreditCardToCollectionIfMissing(
    creditCardCollection: ICreditCard[],
    ...creditCardsToCheck: (ICreditCard | null | undefined)[]
  ): ICreditCard[] {
    const creditCards: ICreditCard[] = creditCardsToCheck.filter(isPresent);
    if (creditCards.length > 0) {
      const creditCardCollectionIdentifiers = creditCardCollection.map(creditCardItem => getCreditCardIdentifier(creditCardItem)!);
      const creditCardsToAdd = creditCards.filter(creditCardItem => {
        const creditCardIdentifier = getCreditCardIdentifier(creditCardItem);
        if (creditCardIdentifier == null || creditCardCollectionIdentifiers.includes(creditCardIdentifier)) {
          return false;
        }
        creditCardCollectionIdentifiers.push(creditCardIdentifier);
        return true;
      });
      return [...creditCardsToAdd, ...creditCardCollection];
    }
    return creditCardCollection;
  }

  protected convertDateFromClient(creditCard: ICreditCard): ICreditCard {
    return Object.assign({}, creditCard, {
      expirationDate: creditCard.expirationDate?.isValid() ? creditCard.expirationDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.expirationDate = res.body.expirationDate ? dayjs(res.body.expirationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((creditCard: ICreditCard) => {
        creditCard.expirationDate = creditCard.expirationDate ? dayjs(creditCard.expirationDate) : undefined;
      });
    }
    return res;
  }
}
