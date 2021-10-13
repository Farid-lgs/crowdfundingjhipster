import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectComment, getProjectCommentIdentifier } from '../project-comment.model';

export type EntityResponseType = HttpResponse<IProjectComment>;
export type EntityArrayResponseType = HttpResponse<IProjectComment[]>;

@Injectable({ providedIn: 'root' })
export class ProjectCommentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-comments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectComment: IProjectComment): Observable<EntityResponseType> {
    return this.http.post<IProjectComment>(this.resourceUrl, projectComment, { observe: 'response' });
  }

  update(projectComment: IProjectComment): Observable<EntityResponseType> {
    return this.http.put<IProjectComment>(`${this.resourceUrl}/${getProjectCommentIdentifier(projectComment) as number}`, projectComment, {
      observe: 'response',
    });
  }

  partialUpdate(projectComment: IProjectComment): Observable<EntityResponseType> {
    return this.http.patch<IProjectComment>(
      `${this.resourceUrl}/${getProjectCommentIdentifier(projectComment) as number}`,
      projectComment,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectComment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectComment[]>(`${this.resourceUrl}/project/${id}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProjectCommentToCollectionIfMissing(
    projectCommentCollection: IProjectComment[],
    ...projectCommentsToCheck: (IProjectComment | null | undefined)[]
  ): IProjectComment[] {
    const projectComments: IProjectComment[] = projectCommentsToCheck.filter(isPresent);
    if (projectComments.length > 0) {
      const projectCommentCollectionIdentifiers = projectCommentCollection.map(
        projectCommentItem => getProjectCommentIdentifier(projectCommentItem)!
      );
      const projectCommentsToAdd = projectComments.filter(projectCommentItem => {
        const projectCommentIdentifier = getProjectCommentIdentifier(projectCommentItem);
        if (projectCommentIdentifier == null || projectCommentCollectionIdentifiers.includes(projectCommentIdentifier)) {
          return false;
        }
        projectCommentCollectionIdentifiers.push(projectCommentIdentifier);
        return true;
      });
      return [...projectCommentsToAdd, ...projectCommentCollection];
    }
    return projectCommentCollection;
  }
}
