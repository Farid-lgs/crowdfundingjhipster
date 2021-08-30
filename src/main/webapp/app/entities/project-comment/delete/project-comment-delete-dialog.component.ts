import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectComment } from '../project-comment.model';
import { ProjectCommentService } from '../service/project-comment.service';

@Component({
  templateUrl: './project-comment-delete-dialog.component.html',
})
export class ProjectCommentDeleteDialogComponent {
  projectComment?: IProjectComment;

  constructor(protected projectCommentService: ProjectCommentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectCommentService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
