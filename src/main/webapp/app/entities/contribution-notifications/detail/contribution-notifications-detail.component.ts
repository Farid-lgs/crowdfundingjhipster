import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContributionNotifications } from '../contribution-notifications.model';

@Component({
  selector: 'jhi-contribution-notifications-detail',
  templateUrl: './contribution-notifications-detail.component.html',
})
export class ContributionNotificationsDetailComponent implements OnInit {
  contributionNotifications: IContributionNotifications | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contributionNotifications }) => {
      this.contributionNotifications = contributionNotifications;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
