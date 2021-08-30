import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContributionNotifications, getContributionNotificationsIdentifier } from '../contribution-notifications.model';

export type EntityResponseType = HttpResponse<IContributionNotifications>;
export type EntityArrayResponseType = HttpResponse<IContributionNotifications[]>;

@Injectable({ providedIn: 'root' })
export class ContributionNotificationsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contribution-notifications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contributionNotifications: IContributionNotifications): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contributionNotifications);
    return this.http
      .post<IContributionNotifications>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contributionNotifications: IContributionNotifications): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contributionNotifications);
    return this.http
      .put<IContributionNotifications>(
        `${this.resourceUrl}/${getContributionNotificationsIdentifier(contributionNotifications) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(contributionNotifications: IContributionNotifications): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contributionNotifications);
    return this.http
      .patch<IContributionNotifications>(
        `${this.resourceUrl}/${getContributionNotificationsIdentifier(contributionNotifications) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContributionNotifications>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContributionNotifications[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addContributionNotificationsToCollectionIfMissing(
    contributionNotificationsCollection: IContributionNotifications[],
    ...contributionNotificationsToCheck: (IContributionNotifications | null | undefined)[]
  ): IContributionNotifications[] {
    const contributionNotifications: IContributionNotifications[] = contributionNotificationsToCheck.filter(isPresent);
    if (contributionNotifications.length > 0) {
      const contributionNotificationsCollectionIdentifiers = contributionNotificationsCollection.map(
        contributionNotificationsItem => getContributionNotificationsIdentifier(contributionNotificationsItem)!
      );
      const contributionNotificationsToAdd = contributionNotifications.filter(contributionNotificationsItem => {
        const contributionNotificationsIdentifier = getContributionNotificationsIdentifier(contributionNotificationsItem);
        if (
          contributionNotificationsIdentifier == null ||
          contributionNotificationsCollectionIdentifiers.includes(contributionNotificationsIdentifier)
        ) {
          return false;
        }
        contributionNotificationsCollectionIdentifiers.push(contributionNotificationsIdentifier);
        return true;
      });
      return [...contributionNotificationsToAdd, ...contributionNotificationsCollection];
    }
    return contributionNotificationsCollection;
  }

  protected convertDateFromClient(contributionNotifications: IContributionNotifications): IContributionNotifications {
    return Object.assign({}, contributionNotifications, {
      sentAt: contributionNotifications.sentAt?.isValid() ? contributionNotifications.sentAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sentAt = res.body.sentAt ? dayjs(res.body.sentAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contributionNotifications: IContributionNotifications) => {
        contributionNotifications.sentAt = contributionNotifications.sentAt ? dayjs(contributionNotifications.sentAt) : undefined;
      });
    }
    return res;
  }
}
