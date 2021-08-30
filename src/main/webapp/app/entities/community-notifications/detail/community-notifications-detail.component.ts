import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommunityNotifications } from '../community-notifications.model';

@Component({
  selector: 'jhi-community-notifications-detail',
  templateUrl: './community-notifications-detail.component.html',
})
export class CommunityNotificationsDetailComponent implements OnInit {
  communityNotifications: ICommunityNotifications | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ communityNotifications }) => {
      this.communityNotifications = communityNotifications;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
