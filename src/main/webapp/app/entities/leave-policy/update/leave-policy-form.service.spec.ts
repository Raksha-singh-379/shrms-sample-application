import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../leave-policy.test-samples';

import { LeavePolicyFormService } from './leave-policy-form.service';

describe('LeavePolicy Form Service', () => {
  let service: LeavePolicyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LeavePolicyFormService);
  });

  describe('Service methods', () => {
    describe('createLeavePolicyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLeavePolicyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            leaveType: expect.any(Object),
            isCarryForword: expect.any(Object),
            employeeType: expect.any(Object),
            genderLeave: expect.any(Object),
            status: expect.any(Object),
            totalLeave: expect.any(Object),
            maxLeave: expect.any(Object),
            hasproRataLeave: expect.any(Object),
            description: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
          })
        );
      });

      it('passing ILeavePolicy should create a new form with FormGroup', () => {
        const formGroup = service.createLeavePolicyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            leaveType: expect.any(Object),
            isCarryForword: expect.any(Object),
            employeeType: expect.any(Object),
            genderLeave: expect.any(Object),
            status: expect.any(Object),
            totalLeave: expect.any(Object),
            maxLeave: expect.any(Object),
            hasproRataLeave: expect.any(Object),
            description: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
          })
        );
      });
    });

    describe('getLeavePolicy', () => {
      it('should return NewLeavePolicy for default LeavePolicy initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLeavePolicyFormGroup(sampleWithNewData);

        const leavePolicy = service.getLeavePolicy(formGroup) as any;

        expect(leavePolicy).toMatchObject(sampleWithNewData);
      });

      it('should return NewLeavePolicy for empty LeavePolicy initial value', () => {
        const formGroup = service.createLeavePolicyFormGroup();

        const leavePolicy = service.getLeavePolicy(formGroup) as any;

        expect(leavePolicy).toMatchObject({});
      });

      it('should return ILeavePolicy', () => {
        const formGroup = service.createLeavePolicyFormGroup(sampleWithRequiredData);

        const leavePolicy = service.getLeavePolicy(formGroup) as any;

        expect(leavePolicy).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILeavePolicy should not enable id FormControl', () => {
        const formGroup = service.createLeavePolicyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLeavePolicy should disable id FormControl', () => {
        const formGroup = service.createLeavePolicyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
