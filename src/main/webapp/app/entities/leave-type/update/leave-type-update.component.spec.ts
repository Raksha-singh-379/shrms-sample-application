import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LeaveTypeFormService } from './leave-type-form.service';
import { LeaveTypeService } from '../service/leave-type.service';
import { ILeaveType } from '../leave-type.model';

import { LeaveTypeUpdateComponent } from './leave-type-update.component';

describe('LeaveType Management Update Component', () => {
  let comp: LeaveTypeUpdateComponent;
  let fixture: ComponentFixture<LeaveTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let leaveTypeFormService: LeaveTypeFormService;
  let leaveTypeService: LeaveTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LeaveTypeUpdateComponent],
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
      .overrideTemplate(LeaveTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LeaveTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    leaveTypeFormService = TestBed.inject(LeaveTypeFormService);
    leaveTypeService = TestBed.inject(LeaveTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const leaveType: ILeaveType = { id: 456 };

      activatedRoute.data = of({ leaveType });
      comp.ngOnInit();

      expect(comp.leaveType).toEqual(leaveType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeaveType>>();
      const leaveType = { id: 123 };
      jest.spyOn(leaveTypeFormService, 'getLeaveType').mockReturnValue(leaveType);
      jest.spyOn(leaveTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leaveType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leaveType }));
      saveSubject.complete();

      // THEN
      expect(leaveTypeFormService.getLeaveType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(leaveTypeService.update).toHaveBeenCalledWith(expect.objectContaining(leaveType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeaveType>>();
      const leaveType = { id: 123 };
      jest.spyOn(leaveTypeFormService, 'getLeaveType').mockReturnValue({ id: null });
      jest.spyOn(leaveTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leaveType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leaveType }));
      saveSubject.complete();

      // THEN
      expect(leaveTypeFormService.getLeaveType).toHaveBeenCalled();
      expect(leaveTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeaveType>>();
      const leaveType = { id: 123 };
      jest.spyOn(leaveTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leaveType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(leaveTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
