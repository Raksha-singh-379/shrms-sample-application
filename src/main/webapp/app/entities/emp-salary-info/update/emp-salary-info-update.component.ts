import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EmpSalaryInfoFormService, EmpSalaryInfoFormGroup } from './emp-salary-info-form.service';
import { IEmpSalaryInfo } from '../emp-salary-info.model';
import { EmpSalaryInfoService } from '../service/emp-salary-info.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { SalaryBasis } from 'app/entities/enumerations/salary-basis.model';

@Component({
  selector: 'jhi-emp-salary-info-update',
  templateUrl: './emp-salary-info-update.component.html',
})
export class EmpSalaryInfoUpdateComponent implements OnInit {
  isSaving = false;
  empSalaryInfo: IEmpSalaryInfo | null = null;
  salaryBasisValues = Object.keys(SalaryBasis);

  employeesSharedCollection: IEmployee[] = [];

  editForm: EmpSalaryInfoFormGroup = this.empSalaryInfoFormService.createEmpSalaryInfoFormGroup();

  constructor(
    protected empSalaryInfoService: EmpSalaryInfoService,
    protected empSalaryInfoFormService: EmpSalaryInfoFormService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empSalaryInfo }) => {
      this.empSalaryInfo = empSalaryInfo;
      if (empSalaryInfo) {
        this.updateForm(empSalaryInfo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empSalaryInfo = this.empSalaryInfoFormService.getEmpSalaryInfo(this.editForm);
    if (empSalaryInfo.id !== null) {
      this.subscribeToSaveResponse(this.empSalaryInfoService.update(empSalaryInfo));
    } else {
      this.subscribeToSaveResponse(this.empSalaryInfoService.create(empSalaryInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpSalaryInfo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(empSalaryInfo: IEmpSalaryInfo): void {
    this.empSalaryInfo = empSalaryInfo;
    this.empSalaryInfoFormService.resetForm(this.editForm, empSalaryInfo);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      empSalaryInfo.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.empSalaryInfo?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
