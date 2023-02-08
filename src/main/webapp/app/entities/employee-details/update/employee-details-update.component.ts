import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EmployeeDetailsFormService, EmployeeDetailsFormGroup } from './employee-details-form.service';
import { IEmployeeDetails } from '../employee-details.model';
import { EmployeeDetailsService } from '../service/employee-details.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';

@Component({
  selector: 'jhi-employee-details-update',
  templateUrl: './employee-details-update.component.html',
})
export class EmployeeDetailsUpdateComponent implements OnInit {
  isSaving = false;
  employeeDetails: IEmployeeDetails | null = null;
  maritalStatusValues = Object.keys(MaritalStatus);
  bloodGroupValues = Object.keys(BloodGroup);

  employeesSharedCollection: IEmployee[] = [];

  editForm: EmployeeDetailsFormGroup = this.employeeDetailsFormService.createEmployeeDetailsFormGroup();

  constructor(
    protected employeeDetailsService: EmployeeDetailsService,
    protected employeeDetailsFormService: EmployeeDetailsFormService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeDetails }) => {
      this.employeeDetails = employeeDetails;
      if (employeeDetails) {
        this.updateForm(employeeDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeDetails = this.employeeDetailsFormService.getEmployeeDetails(this.editForm);
    if (employeeDetails.id !== null) {
      this.subscribeToSaveResponse(this.employeeDetailsService.update(employeeDetails));
    } else {
      this.subscribeToSaveResponse(this.employeeDetailsService.create(employeeDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeDetails>>): void {
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

  protected updateForm(employeeDetails: IEmployeeDetails): void {
    this.employeeDetails = employeeDetails;
    this.employeeDetailsFormService.resetForm(this.editForm, employeeDetails);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      employeeDetails.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.employeeDetails?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
