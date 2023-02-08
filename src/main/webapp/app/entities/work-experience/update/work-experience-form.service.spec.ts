import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../work-experience.test-samples';

import { WorkExperienceFormService } from './work-experience-form.service';

describe('WorkExperience Form Service', () => {
  let service: WorkExperienceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkExperienceFormService);
  });

  describe('Service methods', () => {
    describe('createWorkExperienceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWorkExperienceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            jobTitle: expect.any(Object),
            companyName: expect.any(Object),
            address: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            employeeId: expect.any(Object),
            yearOfExp: expect.any(Object),
            jobDesc: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });

      it('passing IWorkExperience should create a new form with FormGroup', () => {
        const formGroup = service.createWorkExperienceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            jobTitle: expect.any(Object),
            companyName: expect.any(Object),
            address: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            employeeId: expect.any(Object),
            yearOfExp: expect.any(Object),
            jobDesc: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });
    });

    describe('getWorkExperience', () => {
      it('should return NewWorkExperience for default WorkExperience initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createWorkExperienceFormGroup(sampleWithNewData);

        const workExperience = service.getWorkExperience(formGroup) as any;

        expect(workExperience).toMatchObject(sampleWithNewData);
      });

      it('should return NewWorkExperience for empty WorkExperience initial value', () => {
        const formGroup = service.createWorkExperienceFormGroup();

        const workExperience = service.getWorkExperience(formGroup) as any;

        expect(workExperience).toMatchObject({});
      });

      it('should return IWorkExperience', () => {
        const formGroup = service.createWorkExperienceFormGroup(sampleWithRequiredData);

        const workExperience = service.getWorkExperience(formGroup) as any;

        expect(workExperience).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWorkExperience should not enable id FormControl', () => {
        const formGroup = service.createWorkExperienceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWorkExperience should disable id FormControl', () => {
        const formGroup = service.createWorkExperienceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
