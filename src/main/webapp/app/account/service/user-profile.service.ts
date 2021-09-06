import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {IUserInfos} from "../../entities/user-infos/user-infos.model";
import {HttpClient} from "@angular/common/http";
import {ApplicationConfigService} from "../../core/config/application-config.service";

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/users');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) { /* Do nothing */}

  find(login: string): Observable<IUserInfos> {
    return this.http.get<IUserInfos>(`${this.resourceUrl}/${login}`);
  }
}
