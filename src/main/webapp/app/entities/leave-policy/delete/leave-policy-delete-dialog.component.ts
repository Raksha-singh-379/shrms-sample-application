import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILeavePolicy } from '../leave-policy.model';
import { LeavePolicyService } from '../service/leave-policy.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './leave-policy-delete-dialog.component.html',
})
export class LeavePolicyDeleteDialogComponent {
  leavePolicy?: ILeavePolicy;

  constructor(protected leavePolicyService: LeavePolicyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leavePolicyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
