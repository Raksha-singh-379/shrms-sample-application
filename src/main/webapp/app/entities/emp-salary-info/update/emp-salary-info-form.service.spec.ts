import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../emp-salary-info.test-samples';

import { EmpSalaryInfoFormService } from './emp-salary-info-form.service';

describe('EmpSalaryInfo Form Service', () => {
  let service: EmpSalaryInfoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpSalaryInfoFormService);
  });

  describe('Service methods', () => {
    describe('createEmpSalaryInfoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmpSalaryInfoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            salaryBasis: expect.any(Object),
            amount: expect.any(Object),
            paymentType: expect.any(Object),
            isPfContribution: expect.any(Object),
            pfNumber: expect.any(Object),
            pfRate: expect.any(Object),
            additionalPfRate: expect.any(Object),
            totalPfRate: expect.any(Object),
            isEsiContribution: expect.any(Object),
            esiNumber: expect.any(Object),
            esiRate: expect.any(Object),
            additionalEsiRate: expect.any(Object),
            totalEsiRate: expect.any(Object),
            employeId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });

      it('passing IEmpSalaryInfo should create a new form with FormGroup', () => {
        const formGroup = service.createEmpSalaryInfoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            salaryBasis: expect.any(Object),
            amount: expect.any(Object),
            paymentType: expect.any(Object),
            isPfContribution: expect.any(Object),
            pfNumber: expect.any(Object),
            pfRate: expect.any(Object),
            additionalPfRate: expect.any(Object),
            totalPfRate: expect.any(Object),
            isEsiContribution: expect.any(Object),
            esiNumber: expect.any(Object),
            esiRate: expect.any(Object),
            additionalEsiRate: expect.any(Object),
            totalEsiRate: expect.any(Object),
            employeId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });
    });

    describe('getEmpSalaryInfo', () => {
      it('should return NewEmpSalaryInfo for default EmpSalaryInfo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEmpSalaryInfoFormGroup(sampleWithNewData);

        const empSalaryInfo = service.getEmpSalaryInfo(formGroup) as any;

        expect(empSalaryInfo).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmpSalaryInfo for empty EmpSalaryInfo initial value', () => {
        const formGroup = service.createEmpSalaryInfoFormGroup();

        const empSalaryInfo = service.getEmpSalaryInfo(formGroup) as any;

        expect(empSalaryInfo).toMatchObject({});
      });

      it('should return IEmpSalaryInfo', () => {
        const formGroup = service.createEmpSalaryInfoFormGroup(sampleWithRequiredData);

        const empSalaryInfo = service.getEmpSalaryInfo(formGroup) as any;

        expect(empSalaryInfo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmpSalaryInfo should not enable id FormControl', () => {
        const formGroup = service.createEmpSalaryInfoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmpSalaryInfo should disable id FormControl', () => {
        const formGroup = service.createEmpSalaryInfoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
