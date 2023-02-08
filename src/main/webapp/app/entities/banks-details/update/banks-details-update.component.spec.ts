import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BanksDetailsFormService } from './banks-details-form.service';
import { BanksDetailsService } from '../service/banks-details.service';
import { IBanksDetails } from '../banks-details.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { BanksDetailsUpdateComponent } from './banks-details-update.component';

describe('BanksDetails Management Update Component', () => {
  let comp: BanksDetailsUpdateComponent;
  let fixture: ComponentFixture<BanksDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let banksDetailsFormService: BanksDetailsFormService;
  let banksDetailsService: BanksDetailsService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BanksDetailsUpdateComponent],
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
      .overrideTemplate(BanksDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BanksDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    banksDetailsFormService = TestBed.inject(BanksDetailsFormService);
    banksDetailsService = TestBed.inject(BanksDetailsService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const banksDetails: IBanksDetails = { id: 456 };
      const employee: IEmployee = { id: 7150 };
      banksDetails.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 12982 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ banksDetails });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const banksDetails: IBanksDetails = { id: 456 };
      const employee: IEmployee = { id: 46905 };
      banksDetails.employee = employee;

      activatedRoute.data = of({ banksDetails });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.banksDetails).toEqual(banksDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBanksDetails>>();
      const banksDetails = { id: 123 };
      jest.spyOn(banksDetailsFormService, 'getBanksDetails').mockReturnValue(banksDetails);
      jest.spyOn(banksDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ banksDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: banksDetails }));
      saveSubject.complete();

      // THEN
      expect(banksDetailsFormService.getBanksDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(banksDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(banksDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBanksDetails>>();
      const banksDetails = { id: 123 };
      jest.spyOn(banksDetailsFormService, 'getBanksDetails').mockReturnValue({ id: null });
      jest.spyOn(banksDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ banksDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: banksDetails }));
      saveSubject.complete();

      // THEN
      expect(banksDetailsFormService.getBanksDetails).toHaveBeenCalled();
      expect(banksDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBanksDetails>>();
      const banksDetails = { id: 123 };
      jest.spyOn(banksDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ banksDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(banksDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
