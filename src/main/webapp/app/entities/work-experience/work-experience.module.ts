import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WorkExperienceComponent } from './list/work-experience.component';
import { WorkExperienceDetailComponent } from './detail/work-experience-detail.component';
import { WorkExperienceUpdateComponent } from './update/work-experience-update.component';
import { WorkExperienceDeleteDialogComponent } from './delete/work-experience-delete-dialog.component';
import { WorkExperienceRoutingModule } from './route/work-experience-routing.module';

@NgModule({
  imports: [SharedModule, WorkExperienceRoutingModule],
  declarations: [
    WorkExperienceComponent,
    WorkExperienceDetailComponent,
    WorkExperienceUpdateComponent,
    WorkExperienceDeleteDialogComponent,
  ],
})
export class WorkExperienceModule {}
