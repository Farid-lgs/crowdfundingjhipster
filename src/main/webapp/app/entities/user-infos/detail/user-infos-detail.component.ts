import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import { IUserInfos } from '../user-infos.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-user-infos-detail',
  templateUrl: './user-infos-detail.component.html',
})
export class UserInfosDetailComponent implements OnInit {
  userInfos: IUserInfos | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userInfos }) => {
      this.userInfos = userInfos;
      console.log(userInfos);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
