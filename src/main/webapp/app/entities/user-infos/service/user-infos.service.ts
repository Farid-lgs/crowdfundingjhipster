import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserInfos, getUserInfosIdentifier } from '../user-infos.model';

export type EntityResponseType = HttpResponse<IUserInfos>;
export type EntityArrayResponseType = HttpResponse<IUserInfos[]>;

@Injectable({ providedIn: 'root' })
export class UserInfosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userInfos: IUserInfos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userInfos);
    return this.http
      .post<IUserInfos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userInfos: IUserInfos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userInfos);
    return this.http
      .put<IUserInfos>(`${this.resourceUrl}/${getUserInfosIdentifier(userInfos) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userInfos: IUserInfos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userInfos);
    return this.http
      .patch<IUserInfos>(`${this.resourceUrl}/${getUserInfosIdentifier(userInfos) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserInfos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserInfos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserInfosToCollectionIfMissing(
    userInfosCollection: IUserInfos[],
    ...userInfosToCheck: (IUserInfos | null | undefined)[]
  ): IUserInfos[] {
    const userInfos: IUserInfos[] = userInfosToCheck.filter(isPresent);
    if (userInfos.length > 0) {
      const userInfosCollectionIdentifiers = userInfosCollection.map(userInfosItem => getUserInfosIdentifier(userInfosItem)!);
      const userInfosToAdd = userInfos.filter(userInfosItem => {
        const userInfosIdentifier = getUserInfosIdentifier(userInfosItem);
        if (userInfosIdentifier == null || userInfosCollectionIdentifiers.includes(userInfosIdentifier)) {
          return false;
        }
        userInfosCollectionIdentifiers.push(userInfosIdentifier);
        return true;
      });
      return [...userInfosToAdd, ...userInfosCollection];
    }
    return userInfosCollection;
  }

  protected convertDateFromClient(userInfos: IUserInfos): IUserInfos {
    return Object.assign({}, userInfos, {
      birthDate: userInfos.birthDate?.isValid() ? userInfos.birthDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate ? dayjs(res.body.birthDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userInfos: IUserInfos) => {
        userInfos.birthDate = userInfos.birthDate ? dayjs(userInfos.birthDate) : undefined;
      });
    }
    return res;
  }
}
