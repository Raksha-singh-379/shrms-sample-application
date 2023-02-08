import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LeaveApplicationFormService, LeaveApplicationFormGroup } from './leave-application-form.service';
import { ILeaveApplication } from '../leave-application.model';
import { LeaveApplicationService } from '../service/leave-application.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ILeavePolicy } from 'app/entities/leave-policy/leave-policy.model';
import { LeavePolicyService } from 'app/entities/leave-policy/service/leave-policy.service';
import { Status } from 'app/entities/enumerations/status.model';
import { LeaveStatus } from 'app/entities/enumerations/leave-status.model';

@Component({
  selector: 'jhi-leave-application-update',
  templateUrl: './leave-application-update.component.html',
})
export class LeaveApplicationUpdateComponent implements OnInit {
  isSaving = false;
  leaveApplication: ILeaveApplication | null = null;
  statusValues = Object.keys(Status);
  leaveStatusValues = Object.keys(LeaveStatus);

  employeesSharedCollection: IEmployee[] = [];
  leavePoliciesSharedCollection: ILeavePolicy[] = [];

  editForm: LeaveApplicationFormGroup = this.leaveApplicationFormService.createLeaveApplicationFormGroup();

  constructor(
    protected leaveApplicationService: LeaveApplicationService,
    protected leaveApplicationFormService: LeaveApplicationFormService,
    protected employeeService: EmployeeService,
    protected leavePolicyService: LeavePolicyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  compareLeavePolicy = (o1: ILeavePolicy | null, o2: ILeavePolicy | null): boolean => this.leavePolicyService.compareLeavePolicy(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leaveApplication }) => {
      this.leaveApplication = leaveApplication;
      if (leaveApplication) {
        this.updateForm(leaveApplication);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leaveApplication = this.leaveApplicationFormService.getLeaveApplication(this.editForm);
    if (leaveApplication.id !== null) {
      this.subscribeToSaveResponse(this.leaveApplicationService.update(leaveApplication));
    } else {
      this.subscribeToSaveResponse(this.leaveApplicationService.create(leaveApplication));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveApplication>>): void {
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

  protected updateForm(leaveApplication: ILeaveApplication): void {
    this.leaveApplication = leaveApplication;
    this.leaveApplicationFormService.resetForm(this.editForm, leaveApplication);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      leaveApplication.employee
    );
    this.leavePoliciesSharedCollection = this.leavePolicyService.addLeavePolicyToCollectionIfMissing<ILeavePolicy>(
      this.leavePoliciesSharedCollection,
      leaveApplication.leavePolicy
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.leaveApplication?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));

    this.leavePolicyService
      .query()
      .pipe(map((res: HttpResponse<ILeavePolicy[]>) => res.body ?? []))
      .pipe(
        map((leavePolicies: ILeavePolicy[]) =>
          this.leavePolicyService.addLeavePolicyToCollectionIfMissing<ILeavePolicy>(leavePolicies, this.leaveApplication?.leavePolicy)
        )
      )
      .subscribe((leavePolicies: ILeavePolicy[]) => (this.leavePoliciesSharedCollection = leavePolicies));
  }
}
