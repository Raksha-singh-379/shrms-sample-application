import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILeaveTranscation } from '../leave-transcation.model';
import { LeaveTranscationService } from '../service/leave-transcation.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './leave-transcation-delete-dialog.component.html',
})
export class LeaveTranscationDeleteDialogComponent {
  leaveTranscation?: ILeaveTranscation;

  constructor(protected leaveTranscationService: LeaveTranscationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leaveTranscationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
