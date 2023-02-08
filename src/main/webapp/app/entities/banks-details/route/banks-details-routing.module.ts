import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BanksDetailsComponent } from '../list/banks-details.component';
import { BanksDetailsDetailComponent } from '../detail/banks-details-detail.component';
import { BanksDetailsUpdateComponent } from '../update/banks-details-update.component';
import { BanksDetailsRoutingResolveService } from './banks-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const banksDetailsRoute: Routes = [
  {
    path: '',
    component: BanksDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BanksDetailsDetailComponent,
    resolve: {
      banksDetails: BanksDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BanksDetailsUpdateComponent,
    resolve: {
      banksDetails: BanksDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BanksDetailsUpdateComponent,
    resolve: {
      banksDetails: BanksDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(banksDetailsRoute)],
  exports: [RouterModule],
})
export class BanksDetailsRoutingModule {}
