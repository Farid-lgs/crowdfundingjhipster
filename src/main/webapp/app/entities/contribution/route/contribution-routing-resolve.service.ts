import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContribution, Contribution } from '../contribution.model';
import { ContributionService } from '../service/contribution.service';

@Injectable({ providedIn: 'root' })
export class ContributionRoutingResolveService implements Resolve<IContribution> {
  constructor(protected service: ContributionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContribution> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contribution: HttpResponse<Contribution>) => {
          if (contribution.body) {
            return of(contribution.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Contribution());
  }
}
