import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SalarySettingsFormService } from './salary-settings-form.service';
import { SalarySettingsService } from '../service/salary-settings.service';
import { ISalarySettings } from '../salary-settings.model';

import { SalarySettingsUpdateComponent } from './salary-settings-update.component';

describe('SalarySettings Management Update Component', () => {
  let comp: SalarySettingsUpdateComponent;
  let fixture: ComponentFixture<SalarySettingsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salarySettingsFormService: SalarySettingsFormService;
  let salarySettingsService: SalarySettingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SalarySettingsUpdateComponent],
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
      .overrideTemplate(SalarySettingsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalarySettingsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salarySettingsFormService = TestBed.inject(SalarySettingsFormService);
    salarySettingsService = TestBed.inject(SalarySettingsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salarySettings: ISalarySettings = { id: 456 };

      activatedRoute.data = of({ salarySettings });
      comp.ngOnInit();

      expect(comp.salarySettings).toEqual(salarySettings);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalarySettings>>();
      const salarySettings = { id: 123 };
      jest.spyOn(salarySettingsFormService, 'getSalarySettings').mockReturnValue(salarySettings);
      jest.spyOn(salarySettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salarySettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salarySettings }));
      saveSubject.complete();

      // THEN
      expect(salarySettingsFormService.getSalarySettings).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(salarySettingsService.update).toHaveBeenCalledWith(expect.objectContaining(salarySettings));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalarySettings>>();
      const salarySettings = { id: 123 };
      jest.spyOn(salarySettingsFormService, 'getSalarySettings').mockReturnValue({ id: null });
      jest.spyOn(salarySettingsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salarySettings: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salarySettings }));
      saveSubject.complete();

      // THEN
      expect(salarySettingsFormService.getSalarySettings).toHaveBeenCalled();
      expect(salarySettingsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalarySettings>>();
      const salarySettings = { id: 123 };
      jest.spyOn(salarySettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salarySettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salarySettingsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
