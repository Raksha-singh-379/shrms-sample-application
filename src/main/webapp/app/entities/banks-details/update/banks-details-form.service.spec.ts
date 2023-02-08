import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../banks-details.test-samples';

import { BanksDetailsFormService } from './banks-details-form.service';

describe('BanksDetails Form Service', () => {
  let service: BanksDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BanksDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createBanksDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBanksDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountNo: expect.any(Object),
            bankName: expect.any(Object),
            ifscCode: expect.any(Object),
            pan: expect.any(Object),
            branch: expect.any(Object),
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

      it('passing IBanksDetails should create a new form with FormGroup', () => {
        const formGroup = service.createBanksDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountNo: expect.any(Object),
            bankName: expect.any(Object),
            ifscCode: expect.any(Object),
            pan: expect.any(Object),
            branch: expect.any(Object),
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

    describe('getBanksDetails', () => {
      it('should return NewBanksDetails for default BanksDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBanksDetailsFormGroup(sampleWithNewData);

        const banksDetails = service.getBanksDetails(formGroup) as any;

        expect(banksDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewBanksDetails for empty BanksDetails initial value', () => {
        const formGroup = service.createBanksDetailsFormGroup();

        const banksDetails = service.getBanksDetails(formGroup) as any;

        expect(banksDetails).toMatchObject({});
      });

      it('should return IBanksDetails', () => {
        const formGroup = service.createBanksDetailsFormGroup(sampleWithRequiredData);

        const banksDetails = service.getBanksDetails(formGroup) as any;

        expect(banksDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBanksDetails should not enable id FormControl', () => {
        const formGroup = service.createBanksDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBanksDetails should disable id FormControl', () => {
        const formGroup = service.createBanksDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
