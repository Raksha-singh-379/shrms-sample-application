import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectTeams } from '../project-teams.model';
import { ProjectTeamsService } from '../service/project-teams.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './project-teams-delete-dialog.component.html',
})
export class ProjectTeamsDeleteDialogComponent {
  projectTeams?: IProjectTeams;

  constructor(protected projectTeamsService: ProjectTeamsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectTeamsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
