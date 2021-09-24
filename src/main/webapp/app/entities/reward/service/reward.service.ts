import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReward, getRewardIdentifier } from '../reward.model';

export type EntityResponseType = HttpResponse<IReward>;
export type EntityArrayResponseType = HttpResponse<IReward[]>;

@Injectable({ providedIn: 'root' })
export class RewardService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rewards');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reward: IReward): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reward);
    return this.http
      .post<IReward>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reward: IReward): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reward);
    return this.http
      .put<IReward>(`${this.resourceUrl}/${getRewardIdentifier(reward) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(reward: IReward): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reward);
    return this.http
      .patch<IReward>(`${this.resourceUrl}/${getRewardIdentifier(reward) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReward>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReward[]>(`${this.resourceUrl}/project/${id}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRewardToCollectionIfMissing(rewardCollection: IReward[], ...rewardsToCheck: (IReward | null | undefined)[]): IReward[] {
    const rewards: IReward[] = rewardsToCheck.filter(isPresent);
    if (rewards.length > 0) {
      const rewardCollectionIdentifiers = rewardCollection.map(rewardItem => getRewardIdentifier(rewardItem)!);
      const rewardsToAdd = rewards.filter(rewardItem => {
        const rewardIdentifier = getRewardIdentifier(rewardItem);
        if (rewardIdentifier == null || rewardCollectionIdentifiers.includes(rewardIdentifier)) {
          return false;
        }
        rewardCollectionIdentifiers.push(rewardIdentifier);
        return true;
      });
      return [...rewardsToAdd, ...rewardCollection];
    }
    return rewardCollection;
  }

  protected convertDateFromClient(reward: IReward): IReward {
    return Object.assign({}, reward, {
      deliverAt: reward.deliverAt?.isValid() ? reward.deliverAt.toJSON() : undefined,
      expiresAt: reward.expiresAt?.isValid() ? reward.expiresAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.deliverAt = res.body.deliverAt ? dayjs(res.body.deliverAt) : undefined;
      res.body.expiresAt = res.body.expiresAt ? dayjs(res.body.expiresAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reward: IReward) => {
        reward.deliverAt = reward.deliverAt ? dayjs(reward.deliverAt) : undefined;
        reward.expiresAt = reward.expiresAt ? dayjs(reward.expiresAt) : undefined;
      });
    }
    return res;
  }
}
