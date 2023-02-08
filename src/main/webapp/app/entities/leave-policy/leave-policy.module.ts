import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LeavePolicyComponent } from './list/leave-policy.component';
import { LeavePolicyDetailComponent } from './detail/leave-policy-detail.component';
import { LeavePolicyUpdateComponent } from './update/leave-policy-update.component';
import { LeavePolicyDeleteDialogComponent } from './delete/leave-policy-delete-dialog.component';
import { LeavePolicyRoutingModule } from './route/leave-policy-routing.module';

@NgModule({
  imports: [SharedModule, LeavePolicyRoutingModule],
  declarations: [LeavePolicyComponent, LeavePolicyDetailComponent, LeavePolicyUpdateComponent, LeavePolicyDeleteDialogComponent],
})
export class LeavePolicyModule {}
