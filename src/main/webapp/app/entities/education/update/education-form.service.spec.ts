import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../education.test-samples';

import { EducationFormService } from './education-form.service';

describe('Education Form Service', () => {
  let service: EducationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EducationFormService);
  });

  describe('Service methods', () => {
    describe('createEducationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEducationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            institution: expect.any(Object),
            subject: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            degree: expect.any(Object),
            grade: expect.any(Object),
            description: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
            employeeId: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });

      it('passing IEducation should create a new form with FormGroup', () => {
        const formGroup = service.createEducationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            institution: expect.any(Object),
            subject: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            degree: expect.any(Object),
            grade: expect.any(Object),
            description: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
            employeeId: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });
    });

    describe('getEducation', () => {
      it('should return NewEducation for default Education initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEducationFormGroup(sampleWithNewData);

        const education = service.getEducation(formGroup) as any;

        expect(education).toMatchObject(sampleWithNewData);
      });

      it('should return NewEducation for empty Education initial value', () => {
        const formGroup = service.createEducationFormGroup();

        const education = service.getEducation(formGroup) as any;

        expect(education).toMatchObject({});
      });

      it('should return IEducation', () => {
        const formGroup = service.createEducationFormGroup(sampleWithRequiredData);

        const education = service.getEducation(formGroup) as any;

        expect(education).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEducation should not enable id FormControl', () => {
        const formGroup = service.createEducationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEducation should disable id FormControl', () => {
        const formGroup = service.createEducationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
