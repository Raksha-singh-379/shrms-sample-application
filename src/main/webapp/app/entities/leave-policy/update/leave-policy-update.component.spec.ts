import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LeavePolicyFormService } from './leave-policy-form.service';
import { LeavePolicyService } from '../service/leave-policy.service';
import { ILeavePolicy } from '../leave-policy.model';

import { LeavePolicyUpdateComponent } from './leave-policy-update.component';

describe('LeavePolicy Management Update Component', () => {
  let comp: LeavePolicyUpdateComponent;
  let fixture: ComponentFixture<LeavePolicyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let leavePolicyFormService: LeavePolicyFormService;
  let leavePolicyService: LeavePolicyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LeavePolicyUpdateComponent],
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
      .overrideTemplate(LeavePolicyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LeavePolicyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    leavePolicyFormService = TestBed.inject(LeavePolicyFormService);
    leavePolicyService = TestBed.inject(LeavePolicyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const leavePolicy: ILeavePolicy = { id: 456 };

      activatedRoute.data = of({ leavePolicy });
      comp.ngOnInit();

      expect(comp.leavePolicy).toEqual(leavePolicy);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeavePolicy>>();
      const leavePolicy = { id: 123 };
      jest.spyOn(leavePolicyFormService, 'getLeavePolicy').mockReturnValue(leavePolicy);
      jest.spyOn(leavePolicyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leavePolicy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leavePolicy }));
      saveSubject.complete();

      // THEN
      expect(leavePolicyFormService.getLeavePolicy).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(leavePolicyService.update).toHaveBeenCalledWith(expect.objectContaining(leavePolicy));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeavePolicy>>();
      const leavePolicy = { id: 123 };
      jest.spyOn(leavePolicyFormService, 'getLeavePolicy').mockReturnValue({ id: null });
      jest.spyOn(leavePolicyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leavePolicy: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leavePolicy }));
      saveSubject.complete();

      // THEN
      expect(leavePolicyFormService.getLeavePolicy).toHaveBeenCalled();
      expect(leavePolicyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeavePolicy>>();
      const leavePolicy = { id: 123 };
      jest.spyOn(leavePolicyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leavePolicy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(leavePolicyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
