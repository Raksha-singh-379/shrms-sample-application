import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DesignationFormService } from './designation-form.service';
import { DesignationService } from '../service/designation.service';
import { IDesignation } from '../designation.model';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

import { DesignationUpdateComponent } from './designation-update.component';

describe('Designation Management Update Component', () => {
  let comp: DesignationUpdateComponent;
  let fixture: ComponentFixture<DesignationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let designationFormService: DesignationFormService;
  let designationService: DesignationService;
  let companyService: CompanyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DesignationUpdateComponent],
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
      .overrideTemplate(DesignationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DesignationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    designationFormService = TestBed.inject(DesignationFormService);
    designationService = TestBed.inject(DesignationService);
    companyService = TestBed.inject(CompanyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Company query and add missing value', () => {
      const designation: IDesignation = { id: 456 };
      const company: ICompany = { id: 20090 };
      designation.company = company;

      const companyCollection: ICompany[] = [{ id: 81634 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ designation });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining)
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const designation: IDesignation = { id: 456 };
      const company: ICompany = { id: 13596 };
      designation.company = company;

      activatedRoute.data = of({ designation });
      comp.ngOnInit();

      expect(comp.companiesSharedCollection).toContain(company);
      expect(comp.designation).toEqual(designation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDesignation>>();
      const designation = { id: 123 };
      jest.spyOn(designationFormService, 'getDesignation').mockReturnValue(designation);
      jest.spyOn(designationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ designation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: designation }));
      saveSubject.complete();

      // THEN
      expect(designationFormService.getDesignation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(designationService.update).toHaveBeenCalledWith(expect.objectContaining(designation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDesignation>>();
      const designation = { id: 123 };
      jest.spyOn(designationFormService, 'getDesignation').mockReturnValue({ id: null });
      jest.spyOn(designationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ designation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: designation }));
      saveSubject.complete();

      // THEN
      expect(designationFormService.getDesignation).toHaveBeenCalled();
      expect(designationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDesignation>>();
      const designation = { id: 123 };
      jest.spyOn(designationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ designation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(designationService.update).toHaveBeenCalled();
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
