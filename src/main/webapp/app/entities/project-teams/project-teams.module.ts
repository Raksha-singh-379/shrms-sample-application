import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectTeamsComponent } from './list/project-teams.component';
import { ProjectTeamsDetailComponent } from './detail/project-teams-detail.component';
import { ProjectTeamsUpdateComponent } from './update/project-teams-update.component';
import { ProjectTeamsDeleteDialogComponent } from './delete/project-teams-delete-dialog.component';
import { ProjectTeamsRoutingModule } from './route/project-teams-routing.module';

@NgModule({
  imports: [SharedModule, ProjectTeamsRoutingModule],
  declarations: [ProjectTeamsComponent, ProjectTeamsDetailComponent, ProjectTeamsUpdateComponent, ProjectTeamsDeleteDialogComponent],
})
export class ProjectTeamsModule {}
