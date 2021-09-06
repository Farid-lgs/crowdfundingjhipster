import { Component, OnInit } from '@angular/core';
import GlobalVariables from "../../core/context/global-variables";

@Component({
  selector: 'jhi-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  btnDatas: Array<{ logo: string, txt: string }>;

  constructor(public globalVariables: GlobalVariables) {
    this.btnDatas = globalVariables.personalSubbarBtns;
  }

  ngOnInit(): void {
    // Do nothing
  }

}
