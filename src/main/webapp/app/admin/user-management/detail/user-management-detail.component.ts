import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import {UserInfos} from "../../../entities/user-infos/user-infos.model";

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './user-management-detail.component.html',
})
export class UserManagementDetailComponent implements OnInit {
  user: UserInfos | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {

      console.log( user);
      this.user = user;
    });
  }
}
