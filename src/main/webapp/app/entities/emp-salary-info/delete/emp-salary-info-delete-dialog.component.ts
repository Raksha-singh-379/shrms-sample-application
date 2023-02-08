import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmpSalaryInfo } from '../emp-salary-info.model';
import { EmpSalaryInfoService } from '../service/emp-salary-info.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './emp-salary-info-delete-dialog.component.html',
})
export class EmpSalaryInfoDeleteDialogComponent {
  empSalaryInfo?: IEmpSalaryInfo;

  constructor(protected empSalaryInfoService: EmpSalaryInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.empSalaryInfoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
