import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserInfosComponent } from './list/user-infos.component';
import { UserInfosDetailComponent } from './detail/user-infos-detail.component';
import { UserInfosUpdateComponent } from './update/user-infos-update.component';
import { UserInfosDeleteDialogComponent } from './delete/user-infos-delete-dialog.component';
import { UserInfosRoutingModule } from './route/user-infos-routing.module';

@NgModule({
  imports: [SharedModule, UserInfosRoutingModule],
  declarations: [UserInfosComponent, UserInfosDetailComponent, UserInfosUpdateComponent, UserInfosDeleteDialogComponent],
  entryComponents: [UserInfosDeleteDialogComponent],
})
export class UserInfosModule {}
