import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBanksDetails } from '../banks-details.model';
import { BanksDetailsService } from '../service/banks-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './banks-details-delete-dialog.component.html',
})
export class BanksDetailsDeleteDialogComponent {
  banksDetails?: IBanksDetails;

  constructor(protected banksDetailsService: BanksDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.banksDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
