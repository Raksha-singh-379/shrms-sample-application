import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LeaveTranscationComponent } from './list/leave-transcation.component';
import { LeaveTranscationDetailComponent } from './detail/leave-transcation-detail.component';
import { LeaveTranscationUpdateComponent } from './update/leave-transcation-update.component';
import { LeaveTranscationDeleteDialogComponent } from './delete/leave-transcation-delete-dialog.component';
import { LeaveTranscationRoutingModule } from './route/leave-transcation-routing.module';

@NgModule({
  imports: [SharedModule, LeaveTranscationRoutingModule],
  declarations: [
    LeaveTranscationComponent,
    LeaveTranscationDetailComponent,
    LeaveTranscationUpdateComponent,
    LeaveTranscationDeleteDialogComponent,
  ],
})
export class LeaveTranscationModule {}
