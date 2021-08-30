import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectAccount, ProjectAccount } from '../project-account.model';
import { ProjectAccountService } from '../service/project-account.service';

@Injectable({ providedIn: 'root' })
export class ProjectAccountRoutingResolveService implements Resolve<IProjectAccount> {
  constructor(protected service: ProjectAccountService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectAccount: HttpResponse<ProjectAccount>) => {
          if (projectAccount.body) {
            return of(projectAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectAccount());
  }
}
