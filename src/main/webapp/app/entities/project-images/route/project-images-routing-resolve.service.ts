import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectImages, ProjectImages } from '../project-images.model';
import { ProjectImagesService } from '../service/project-images.service';

@Injectable({ providedIn: 'root' })
export class ProjectImagesRoutingResolveService implements Resolve<IProjectImages> {
  constructor(protected service: ProjectImagesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectImages> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectImages: HttpResponse<ProjectImages>) => {
          if (projectImages.body) {
            return of(projectImages.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectImages());
  }
}
