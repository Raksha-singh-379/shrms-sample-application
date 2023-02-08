import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BanksDetailsComponent } from './list/banks-details.component';
import { BanksDetailsDetailComponent } from './detail/banks-details-detail.component';
import { BanksDetailsUpdateComponent } from './update/banks-details-update.component';
import { BanksDetailsDeleteDialogComponent } from './delete/banks-details-delete-dialog.component';
import { BanksDetailsRoutingModule } from './route/banks-details-routing.module';

@NgModule({
  imports: [SharedModule, BanksDetailsRoutingModule],
  declarations: [BanksDetailsComponent, BanksDetailsDetailComponent, BanksDetailsUpdateComponent, BanksDetailsDeleteDialogComponent],
})
export class BanksDetailsModule {}
