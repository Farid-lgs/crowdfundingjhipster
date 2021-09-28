import { Component, OnInit } from '@angular/core';
import GlobalVariables from "../../core/context/global-variables";
import {UserInfosService} from "../../entities/user-infos/service/user-infos.service";
import {IUserInfos} from "../../entities/user-infos/user-infos.model";
import {HttpResponse} from "@angular/common/http";
import {AccountService} from "../../core/auth/account.service";

@Component({
  selector: 'jhi-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  btnDatas: Array<{ path: string, logo: string, txt: string }> = [];
  login: string = localStorage.getItem('login') as string;
  userInfos: IUserInfos | null = null;
  id: number | null | undefined;

  constructor(public globalVariables: GlobalVariables, public userInfosService: UserInfosService, protected accountService: AccountService) {
  }

  ngOnInit(): void {
    this.userInfosService.findUserByLogin(this.login).subscribe(
      (res: HttpResponse<IUserInfos>) => {
        this.userInfos = res.body;


        if(this.accountService.userIdentity !== null) {
          this.id = this.accountService.userIdentity.id;
          this.login = this.accountService.userIdentity.login;
        }

        this.globalVariables.personalSubbarBtns[0].path = `details/${this.login}/view`;
        this.btnDatas = this.globalVariables.personalSubbarBtns;

        if(!this.id) {
          throw new Error("Not Number Id");
        } else {
          this.globalVariables.personalSubbarBtns[1].path = `project/${(this.id)}`;
        }
      },
      () => {
        console.log('error');
      }
    );


  }

}
