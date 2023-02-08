import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmployeeDetailsFormService } from './employee-details-form.service';
import { EmployeeDetailsService } from '../service/employee-details.service';
import { IEmployeeDetails } from '../employee-details.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { EmployeeDetailsUpdateComponent } from './employee-details-update.component';

describe('EmployeeDetails Management Update Component', () => {
  let comp: EmployeeDetailsUpdateComponent;
  let fixture: ComponentFixture<EmployeeDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeeDetailsFormService: EmployeeDetailsFormService;
  let employeeDetailsService: EmployeeDetailsService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmployeeDetailsUpdateComponent],
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
      .overrideTemplate(EmployeeDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeeDetailsFormService = TestBed.inject(EmployeeDetailsFormService);
    employeeDetailsService = TestBed.inject(EmployeeDetailsService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const employeeDetails: IEmployeeDetails = { id: 456 };
      const employee: IEmployee = { id: 8195 };
      employeeDetails.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 11687 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employeeDetails });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employeeDetails: IEmployeeDetails = { id: 456 };
      const employee: IEmployee = { id: 89484 };
      employeeDetails.employee = employee;

      activatedRoute.data = of({ employeeDetails });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.employeeDetails).toEqual(employeeDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeDetails>>();
      const employeeDetails = { id: 123 };
      jest.spyOn(employeeDetailsFormService, 'getEmployeeDetails').mockReturnValue(employeeDetails);
      jest.spyOn(employeeDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employeeDetails }));
      saveSubject.complete();

      // THEN
      expect(employeeDetailsFormService.getEmployeeDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeeDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(employeeDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeDetails>>();
      const employeeDetails = { id: 123 };
      jest.spyOn(employeeDetailsFormService, 'getEmployeeDetails').mockReturnValue({ id: null });
      jest.spyOn(employeeDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employeeDetails }));
      saveSubject.complete();

      // THEN
      expect(employeeDetailsFormService.getEmployeeDetails).toHaveBeenCalled();
      expect(employeeDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeDetails>>();
      const employeeDetails = { id: 123 };
      jest.spyOn(employeeDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeeDetailsService.update).toHaveBeenCalled();
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
