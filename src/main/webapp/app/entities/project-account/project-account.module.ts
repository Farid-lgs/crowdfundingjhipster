import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectAccountComponent } from './list/project-account.component';
import { ProjectAccountDetailComponent } from './detail/project-account-detail.component';
import { ProjectAccountUpdateComponent } from './update/project-account-update.component';
import { ProjectAccountDeleteDialogComponent } from './delete/project-account-delete-dialog.component';
import { ProjectAccountRoutingModule } from './route/project-account-routing.module';

@NgModule({
  imports: [SharedModule, ProjectAccountRoutingModule],
  declarations: [
    ProjectAccountComponent,
    ProjectAccountDetailComponent,
    ProjectAccountUpdateComponent,
    ProjectAccountDeleteDialogComponent,
  ],
  entryComponents: [ProjectAccountDeleteDialogComponent],
})
export class ProjectAccountModule {}
