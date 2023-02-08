import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WorkExperienceComponent } from '../list/work-experience.component';
import { WorkExperienceDetailComponent } from '../detail/work-experience-detail.component';
import { WorkExperienceUpdateComponent } from '../update/work-experience-update.component';
import { WorkExperienceRoutingResolveService } from './work-experience-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const workExperienceRoute: Routes = [
  {
    path: '',
    component: WorkExperienceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkExperienceDetailComponent,
    resolve: {
      workExperience: WorkExperienceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkExperienceUpdateComponent,
    resolve: {
      workExperience: WorkExperienceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkExperienceUpdateComponent,
    resolve: {
      workExperience: WorkExperienceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(workExperienceRoute)],
  exports: [RouterModule],
})
export class WorkExperienceRoutingModule {}
