import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommunityNotifications } from '../community-notifications.model';
import { CommunityNotificationsService } from '../service/community-notifications.service';

@Component({
  templateUrl: './community-notifications-delete-dialog.component.html',
})
export class CommunityNotificationsDeleteDialogComponent {
  communityNotifications?: ICommunityNotifications;

  constructor(protected communityNotificationsService: CommunityNotificationsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.communityNotificationsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
