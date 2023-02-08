import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FamilyInfoFormService } from './family-info-form.service';
import { FamilyInfoService } from '../service/family-info.service';
import { IFamilyInfo } from '../family-info.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { FamilyInfoUpdateComponent } from './family-info-update.component';

describe('FamilyInfo Management Update Component', () => {
  let comp: FamilyInfoUpdateComponent;
  let fixture: ComponentFixture<FamilyInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let familyInfoFormService: FamilyInfoFormService;
  let familyInfoService: FamilyInfoService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FamilyInfoUpdateComponent],
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
      .overrideTemplate(FamilyInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FamilyInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    familyInfoFormService = TestBed.inject(FamilyInfoFormService);
    familyInfoService = TestBed.inject(FamilyInfoService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const familyInfo: IFamilyInfo = { id: 456 };
      const employee: IEmployee = { id: 82097 };
      familyInfo.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 89116 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ familyInfo });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const familyInfo: IFamilyInfo = { id: 456 };
      const employee: IEmployee = { id: 24297 };
      familyInfo.employee = employee;

      activatedRoute.data = of({ familyInfo });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.familyInfo).toEqual(familyInfo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFamilyInfo>>();
      const familyInfo = { id: 123 };
      jest.spyOn(familyInfoFormService, 'getFamilyInfo').mockReturnValue(familyInfo);
      jest.spyOn(familyInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ familyInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: familyInfo }));
      saveSubject.complete();

      // THEN
      expect(familyInfoFormService.getFamilyInfo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(familyInfoService.update).toHaveBeenCalledWith(expect.objectContaining(familyInfo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFamilyInfo>>();
      const familyInfo = { id: 123 };
      jest.spyOn(familyInfoFormService, 'getFamilyInfo').mockReturnValue({ id: null });
      jest.spyOn(familyInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ familyInfo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: familyInfo }));
      saveSubject.complete();

      // THEN
      expect(familyInfoFormService.getFamilyInfo).toHaveBeenCalled();
      expect(familyInfoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFamilyInfo>>();
      const familyInfo = { id: 123 };
      jest.spyOn(familyInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ familyInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(familyInfoService.update).toHaveBeenCalled();
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
