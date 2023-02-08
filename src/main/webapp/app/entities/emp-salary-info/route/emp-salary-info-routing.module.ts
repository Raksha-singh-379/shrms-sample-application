import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmpSalaryInfoComponent } from '../list/emp-salary-info.component';
import { EmpSalaryInfoDetailComponent } from '../detail/emp-salary-info-detail.component';
import { EmpSalaryInfoUpdateComponent } from '../update/emp-salary-info-update.component';
import { EmpSalaryInfoRoutingResolveService } from './emp-salary-info-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const empSalaryInfoRoute: Routes = [
  {
    path: '',
    component: EmpSalaryInfoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmpSalaryInfoDetailComponent,
    resolve: {
      empSalaryInfo: EmpSalaryInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmpSalaryInfoUpdateComponent,
    resolve: {
      empSalaryInfo: EmpSalaryInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmpSalaryInfoUpdateComponent,
    resolve: {
      empSalaryInfo: EmpSalaryInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(empSalaryInfoRoute)],
  exports: [RouterModule],
})
export class EmpSalaryInfoRoutingModule {}
