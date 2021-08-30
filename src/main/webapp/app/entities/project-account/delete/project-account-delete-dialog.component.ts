import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectAccount } from '../project-account.model';
import { ProjectAccountService } from '../service/project-account.service';

@Component({
  templateUrl: './project-account-delete-dialog.component.html',
})
export class ProjectAccountDeleteDialogComponent {
  projectAccount?: IProjectAccount;

  constructor(protected projectAccountService: ProjectAccountService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectAccountService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
