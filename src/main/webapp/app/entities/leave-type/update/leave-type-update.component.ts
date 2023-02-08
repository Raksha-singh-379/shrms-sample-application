import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LeaveTypeFormService, LeaveTypeFormGroup } from './leave-type-form.service';
import { ILeaveType } from '../leave-type.model';
import { LeaveTypeService } from '../service/leave-type.service';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-leave-type-update',
  templateUrl: './leave-type-update.component.html',
})
export class LeaveTypeUpdateComponent implements OnInit {
  isSaving = false;
  leaveType: ILeaveType | null = null;
  statusValues = Object.keys(Status);

  editForm: LeaveTypeFormGroup = this.leaveTypeFormService.createLeaveTypeFormGroup();

  constructor(
    protected leaveTypeService: LeaveTypeService,
    protected leaveTypeFormService: LeaveTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leaveType }) => {
      this.leaveType = leaveType;
      if (leaveType) {
        this.updateForm(leaveType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leaveType = this.leaveTypeFormService.getLeaveType(this.editForm);
    if (leaveType.id !== null) {
      this.subscribeToSaveResponse(this.leaveTypeService.update(leaveType));
    } else {
      this.subscribeToSaveResponse(this.leaveTypeService.create(leaveType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveType>>): void {
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

  protected updateForm(leaveType: ILeaveType): void {
    this.leaveType = leaveType;
    this.leaveTypeFormService.resetForm(this.editForm, leaveType);
  }
}
