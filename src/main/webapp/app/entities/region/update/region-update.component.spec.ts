import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RegionFormService } from './region-form.service';
import { RegionService } from '../service/region.service';
import { IRegion } from '../region.model';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

import { RegionUpdateComponent } from './region-update.component';

describe('Region Management Update Component', () => {
  let comp: RegionUpdateComponent;
  let fixture: ComponentFixture<RegionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let regionFormService: RegionFormService;
  let regionService: RegionService;
  let companyService: CompanyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RegionUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RegionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RegionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    regionFormService = TestBed.inject(RegionFormService);
    regionService = TestBed.inject(RegionService);
    companyService = TestBed.inject(CompanyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Company query and add missing value', () => {
      const region: IRegion = { id: 456 };
      const company: ICompany = { id: 28433 };
      region.company = company;

      const companyCollection: ICompany[] = [{ id: 66683 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ region });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining)
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const region: IRegion = { id: 456 };
      const company: ICompany = { id: 49558 };
      region.company = company;

      activatedRoute.data = of({ region });
      comp.ngOnInit();

      expect(comp.companiesSharedCollection).toContain(company);
      expect(comp.region).toEqual(region);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegion>>();
      const region = { id: 123 };
      jest.spyOn(regionFormService, 'getRegion').mockReturnValue(region);
      jest.spyOn(regionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ region });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: region }));
      saveSubject.complete();

      // THEN
      expect(regionFormService.getRegion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(regionService.update).toHaveBeenCalledWith(expect.objectContaining(region));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegion>>();
      const region = { id: 123 };
      jest.spyOn(regionFormService, 'getRegion').mockReturnValue({ id: null });
      jest.spyOn(regionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ region: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: region }));
      saveSubject.complete();

      // THEN
      expect(regionFormService.getRegion).toHaveBeenCalled();
      expect(regionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegion>>();
      const region = { id: 123 };
      jest.spyOn(regionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ region });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(regionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCompany', () => {
      it('Should forward to companyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyService, 'compareCompany');
        comp.compareCompany(entity, entity2);
        expect(companyService.compareCompany).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
