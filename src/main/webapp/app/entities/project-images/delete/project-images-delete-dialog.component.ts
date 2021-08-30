import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectImages } from '../project-images.model';
import { ProjectImagesService } from '../service/project-images.service';

@Component({
  templateUrl: './project-images-delete-dialog.component.html',
})
export class ProjectImagesDeleteDialogComponent {
  projectImages?: IProjectImages;

  constructor(protected projectImagesService: ProjectImagesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectImagesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
