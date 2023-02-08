import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LeaveTranscationFormService, LeaveTranscationFormGroup } from './leave-transcation-form.service';
import { ILeaveTranscation } from '../leave-transcation.model';
import { LeaveTranscationService } from '../service/leave-transcation.service';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-leave-transcation-update',
  templateUrl: './leave-transcation-update.component.html',
})
export class LeaveTranscationUpdateComponent implements OnInit {
  isSaving = false;
  leaveTranscation: ILeaveTranscation | null = null;
  statusValues = Object.keys(Status);

  editForm: LeaveTranscationFormGroup = this.leaveTranscationFormService.createLeaveTranscationFormGroup();

  constructor(
    protected leaveTranscationService: LeaveTranscationService,
    protected leaveTranscationFormService: LeaveTranscationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leaveTranscation }) => {
      this.leaveTranscation = leaveTranscation;
      if (leaveTranscation) {
        this.updateForm(leaveTranscation);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leaveTranscation = this.leaveTranscationFormService.getLeaveTranscation(this.editForm);
    if (leaveTranscation.id !== null) {
      this.subscribeToSaveResponse(this.leaveTranscationService.update(leaveTranscation));
    } else {
      this.subscribeToSaveResponse(this.leaveTranscationService.create(leaveTranscation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveTranscation>>): void {
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

  protected updateForm(leaveTranscation: ILeaveTranscation): void {
    this.leaveTranscation = leaveTranscation;
    this.leaveTranscationFormService.resetForm(this.editForm, leaveTranscation);
  }
}
