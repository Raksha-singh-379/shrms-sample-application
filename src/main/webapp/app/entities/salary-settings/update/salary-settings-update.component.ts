import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SalarySettingsFormService, SalarySettingsFormGroup } from './salary-settings-form.service';
import { ISalarySettings } from '../salary-settings.model';
import { SalarySettingsService } from '../service/salary-settings.service';

@Component({
  selector: 'jhi-salary-settings-update',
  templateUrl: './salary-settings-update.component.html',
})
export class SalarySettingsUpdateComponent implements OnInit {
  isSaving = false;
  salarySettings: ISalarySettings | null = null;

  editForm: SalarySettingsFormGroup = this.salarySettingsFormService.createSalarySettingsFormGroup();

  constructor(
    protected salarySettingsService: SalarySettingsService,
    protected salarySettingsFormService: SalarySettingsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salarySettings }) => {
      this.salarySettings = salarySettings;
      if (salarySettings) {
        this.updateForm(salarySettings);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salarySettings = this.salarySettingsFormService.getSalarySettings(this.editForm);
    if (salarySettings.id !== null) {
      this.subscribeToSaveResponse(this.salarySettingsService.update(salarySettings));
    } else {
      this.subscribeToSaveResponse(this.salarySettingsService.create(salarySettings));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalarySettings>>): void {
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

  protected updateForm(salarySettings: ISalarySettings): void {
    this.salarySettings = salarySettings;
    this.salarySettingsFormService.resetForm(this.editForm, salarySettings);
  }
}
