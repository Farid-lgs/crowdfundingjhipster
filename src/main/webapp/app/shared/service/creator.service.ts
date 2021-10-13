import { Injectable } from '@angular/core';
import {UserInfosService} from "../../entities/user-infos/service/user-infos.service";

@Injectable({
  providedIn: 'root'
})
export class CreatorService {
  userLogin: string | undefined = '';
  id: number | undefined = 0;

  constructor(protected userInfosService: UserInfosService) {/*do nothing*/}

  isCreator(): boolean {
    return localStorage.getItem('login') === this.userLogin;
  }

  idUser(): void {
    this.userInfosService.findUserByLogin(localStorage.getItem('login') as string).subscribe(
    (value) => {
      this.id = value.body?.id;
    },
      (error) => {
      console.log('error');
    });
  }
 }
