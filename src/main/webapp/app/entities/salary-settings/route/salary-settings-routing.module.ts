import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SalarySettingsComponent } from '../list/salary-settings.component';
import { SalarySettingsDetailComponent } from '../detail/salary-settings-detail.component';
import { SalarySettingsUpdateComponent } from '../update/salary-settings-update.component';
import { SalarySettingsRoutingResolveService } from './salary-settings-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const salarySettingsRoute: Routes = [
  {
    path: '',
    component: SalarySettingsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SalarySettingsDetailComponent,
    resolve: {
      salarySettings: SalarySettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SalarySettingsUpdateComponent,
    resolve: {
      salarySettings: SalarySettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SalarySettingsUpdateComponent,
    resolve: {
      salarySettings: SalarySettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(salarySettingsRoute)],
  exports: [RouterModule],
})
export class SalarySettingsRoutingModule {}
