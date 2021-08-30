import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectImages, getProjectImagesIdentifier } from '../project-images.model';

export type EntityResponseType = HttpResponse<IProjectImages>;
export type EntityArrayResponseType = HttpResponse<IProjectImages[]>;

@Injectable({ providedIn: 'root' })
export class ProjectImagesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-images');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectImages: IProjectImages): Observable<EntityResponseType> {
    return this.http.post<IProjectImages>(this.resourceUrl, projectImages, { observe: 'response' });
  }

  update(projectImages: IProjectImages): Observable<EntityResponseType> {
    return this.http.put<IProjectImages>(`${this.resourceUrl}/${getProjectImagesIdentifier(projectImages) as number}`, projectImages, {
      observe: 'response',
    });
  }

  partialUpdate(projectImages: IProjectImages): Observable<EntityResponseType> {
    return this.http.patch<IProjectImages>(`${this.resourceUrl}/${getProjectImagesIdentifier(projectImages) as number}`, projectImages, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectImages>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectImages[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProjectImagesToCollectionIfMissing(
    projectImagesCollection: IProjectImages[],
    ...projectImagesToCheck: (IProjectImages | null | undefined)[]
  ): IProjectImages[] {
    const projectImages: IProjectImages[] = projectImagesToCheck.filter(isPresent);
    if (projectImages.length > 0) {
      const projectImagesCollectionIdentifiers = projectImagesCollection.map(
        projectImagesItem => getProjectImagesIdentifier(projectImagesItem)!
      );
      const projectImagesToAdd = projectImages.filter(projectImagesItem => {
        const projectImagesIdentifier = getProjectImagesIdentifier(projectImagesItem);
        if (projectImagesIdentifier == null || projectImagesCollectionIdentifiers.includes(projectImagesIdentifier)) {
          return false;
        }
        projectImagesCollectionIdentifiers.push(projectImagesIdentifier);
        return true;
      });
      return [...projectImagesToAdd, ...projectImagesCollection];
    }
    return projectImagesCollection;
  }
}
