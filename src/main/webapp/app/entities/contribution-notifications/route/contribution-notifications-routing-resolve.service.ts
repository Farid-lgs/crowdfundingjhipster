import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContributionNotifications, ContributionNotifications } from '../contribution-notifications.model';
import { ContributionNotificationsService } from '../service/contribution-notifications.service';

@Injectable({ providedIn: 'root' })
export class ContributionNotificationsRoutingResolveService implements Resolve<IContributionNotifications> {
  constructor(protected service: ContributionNotificationsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContributionNotifications> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contributionNotifications: HttpResponse<ContributionNotifications>) => {
          if (contributionNotifications.body) {
            return of(contributionNotifications.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContributionNotifications());
  }
}
