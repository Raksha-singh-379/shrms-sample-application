import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DesignationFormService, DesignationFormGroup } from './designation-form.service';
import { IDesignation } from '../designation.model';
import { DesignationService } from '../service/designation.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

@Component({
  selector: 'jhi-designation-update',
  templateUrl: './designation-update.component.html',
})
export class DesignationUpdateComponent implements OnInit {
  isSaving = false;
  designation: IDesignation | null = null;

  companiesSharedCollection: ICompany[] = [];

  editForm: DesignationFormGroup = this.designationFormService.createDesignationFormGroup();

  constructor(
    protected designationService: DesignationService,
    protected designationFormService: DesignationFormService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ designation }) => {
      this.designation = designation;
      if (designation) {
        this.updateForm(designation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const designation = this.designationFormService.getDesignation(this.editForm);
    if (designation.id !== null) {
      this.subscribeToSaveResponse(this.designationService.update(designation));
    } else {
      this.subscribeToSaveResponse(this.designationService.create(designation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDesignation>>): void {
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

  protected updateForm(designation: IDesignation): void {
    this.designation = designation;
    this.designationFormService.resetForm(this.editForm, designation);

    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      designation.company
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.designation?.company))
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }
}
