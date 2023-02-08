import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmpSalaryInfoFormService } from './emp-salary-info-form.service';
import { EmpSalaryInfoService } from '../service/emp-salary-info.service';
import { IEmpSalaryInfo } from '../emp-salary-info.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { EmpSalaryInfoUpdateComponent } from './emp-salary-info-update.component';

describe('EmpSalaryInfo Management Update Component', () => {
  let comp: EmpSalaryInfoUpdateComponent;
  let fixture: ComponentFixture<EmpSalaryInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empSalaryInfoFormService: EmpSalaryInfoFormService;
  let empSalaryInfoService: EmpSalaryInfoService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmpSalaryInfoUpdateComponent],
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
      .overrideTemplate(EmpSalaryInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpSalaryInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empSalaryInfoFormService = TestBed.inject(EmpSalaryInfoFormService);
    empSalaryInfoService = TestBed.inject(EmpSalaryInfoService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const empSalaryInfo: IEmpSalaryInfo = { id: 456 };
      const employee: IEmployee = { id: 18581 };
      empSalaryInfo.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 4936 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empSalaryInfo });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const empSalaryInfo: IEmpSalaryInfo = { id: 456 };
      const employee: IEmployee = { id: 86564 };
      empSalaryInfo.employee = employee;

      activatedRoute.data = of({ empSalaryInfo });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.empSalaryInfo).toEqual(empSalaryInfo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpSalaryInfo>>();
      const empSalaryInfo = { id: 123 };
      jest.spyOn(empSalaryInfoFormService, 'getEmpSalaryInfo').mockReturnValue(empSalaryInfo);
      jest.spyOn(empSalaryInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empSalaryInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empSalaryInfo }));
      saveSubject.complete();

      // THEN
      expect(empSalaryInfoFormService.getEmpSalaryInfo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empSalaryInfoService.update).toHaveBeenCalledWith(expect.objectContaining(empSalaryInfo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpSalaryInfo>>();
      const empSalaryInfo = { id: 123 };
      jest.spyOn(empSalaryInfoFormService, 'getEmpSalaryInfo').mockReturnValue({ id: null });
      jest.spyOn(empSalaryInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empSalaryInfo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empSalaryInfo }));
      saveSubject.complete();

      // THEN
      expect(empSalaryInfoFormService.getEmpSalaryInfo).toHaveBeenCalled();
      expect(empSalaryInfoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpSalaryInfo>>();
      const empSalaryInfo = { id: 123 };
      jest.spyOn(empSalaryInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empSalaryInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empSalaryInfoService.update).toHaveBeenCalled();
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
