import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../leave-transcation.test-samples';

import { LeaveTranscationFormService } from './leave-transcation-form.service';

describe('LeaveTranscation Form Service', () => {
  let service: LeaveTranscationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LeaveTranscationFormService);
  });

  describe('Service methods', () => {
    describe('createLeaveTranscationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLeaveTranscationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            leaveType: expect.any(Object),
            empId: expect.any(Object),
            monthDate: expect.any(Object),
            status: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
          })
        );
      });

      it('passing ILeaveTranscation should create a new form with FormGroup', () => {
        const formGroup = service.createLeaveTranscationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            leaveType: expect.any(Object),
            empId: expect.any(Object),
            monthDate: expect.any(Object),
            status: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
          })
        );
      });
    });

    describe('getLeaveTranscation', () => {
      it('should return NewLeaveTranscation for default LeaveTranscation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLeaveTranscationFormGroup(sampleWithNewData);

        const leaveTranscation = service.getLeaveTranscation(formGroup) as any;

        expect(leaveTranscation).toMatchObject(sampleWithNewData);
      });

      it('should return NewLeaveTranscation for empty LeaveTranscation initial value', () => {
        const formGroup = service.createLeaveTranscationFormGroup();

        const leaveTranscation = service.getLeaveTranscation(formGroup) as any;

        expect(leaveTranscation).toMatchObject({});
      });

      it('should return ILeaveTranscation', () => {
        const formGroup = service.createLeaveTranscationFormGroup(sampleWithRequiredData);

        const leaveTranscation = service.getLeaveTranscation(formGroup) as any;

        expect(leaveTranscation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILeaveTranscation should not enable id FormControl', () => {
        const formGroup = service.createLeaveTranscationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLeaveTranscation should disable id FormControl', () => {
        const formGroup = service.createLeaveTranscationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
