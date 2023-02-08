import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../salary-settings.test-samples';

import { SalarySettingsFormService } from './salary-settings-form.service';

describe('SalarySettings Form Service', () => {
  let service: SalarySettingsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalarySettingsFormService);
  });

  describe('Service methods', () => {
    describe('createSalarySettingsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSalarySettingsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            da: expect.any(Object),
            hra: expect.any(Object),
            employeeshare: expect.any(Object),
            companyshare: expect.any(Object),
            salaryFrom: expect.any(Object),
            salaryTo: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
          })
        );
      });

      it('passing ISalarySettings should create a new form with FormGroup', () => {
        const formGroup = service.createSalarySettingsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            da: expect.any(Object),
            hra: expect.any(Object),
            employeeshare: expect.any(Object),
            companyshare: expect.any(Object),
            salaryFrom: expect.any(Object),
            salaryTo: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
          })
        );
      });
    });

    describe('getSalarySettings', () => {
      it('should return NewSalarySettings for default SalarySettings initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSalarySettingsFormGroup(sampleWithNewData);

        const salarySettings = service.getSalarySettings(formGroup) as any;

        expect(salarySettings).toMatchObject(sampleWithNewData);
      });

      it('should return NewSalarySettings for empty SalarySettings initial value', () => {
        const formGroup = service.createSalarySettingsFormGroup();

        const salarySettings = service.getSalarySettings(formGroup) as any;

        expect(salarySettings).toMatchObject({});
      });

      it('should return ISalarySettings', () => {
        const formGroup = service.createSalarySettingsFormGroup(sampleWithRequiredData);

        const salarySettings = service.getSalarySettings(formGroup) as any;

        expect(salarySettings).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISalarySettings should not enable id FormControl', () => {
        const formGroup = service.createSalarySettingsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSalarySettings should disable id FormControl', () => {
        const formGroup = service.createSalarySettingsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
