import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContributionNotificationsComponent } from './list/contribution-notifications.component';
import { ContributionNotificationsDetailComponent } from './detail/contribution-notifications-detail.component';
import { ContributionNotificationsUpdateComponent } from './update/contribution-notifications-update.component';
import { ContributionNotificationsDeleteDialogComponent } from './delete/contribution-notifications-delete-dialog.component';
import { ContributionNotificationsRoutingModule } from './route/contribution-notifications-routing.module';

@NgModule({
  imports: [SharedModule, ContributionNotificationsRoutingModule],
  declarations: [
    ContributionNotificationsComponent,
    ContributionNotificationsDetailComponent,
    ContributionNotificationsUpdateComponent,
    ContributionNotificationsDeleteDialogComponent,
  ],
  entryComponents: [ContributionNotificationsDeleteDialogComponent],
})
export class ContributionNotificationsModule {}
