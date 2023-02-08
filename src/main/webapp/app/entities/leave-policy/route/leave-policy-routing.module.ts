import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LeavePolicyComponent } from '../list/leave-policy.component';
import { LeavePolicyDetailComponent } from '../detail/leave-policy-detail.component';
import { LeavePolicyUpdateComponent } from '../update/leave-policy-update.component';
import { LeavePolicyRoutingResolveService } from './leave-policy-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const leavePolicyRoute: Routes = [
  {
    path: '',
    component: LeavePolicyComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LeavePolicyDetailComponent,
    resolve: {
      leavePolicy: LeavePolicyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LeavePolicyUpdateComponent,
    resolve: {
      leavePolicy: LeavePolicyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LeavePolicyUpdateComponent,
    resolve: {
      leavePolicy: LeavePolicyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(leavePolicyRoute)],
  exports: [RouterModule],
})
export class LeavePolicyRoutingModule {}
