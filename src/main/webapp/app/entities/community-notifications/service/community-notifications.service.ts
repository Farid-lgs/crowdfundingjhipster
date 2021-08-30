import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommunityNotifications, getCommunityNotificationsIdentifier } from '../community-notifications.model';

export type EntityResponseType = HttpResponse<ICommunityNotifications>;
export type EntityArrayResponseType = HttpResponse<ICommunityNotifications[]>;

@Injectable({ providedIn: 'root' })
export class CommunityNotificationsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/community-notifications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(communityNotifications: ICommunityNotifications): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(communityNotifications);
    return this.http
      .post<ICommunityNotifications>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(communityNotifications: ICommunityNotifications): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(communityNotifications);
    return this.http
      .put<ICommunityNotifications>(`${this.resourceUrl}/${getCommunityNotificationsIdentifier(communityNotifications) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(communityNotifications: ICommunityNotifications): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(communityNotifications);
    return this.http
      .patch<ICommunityNotifications>(
        `${this.resourceUrl}/${getCommunityNotificationsIdentifier(communityNotifications) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommunityNotifications>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommunityNotifications[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCommunityNotificationsToCollectionIfMissing(
    communityNotificationsCollection: ICommunityNotifications[],
    ...communityNotificationsToCheck: (ICommunityNotifications | null | undefined)[]
  ): ICommunityNotifications[] {
    const communityNotifications: ICommunityNotifications[] = communityNotificationsToCheck.filter(isPresent);
    if (communityNotifications.length > 0) {
      const communityNotificationsCollectionIdentifiers = communityNotificationsCollection.map(
        communityNotificationsItem => getCommunityNotificationsIdentifier(communityNotificationsItem)!
      );
      const communityNotificationsToAdd = communityNotifications.filter(communityNotificationsItem => {
        const communityNotificationsIdentifier = getCommunityNotificationsIdentifier(communityNotificationsItem);
        if (
          communityNotificationsIdentifier == null ||
          communityNotificationsCollectionIdentifiers.includes(communityNotificationsIdentifier)
        ) {
          return false;
        }
        communityNotificationsCollectionIdentifiers.push(communityNotificationsIdentifier);
        return true;
      });
      return [...communityNotificationsToAdd, ...communityNotificationsCollection];
    }
    return communityNotificationsCollection;
  }

  protected convertDateFromClient(communityNotifications: ICommunityNotifications): ICommunityNotifications {
    return Object.assign({}, communityNotifications, {
      sentAt: communityNotifications.sentAt?.isValid() ? communityNotifications.sentAt.toJSON() : undefined,
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
      res.body.forEach((communityNotifications: ICommunityNotifications) => {
        communityNotifications.sentAt = communityNotifications.sentAt ? dayjs(communityNotifications.sentAt) : undefined;
      });
    }
    return res;
  }
}
