import { Component, OnInit } from '@angular/core';
import GlobalVariables from "../../core/context/global-variables";

@Component({
  selector: 'jhi-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  btnDatas: Array<{ path: string, logo: string, txt: string }>;

  constructor(public globalVariables: GlobalVariables) {
    globalVariables.personalSubbarBtns[0].path = `details/${localStorage.getItem('login') as string}/view`;
    this.btnDatas = globalVariables.personalSubbarBtns;
  }

  ngOnInit(): void {
    // Do nothing
  }

}
