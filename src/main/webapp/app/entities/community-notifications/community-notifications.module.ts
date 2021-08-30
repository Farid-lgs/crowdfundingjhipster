import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommunityNotificationsComponent } from './list/community-notifications.component';
import { CommunityNotificationsDetailComponent } from './detail/community-notifications-detail.component';
import { CommunityNotificationsUpdateComponent } from './update/community-notifications-update.component';
import { CommunityNotificationsDeleteDialogComponent } from './delete/community-notifications-delete-dialog.component';
import { CommunityNotificationsRoutingModule } from './route/community-notifications-routing.module';

@NgModule({
  imports: [SharedModule, CommunityNotificationsRoutingModule],
  declarations: [
    CommunityNotificationsComponent,
    CommunityNotificationsDetailComponent,
    CommunityNotificationsUpdateComponent,
    CommunityNotificationsDeleteDialogComponent,
  ],
  entryComponents: [CommunityNotificationsDeleteDialogComponent],
})
export class CommunityNotificationsModule {}
