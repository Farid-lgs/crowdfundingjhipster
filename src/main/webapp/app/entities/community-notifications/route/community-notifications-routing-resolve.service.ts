import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommunityNotifications, CommunityNotifications } from '../community-notifications.model';
import { CommunityNotificationsService } from '../service/community-notifications.service';

@Injectable({ providedIn: 'root' })
export class CommunityNotificationsRoutingResolveService implements Resolve<ICommunityNotifications> {
  constructor(protected service: CommunityNotificationsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommunityNotifications> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((communityNotifications: HttpResponse<CommunityNotifications>) => {
          if (communityNotifications.body) {
            return of(communityNotifications.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommunityNotifications());
  }
}
