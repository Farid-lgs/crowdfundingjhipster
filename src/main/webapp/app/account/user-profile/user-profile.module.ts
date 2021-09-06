import { NgModule } from '@angular/core';
import {SharedModule} from "../../shared/shared.module";
import {AccountModule} from "../account.module";

@NgModule({
  declarations: [
  ],
  imports: [
    SharedModule,
    AccountModule
  ],
})
export class UserProfileModule { }
