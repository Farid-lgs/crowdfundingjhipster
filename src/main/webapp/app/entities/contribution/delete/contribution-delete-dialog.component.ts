import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContribution } from '../contribution.model';
import { ContributionService } from '../service/contribution.service';

@Component({
  templateUrl: './contribution-delete-dialog.component.html',
})
export class ContributionDeleteDialogComponent {
  contribution?: IContribution;

  constructor(protected contributionService: ContributionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contributionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
