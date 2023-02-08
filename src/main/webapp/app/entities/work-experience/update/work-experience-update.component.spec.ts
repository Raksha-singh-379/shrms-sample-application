import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WorkExperienceFormService } from './work-experience-form.service';
import { WorkExperienceService } from '../service/work-experience.service';
import { IWorkExperience } from '../work-experience.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { WorkExperienceUpdateComponent } from './work-experience-update.component';

describe('WorkExperience Management Update Component', () => {
  let comp: WorkExperienceUpdateComponent;
  let fixture: ComponentFixture<WorkExperienceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let workExperienceFormService: WorkExperienceFormService;
  let workExperienceService: WorkExperienceService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WorkExperienceUpdateComponent],
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
      .overrideTemplate(WorkExperienceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WorkExperienceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    workExperienceFormService = TestBed.inject(WorkExperienceFormService);
    workExperienceService = TestBed.inject(WorkExperienceService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const workExperience: IWorkExperience = { id: 456 };
      const employee: IEmployee = { id: 5634 };
      workExperience.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 67271 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workExperience });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const workExperience: IWorkExperience = { id: 456 };
      const employee: IEmployee = { id: 6870 };
      workExperience.employee = employee;

      activatedRoute.data = of({ workExperience });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.workExperience).toEqual(workExperience);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkExperience>>();
      const workExperience = { id: 123 };
      jest.spyOn(workExperienceFormService, 'getWorkExperience').mockReturnValue(workExperience);
      jest.spyOn(workExperienceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workExperience });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workExperience }));
      saveSubject.complete();

      // THEN
      expect(workExperienceFormService.getWorkExperience).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(workExperienceService.update).toHaveBeenCalledWith(expect.objectContaining(workExperience));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkExperience>>();
      const workExperience = { id: 123 };
      jest.spyOn(workExperienceFormService, 'getWorkExperience').mockReturnValue({ id: null });
      jest.spyOn(workExperienceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workExperience: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workExperience }));
      saveSubject.complete();

      // THEN
      expect(workExperienceFormService.getWorkExperience).toHaveBeenCalled();
      expect(workExperienceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkExperience>>();
      const workExperience = { id: 123 };
      jest.spyOn(workExperienceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workExperience });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(workExperienceService.update).toHaveBeenCalled();
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
