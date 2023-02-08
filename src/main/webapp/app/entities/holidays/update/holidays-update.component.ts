import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { HolidaysFormService, HolidaysFormGroup } from './holidays-form.service';
import { IHolidays } from '../holidays.model';
import { HolidaysService } from '../service/holidays.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

@Component({
  selector: 'jhi-holidays-update',
  templateUrl: './holidays-update.component.html',
})
export class HolidaysUpdateComponent implements OnInit {
  isSaving = false;
  holidays: IHolidays | null = null;

  companiesSharedCollection: ICompany[] = [];

  editForm: HolidaysFormGroup = this.holidaysFormService.createHolidaysFormGroup();

  constructor(
    protected holidaysService: HolidaysService,
    protected holidaysFormService: HolidaysFormService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ holidays }) => {
      this.holidays = holidays;
      if (holidays) {
        this.updateForm(holidays);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const holidays = this.holidaysFormService.getHolidays(this.editForm);
    if (holidays.id !== null) {
      this.subscribeToSaveResponse(this.holidaysService.update(holidays));
    } else {
      this.subscribeToSaveResponse(this.holidaysService.create(holidays));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHolidays>>): void {
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

  protected updateForm(holidays: IHolidays): void {
    this.holidays = holidays;
    this.holidaysFormService.resetForm(this.editForm, holidays);

    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      holidays.company
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.holidays?.company))
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }
}
