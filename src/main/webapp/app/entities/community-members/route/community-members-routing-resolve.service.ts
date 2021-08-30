import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommunityMembers, CommunityMembers } from '../community-members.model';
import { CommunityMembersService } from '../service/community-members.service';

@Injectable({ providedIn: 'root' })
export class CommunityMembersRoutingResolveService implements Resolve<ICommunityMembers> {
  constructor(protected service: CommunityMembersService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommunityMembers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((communityMembers: HttpResponse<CommunityMembers>) => {
          if (communityMembers.body) {
            return of(communityMembers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommunityMembers());
  }
}
