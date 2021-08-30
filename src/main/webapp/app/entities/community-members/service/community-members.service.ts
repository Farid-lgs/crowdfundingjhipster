import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommunityMembers, getCommunityMembersIdentifier } from '../community-members.model';

export type EntityResponseType = HttpResponse<ICommunityMembers>;
export type EntityArrayResponseType = HttpResponse<ICommunityMembers[]>;

@Injectable({ providedIn: 'root' })
export class CommunityMembersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/community-members');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(communityMembers: ICommunityMembers): Observable<EntityResponseType> {
    return this.http.post<ICommunityMembers>(this.resourceUrl, communityMembers, { observe: 'response' });
  }

  update(communityMembers: ICommunityMembers): Observable<EntityResponseType> {
    return this.http.put<ICommunityMembers>(
      `${this.resourceUrl}/${getCommunityMembersIdentifier(communityMembers) as number}`,
      communityMembers,
      { observe: 'response' }
    );
  }

  partialUpdate(communityMembers: ICommunityMembers): Observable<EntityResponseType> {
    return this.http.patch<ICommunityMembers>(
      `${this.resourceUrl}/${getCommunityMembersIdentifier(communityMembers) as number}`,
      communityMembers,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommunityMembers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommunityMembers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCommunityMembersToCollectionIfMissing(
    communityMembersCollection: ICommunityMembers[],
    ...communityMembersToCheck: (ICommunityMembers | null | undefined)[]
  ): ICommunityMembers[] {
    const communityMembers: ICommunityMembers[] = communityMembersToCheck.filter(isPresent);
    if (communityMembers.length > 0) {
      const communityMembersCollectionIdentifiers = communityMembersCollection.map(
        communityMembersItem => getCommunityMembersIdentifier(communityMembersItem)!
      );
      const communityMembersToAdd = communityMembers.filter(communityMembersItem => {
        const communityMembersIdentifier = getCommunityMembersIdentifier(communityMembersItem);
        if (communityMembersIdentifier == null || communityMembersCollectionIdentifiers.includes(communityMembersIdentifier)) {
          return false;
        }
        communityMembersCollectionIdentifiers.push(communityMembersIdentifier);
        return true;
      });
      return [...communityMembersToAdd, ...communityMembersCollection];
    }
    return communityMembersCollection;
  }
}
