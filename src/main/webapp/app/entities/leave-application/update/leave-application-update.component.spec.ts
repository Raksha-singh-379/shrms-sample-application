import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LeaveApplicationFormService } from './leave-application-form.service';
import { LeaveApplicationService } from '../service/leave-application.service';
import { ILeaveApplication } from '../leave-application.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ILeavePolicy } from 'app/entities/leave-policy/leave-policy.model';
import { LeavePolicyService } from 'app/entities/leave-policy/service/leave-policy.service';

import { LeaveApplicationUpdateComponent } from './leave-application-update.component';

describe('LeaveApplication Management Update Component', () => {
  let comp: LeaveApplicationUpdateComponent;
  let fixture: ComponentFixture<LeaveApplicationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let leaveApplicationFormService: LeaveApplicationFormService;
  let leaveApplicationService: LeaveApplicationService;
  let employeeService: EmployeeService;
  let leavePolicyService: LeavePolicyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LeaveApplicationUpdateComponent],
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
      .overrideTemplate(LeaveApplicationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LeaveApplicationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    leaveApplicationFormService = TestBed.inject(LeaveApplicationFormService);
    leaveApplicationService = TestBed.inject(LeaveApplicationService);
    employeeService = TestBed.inject(EmployeeService);
    leavePolicyService = TestBed.inject(LeavePolicyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const leaveApplication: ILeaveApplication = { id: 456 };
      const employee: IEmployee = { id: 31850 };
      leaveApplication.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 37429 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leaveApplication });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LeavePolicy query and add missing value', () => {
      const leaveApplication: ILeaveApplication = { id: 456 };
      const leavePolicy: ILeavePolicy = { id: 60868 };
      leaveApplication.leavePolicy = leavePolicy;

      const leavePolicyCollection: ILeavePolicy[] = [{ id: 10272 }];
      jest.spyOn(leavePolicyService, 'query').mockReturnValue(of(new HttpResponse({ body: leavePolicyCollection })));
      const additionalLeavePolicies = [leavePolicy];
      const expectedCollection: ILeavePolicy[] = [...additionalLeavePolicies, ...leavePolicyCollection];
      jest.spyOn(leavePolicyService, 'addLeavePolicyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leaveApplication });
      comp.ngOnInit();

      expect(leavePolicyService.query).toHaveBeenCalled();
      expect(leavePolicyService.addLeavePolicyToCollectionIfMissing).toHaveBeenCalledWith(
        leavePolicyCollection,
        ...additionalLeavePolicies.map(expect.objectContaining)
      );
      expect(comp.leavePoliciesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const leaveApplication: ILeaveApplication = { id: 456 };
      const employee: IEmployee = { id: 76708 };
      leaveApplication.employee = employee;
      const leavePolicy: ILeavePolicy = { id: 98123 };
      leaveApplication.leavePolicy = leavePolicy;

      activatedRoute.data = of({ leaveApplication });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.leavePoliciesSharedCollection).toContain(leavePolicy);
      expect(comp.leaveApplication).toEqual(leaveApplication);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeaveApplication>>();
      const leaveApplication = { id: 123 };
      jest.spyOn(leaveApplicationFormService, 'getLeaveApplication').mockReturnValue(leaveApplication);
      jest.spyOn(leaveApplicationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leaveApplication });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leaveApplication }));
      saveSubject.complete();

      // THEN
      expect(leaveApplicationFormService.getLeaveApplication).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(leaveApplicationService.update).toHaveBeenCalledWith(expect.objectContaining(leaveApplication));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeaveApplication>>();
      const leaveApplication = { id: 123 };
      jest.spyOn(leaveApplicationFormService, 'getLeaveApplication').mockReturnValue({ id: null });
      jest.spyOn(leaveApplicationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leaveApplication: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leaveApplication }));
      saveSubject.complete();

      // THEN
      expect(leaveApplicationFormService.getLeaveApplication).toHaveBeenCalled();
      expect(leaveApplicationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeaveApplication>>();
      const leaveApplication = { id: 123 };
      jest.spyOn(leaveApplicationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leaveApplication });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(leaveApplicationService.update).toHaveBeenCalled();
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

    describe('compareLeavePolicy', () => {
      it('Should forward to leavePolicyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(leavePolicyService, 'compareLeavePolicy');
        comp.compareLeavePolicy(entity, entity2);
        expect(leavePolicyService.compareLeavePolicy).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
