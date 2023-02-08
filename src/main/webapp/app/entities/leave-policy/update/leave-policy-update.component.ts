import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LeavePolicyFormService, LeavePolicyFormGroup } from './leave-policy-form.service';
import { ILeavePolicy } from '../leave-policy.model';
import { LeavePolicyService } from '../service/leave-policy.service';
import { EmployeeType } from 'app/entities/enumerations/employee-type.model';
import { GenderLeave } from 'app/entities/enumerations/gender-leave.model';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-leave-policy-update',
  templateUrl: './leave-policy-update.component.html',
})
export class LeavePolicyUpdateComponent implements OnInit {
  isSaving = false;
  leavePolicy: ILeavePolicy | null = null;
  employeeTypeValues = Object.keys(EmployeeType);
  genderLeaveValues = Object.keys(GenderLeave);
  statusValues = Object.keys(Status);

  editForm: LeavePolicyFormGroup = this.leavePolicyFormService.createLeavePolicyFormGroup();

  constructor(
    protected leavePolicyService: LeavePolicyService,
    protected leavePolicyFormService: LeavePolicyFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leavePolicy }) => {
      this.leavePolicy = leavePolicy;
      if (leavePolicy) {
        this.updateForm(leavePolicy);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leavePolicy = this.leavePolicyFormService.getLeavePolicy(this.editForm);
    if (leavePolicy.id !== null) {
      this.subscribeToSaveResponse(this.leavePolicyService.update(leavePolicy));
    } else {
      this.subscribeToSaveResponse(this.leavePolicyService.create(leavePolicy));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeavePolicy>>): void {
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

  protected updateForm(leavePolicy: ILeavePolicy): void {
    this.leavePolicy = leavePolicy;
    this.leavePolicyFormService.resetForm(this.editForm, leavePolicy);
  }
}
