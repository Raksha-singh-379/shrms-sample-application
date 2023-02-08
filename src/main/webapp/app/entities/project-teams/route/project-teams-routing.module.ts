import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectTeamsComponent } from '../list/project-teams.component';
import { ProjectTeamsDetailComponent } from '../detail/project-teams-detail.component';
import { ProjectTeamsUpdateComponent } from '../update/project-teams-update.component';
import { ProjectTeamsRoutingResolveService } from './project-teams-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const projectTeamsRoute: Routes = [
  {
    path: '',
    component: ProjectTeamsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectTeamsDetailComponent,
    resolve: {
      projectTeams: ProjectTeamsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectTeamsUpdateComponent,
    resolve: {
      projectTeams: ProjectTeamsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectTeamsUpdateComponent,
    resolve: {
      projectTeams: ProjectTeamsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectTeamsRoute)],
  exports: [RouterModule],
})
export class ProjectTeamsRoutingModule {}
