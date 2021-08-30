import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommunityMembers } from '../community-members.model';
import { CommunityMembersService } from '../service/community-members.service';

@Component({
  templateUrl: './community-members-delete-dialog.component.html',
})
export class CommunityMembersDeleteDialogComponent {
  communityMembers?: ICommunityMembers;

  constructor(protected communityMembersService: CommunityMembersService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.communityMembersService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
