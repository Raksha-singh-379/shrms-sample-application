import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../employee-details.test-samples';

import { EmployeeDetailsFormService } from './employee-details-form.service';

describe('EmployeeDetails Form Service', () => {
  let service: EmployeeDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createEmployeeDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmployeeDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            passportNo: expect.any(Object),
            passportExpDate: expect.any(Object),
            telephoneNo: expect.any(Object),
            nationality: expect.any(Object),
            maritalStatus: expect.any(Object),
            religion: expect.any(Object),
            isSpousEmployed: expect.any(Object),
            noOfChildren: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            deleted: expect.any(Object),
            employeeId: expect.any(Object),
            age: expect.any(Object),
            fatherName: expect.any(Object),
            motherName: expect.any(Object),
            aadharNo: expect.any(Object),
            bloodGroup: expect.any(Object),
            dob: expect.any(Object),
            expertise: expect.any(Object),
            hobbies: expect.any(Object),
            areaInterest: expect.any(Object),
            languageKnown: expect.any(Object),
            description: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });

      it('passing IEmployeeDetails should create a new form with FormGroup', () => {
        const formGroup = service.createEmployeeDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            passportNo: expect.any(Object),
            passportExpDate: expect.any(Object),
            telephoneNo: expect.any(Object),
            nationality: expect.any(Object),
            maritalStatus: expect.any(Object),
            religion: expect.any(Object),
            isSpousEmployed: expect.any(Object),
            noOfChildren: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            deleted: expect.any(Object),
            employeeId: expect.any(Object),
            age: expect.any(Object),
            fatherName: expect.any(Object),
            motherName: expect.any(Object),
            aadharNo: expect.any(Object),
            bloodGroup: expect.any(Object),
            dob: expect.any(Object),
            expertise: expect.any(Object),
            hobbies: expect.any(Object),
            areaInterest: expect.any(Object),
            languageKnown: expect.any(Object),
            description: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });
    });

    describe('getEmployeeDetails', () => {
      it('should return NewEmployeeDetails for default EmployeeDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEmployeeDetailsFormGroup(sampleWithNewData);

        const employeeDetails = service.getEmployeeDetails(formGroup) as any;

        expect(employeeDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmployeeDetails for empty EmployeeDetails initial value', () => {
        const formGroup = service.createEmployeeDetailsFormGroup();

        const employeeDetails = service.getEmployeeDetails(formGroup) as any;

        expect(employeeDetails).toMatchObject({});
      });

      it('should return IEmployeeDetails', () => {
        const formGroup = service.createEmployeeDetailsFormGroup(sampleWithRequiredData);

        const employeeDetails = service.getEmployeeDetails(formGroup) as any;

        expect(employeeDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmployeeDetails should not enable id FormControl', () => {
        const formGroup = service.createEmployeeDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmployeeDetails should disable id FormControl', () => {
        const formGroup = service.createEmployeeDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
