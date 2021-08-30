import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectComment, ProjectComment } from '../project-comment.model';
import { ProjectCommentService } from '../service/project-comment.service';

@Injectable({ providedIn: 'root' })
export class ProjectCommentRoutingResolveService implements Resolve<IProjectComment> {
  constructor(protected service: ProjectCommentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectComment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectComment: HttpResponse<ProjectComment>) => {
          if (projectComment.body) {
            return of(projectComment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectComment());
  }
}
