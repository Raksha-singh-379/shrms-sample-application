import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkExperience } from '../work-experience.model';
import { WorkExperienceService } from '../service/work-experience.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './work-experience-delete-dialog.component.html',
})
export class WorkExperienceDeleteDialogComponent {
  workExperience?: IWorkExperience;

  constructor(protected workExperienceService: WorkExperienceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workExperienceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
