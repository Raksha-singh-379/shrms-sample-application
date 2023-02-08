import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LeaveTranscationComponent } from '../list/leave-transcation.component';
import { LeaveTranscationDetailComponent } from '../detail/leave-transcation-detail.component';
import { LeaveTranscationUpdateComponent } from '../update/leave-transcation-update.component';
import { LeaveTranscationRoutingResolveService } from './leave-transcation-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const leaveTranscationRoute: Routes = [
  {
    path: '',
    component: LeaveTranscationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LeaveTranscationDetailComponent,
    resolve: {
      leaveTranscation: LeaveTranscationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LeaveTranscationUpdateComponent,
    resolve: {
      leaveTranscation: LeaveTranscationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LeaveTranscationUpdateComponent,
    resolve: {
      leaveTranscation: LeaveTranscationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(leaveTranscationRoute)],
  exports: [RouterModule],
})
export class LeaveTranscationRoutingModule {}
