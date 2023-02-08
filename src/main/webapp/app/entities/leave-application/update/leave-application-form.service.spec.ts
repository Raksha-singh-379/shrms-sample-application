import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../leave-application.test-samples';

import { LeaveApplicationFormService } from './leave-application-form.service';

describe('LeaveApplication Form Service', () => {
  let service: LeaveApplicationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LeaveApplicationFormService);
  });

  describe('Service methods', () => {
    describe('createLeaveApplicationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLeaveApplicationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            leaveType: expect.any(Object),
            balanceLeave: expect.any(Object),
            noOfDays: expect.any(Object),
            reason: expect.any(Object),
            year: expect.any(Object),
            formDate: expect.any(Object),
            toDate: expect.any(Object),
            status: expect.any(Object),
            leaveStatus: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
            employee: expect.any(Object),
            leavePolicy: expect.any(Object),
          })
        );
      });

      it('passing ILeaveApplication should create a new form with FormGroup', () => {
        const formGroup = service.createLeaveApplicationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            leaveType: expect.any(Object),
            balanceLeave: expect.any(Object),
            noOfDays: expect.any(Object),
            reason: expect.any(Object),
            year: expect.any(Object),
            formDate: expect.any(Object),
            toDate: expect.any(Object),
            status: expect.any(Object),
            leaveStatus: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
            employee: expect.any(Object),
            leavePolicy: expect.any(Object),
          })
        );
      });
    });

    describe('getLeaveApplication', () => {
      it('should return NewLeaveApplication for default LeaveApplication initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLeaveApplicationFormGroup(sampleWithNewData);

        const leaveApplication = service.getLeaveApplication(formGroup) as any;

        expect(leaveApplication).toMatchObject(sampleWithNewData);
      });

      it('should return NewLeaveApplication for empty LeaveApplication initial value', () => {
        const formGroup = service.createLeaveApplicationFormGroup();

        const leaveApplication = service.getLeaveApplication(formGroup) as any;

        expect(leaveApplication).toMatchObject({});
      });

      it('should return ILeaveApplication', () => {
        const formGroup = service.createLeaveApplicationFormGroup(sampleWithRequiredData);

        const leaveApplication = service.getLeaveApplication(formGroup) as any;

        expect(leaveApplication).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILeaveApplication should not enable id FormControl', () => {
        const formGroup = service.createLeaveApplicationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLeaveApplication should disable id FormControl', () => {
        const formGroup = service.createLeaveApplicationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
