import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve} from "@angular/router";
import {IUserInfos} from "../entities/user-infos/user-infos.model";
import {UserProfileService} from "./service/user-profile.service";
import {Observable, of} from "rxjs";
import {User} from "../admin/user-management/user-management.model";

@Injectable({ providedIn: 'root' })
export class UserResolve implements Resolve<IUserInfos> {

  constructor(private service: UserProfileService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserInfos> {
    const id = route.params['login'];
    if (id) {
      return this.service.find(id);
    }
    return of(new User());
  }

}
