import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmpSalaryInfoComponent } from './list/emp-salary-info.component';
import { EmpSalaryInfoDetailComponent } from './detail/emp-salary-info-detail.component';
import { EmpSalaryInfoUpdateComponent } from './update/emp-salary-info-update.component';
import { EmpSalaryInfoDeleteDialogComponent } from './delete/emp-salary-info-delete-dialog.component';
import { EmpSalaryInfoRoutingModule } from './route/emp-salary-info-routing.module';

@NgModule({
  imports: [SharedModule, EmpSalaryInfoRoutingModule],
  declarations: [EmpSalaryInfoComponent, EmpSalaryInfoDetailComponent, EmpSalaryInfoUpdateComponent, EmpSalaryInfoDeleteDialogComponent],
})
export class EmpSalaryInfoModule {}
