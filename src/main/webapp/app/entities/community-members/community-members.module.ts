import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommunityMembersComponent } from './list/community-members.component';
import { CommunityMembersDetailComponent } from './detail/community-members-detail.component';
import { CommunityMembersUpdateComponent } from './update/community-members-update.component';
import { CommunityMembersDeleteDialogComponent } from './delete/community-members-delete-dialog.component';
import { CommunityMembersRoutingModule } from './route/community-members-routing.module';

@NgModule({
  imports: [SharedModule, CommunityMembersRoutingModule],
  declarations: [
    CommunityMembersComponent,
    CommunityMembersDetailComponent,
    CommunityMembersUpdateComponent,
    CommunityMembersDeleteDialogComponent,
  ],
  entryComponents: [CommunityMembersDeleteDialogComponent],
})
export class CommunityMembersModule {}
