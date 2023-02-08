import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LeaveTranscationFormService } from './leave-transcation-form.service';
import { LeaveTranscationService } from '../service/leave-transcation.service';
import { ILeaveTranscation } from '../leave-transcation.model';

import { LeaveTranscationUpdateComponent } from './leave-transcation-update.component';

describe('LeaveTranscation Management Update Component', () => {
  let comp: LeaveTranscationUpdateComponent;
  let fixture: ComponentFixture<LeaveTranscationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let leaveTranscationFormService: LeaveTranscationFormService;
  let leaveTranscationService: LeaveTranscationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LeaveTranscationUpdateComponent],
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
      .overrideTemplate(LeaveTranscationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LeaveTranscationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    leaveTranscationFormService = TestBed.inject(LeaveTranscationFormService);
    leaveTranscationService = TestBed.inject(LeaveTranscationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const leaveTranscation: ILeaveTranscation = { id: 456 };

      activatedRoute.data = of({ leaveTranscation });
      comp.ngOnInit();

      expect(comp.leaveTranscation).toEqual(leaveTranscation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeaveTranscation>>();
      const leaveTranscation = { id: 123 };
      jest.spyOn(leaveTranscationFormService, 'getLeaveTranscation').mockReturnValue(leaveTranscation);
      jest.spyOn(leaveTranscationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leaveTranscation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leaveTranscation }));
      saveSubject.complete();

      // THEN
      expect(leaveTranscationFormService.getLeaveTranscation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(leaveTranscationService.update).toHaveBeenCalledWith(expect.objectContaining(leaveTranscation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeaveTranscation>>();
      const leaveTranscation = { id: 123 };
      jest.spyOn(leaveTranscationFormService, 'getLeaveTranscation').mockReturnValue({ id: null });
      jest.spyOn(leaveTranscationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leaveTranscation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leaveTranscation }));
      saveSubject.complete();

      // THEN
      expect(leaveTranscationFormService.getLeaveTranscation).toHaveBeenCalled();
      expect(leaveTranscationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeaveTranscation>>();
      const leaveTranscation = { id: 123 };
      jest.spyOn(leaveTranscationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leaveTranscation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(leaveTranscationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
