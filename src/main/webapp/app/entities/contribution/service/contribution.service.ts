import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContribution, getContributionIdentifier } from '../contribution.model';

export type EntityResponseType = HttpResponse<IContribution>;
export type EntityArrayResponseType = HttpResponse<IContribution[]>;

@Injectable({ providedIn: 'root' })
export class ContributionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contributions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contribution: IContribution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contribution);
    return this.http
      .post<IContribution>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contribution: IContribution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contribution);
    return this.http
      .put<IContribution>(`${this.resourceUrl}/${getContributionIdentifier(contribution) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(contribution: IContribution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contribution);
    return this.http
      .patch<IContribution>(`${this.resourceUrl}/${getContributionIdentifier(contribution) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContribution>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContribution[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addContributionToCollectionIfMissing(
    contributionCollection: IContribution[],
    ...contributionsToCheck: (IContribution | null | undefined)[]
  ): IContribution[] {
    const contributions: IContribution[] = contributionsToCheck.filter(isPresent);
    if (contributions.length > 0) {
      const contributionCollectionIdentifiers = contributionCollection.map(
        contributionItem => getContributionIdentifier(contributionItem)!
      );
      const contributionsToAdd = contributions.filter(contributionItem => {
        const contributionIdentifier = getContributionIdentifier(contributionItem);
        if (contributionIdentifier == null || contributionCollectionIdentifiers.includes(contributionIdentifier)) {
          return false;
        }
        contributionCollectionIdentifiers.push(contributionIdentifier);
        return true;
      });
      return [...contributionsToAdd, ...contributionCollection];
    }
    return contributionCollection;
  }

  protected convertDateFromClient(contribution: IContribution): IContribution {
    return Object.assign({}, contribution, {
      createdAt: contribution.createdAt?.isValid() ? contribution.createdAt.toJSON() : undefined,
      updatedAt: contribution.updatedAt?.isValid() ? contribution.updatedAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contribution: IContribution) => {
        contribution.createdAt = contribution.createdAt ? dayjs(contribution.createdAt) : undefined;
        contribution.updatedAt = contribution.updatedAt ? dayjs(contribution.updatedAt) : undefined;
      });
    }
    return res;
  }
}
