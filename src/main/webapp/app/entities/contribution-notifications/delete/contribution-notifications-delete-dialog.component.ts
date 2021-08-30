import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContributionNotifications } from '../contribution-notifications.model';
import { ContributionNotificationsService } from '../service/contribution-notifications.service';

@Component({
  templateUrl: './contribution-notifications-delete-dialog.component.html',
})
export class ContributionNotificationsDeleteDialogComponent {
  contributionNotifications?: IContributionNotifications;

  constructor(protected contributionNotificationsService: ContributionNotificationsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contributionNotificationsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
