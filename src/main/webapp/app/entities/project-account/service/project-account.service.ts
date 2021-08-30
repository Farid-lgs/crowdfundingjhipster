import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectAccount, getProjectAccountIdentifier } from '../project-account.model';

export type EntityResponseType = HttpResponse<IProjectAccount>;
export type EntityArrayResponseType = HttpResponse<IProjectAccount[]>;

@Injectable({ providedIn: 'root' })
export class ProjectAccountService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-accounts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectAccount: IProjectAccount): Observable<EntityResponseType> {
    return this.http.post<IProjectAccount>(this.resourceUrl, projectAccount, { observe: 'response' });
  }

  update(projectAccount: IProjectAccount): Observable<EntityResponseType> {
    return this.http.put<IProjectAccount>(`${this.resourceUrl}/${getProjectAccountIdentifier(projectAccount) as number}`, projectAccount, {
      observe: 'response',
    });
  }

  partialUpdate(projectAccount: IProjectAccount): Observable<EntityResponseType> {
    return this.http.patch<IProjectAccount>(
      `${this.resourceUrl}/${getProjectAccountIdentifier(projectAccount) as number}`,
      projectAccount,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProjectAccountToCollectionIfMissing(
    projectAccountCollection: IProjectAccount[],
    ...projectAccountsToCheck: (IProjectAccount | null | undefined)[]
  ): IProjectAccount[] {
    const projectAccounts: IProjectAccount[] = projectAccountsToCheck.filter(isPresent);
    if (projectAccounts.length > 0) {
      const projectAccountCollectionIdentifiers = projectAccountCollection.map(
        projectAccountItem => getProjectAccountIdentifier(projectAccountItem)!
      );
      const projectAccountsToAdd = projectAccounts.filter(projectAccountItem => {
        const projectAccountIdentifier = getProjectAccountIdentifier(projectAccountItem);
        if (projectAccountIdentifier == null || projectAccountCollectionIdentifiers.includes(projectAccountIdentifier)) {
          return false;
        }
        projectAccountCollectionIdentifiers.push(projectAccountIdentifier);
        return true;
      });
      return [...projectAccountsToAdd, ...projectAccountCollection];
    }
    return projectAccountCollection;
  }
}
