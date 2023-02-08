import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FamilyInfoFormService, FamilyInfoFormGroup } from './family-info-form.service';
import { IFamilyInfo } from '../family-info.model';
import { FamilyInfoService } from '../service/family-info.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { Relationship } from 'app/entities/enumerations/relationship.model';

@Component({
  selector: 'jhi-family-info-update',
  templateUrl: './family-info-update.component.html',
})
export class FamilyInfoUpdateComponent implements OnInit {
  isSaving = false;
  familyInfo: IFamilyInfo | null = null;
  relationshipValues = Object.keys(Relationship);

  employeesSharedCollection: IEmployee[] = [];

  editForm: FamilyInfoFormGroup = this.familyInfoFormService.createFamilyInfoFormGroup();

  constructor(
    protected familyInfoService: FamilyInfoService,
    protected familyInfoFormService: FamilyInfoFormService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ familyInfo }) => {
      this.familyInfo = familyInfo;
      if (familyInfo) {
        this.updateForm(familyInfo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const familyInfo = this.familyInfoFormService.getFamilyInfo(this.editForm);
    if (familyInfo.id !== null) {
      this.subscribeToSaveResponse(this.familyInfoService.update(familyInfo));
    } else {
      this.subscribeToSaveResponse(this.familyInfoService.create(familyInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilyInfo>>): void {
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

  protected updateForm(familyInfo: IFamilyInfo): void {
    this.familyInfo = familyInfo;
    this.familyInfoFormService.resetForm(this.editForm, familyInfo);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      familyInfo.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.familyInfo?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
