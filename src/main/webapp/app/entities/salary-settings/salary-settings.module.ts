import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SalarySettingsComponent } from './list/salary-settings.component';
import { SalarySettingsDetailComponent } from './detail/salary-settings-detail.component';
import { SalarySettingsUpdateComponent } from './update/salary-settings-update.component';
import { SalarySettingsDeleteDialogComponent } from './delete/salary-settings-delete-dialog.component';
import { SalarySettingsRoutingModule } from './route/salary-settings-routing.module';

@NgModule({
  imports: [SharedModule, SalarySettingsRoutingModule],
  declarations: [
    SalarySettingsComponent,
    SalarySettingsDetailComponent,
    SalarySettingsUpdateComponent,
    SalarySettingsDeleteDialogComponent,
  ],
})
export class SalarySettingsModule {}
