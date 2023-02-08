import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EducationFormService, EducationFormGroup } from './education-form.service';
import { IEducation } from '../education.model';
import { EducationService } from '../service/education.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { Degree } from 'app/entities/enumerations/degree.model';

@Component({
  selector: 'jhi-education-update',
  templateUrl: './education-update.component.html',
})
export class EducationUpdateComponent implements OnInit {
  isSaving = false;
  education: IEducation | null = null;
  degreeValues = Object.keys(Degree);

  employeesSharedCollection: IEmployee[] = [];

  editForm: EducationFormGroup = this.educationFormService.createEducationFormGroup();

  constructor(
    protected educationService: EducationService,
    protected educationFormService: EducationFormService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ education }) => {
      this.education = education;
      if (education) {
        this.updateForm(education);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const education = this.educationFormService.getEducation(this.editForm);
    if (education.id !== null) {
      this.subscribeToSaveResponse(this.educationService.update(education));
    } else {
      this.subscribeToSaveResponse(this.educationService.create(education));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEducation>>): void {
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

  protected updateForm(education: IEducation): void {
    this.education = education;
    this.educationFormService.resetForm(this.editForm, education);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      education.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.education?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
