import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../company.test-samples';

import { CompanyFormService } from './company-form.service';

describe('Company Form Service', () => {
  let service: CompanyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyFormService);
  });

  describe('Service methods', () => {
    describe('createCompanyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompanyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            companyName: expect.any(Object),
            contactPerson: expect.any(Object),
            address: expect.any(Object),
            postalCode: expect.any(Object),
            email: expect.any(Object),
            phoneNumber: expect.any(Object),
            mobileNumber: expect.any(Object),
            websiteUrl: expect.any(Object),
            fax: expect.any(Object),
            regNumber: expect.any(Object),
            gstin: expect.any(Object),
            pan: expect.any(Object),
            tan: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            deleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            address: expect.any(Object),
          })
        );
      });

      it('passing ICompany should create a new form with FormGroup', () => {
        const formGroup = service.createCompanyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            companyName: expect.any(Object),
            contactPerson: expect.any(Object),
            address: expect.any(Object),
            postalCode: expect.any(Object),
            email: expect.any(Object),
            phoneNumber: expect.any(Object),
            mobileNumber: expect.any(Object),
            websiteUrl: expect.any(Object),
            fax: expect.any(Object),
            regNumber: expect.any(Object),
            gstin: expect.any(Object),
            pan: expect.any(Object),
            tan: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            deleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            address: expect.any(Object),
          })
        );
      });
    });

    describe('getCompany', () => {
      it('should return NewCompany for default Company initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCompanyFormGroup(sampleWithNewData);

        const company = service.getCompany(formGroup) as any;

        expect(company).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompany for empty Company initial value', () => {
        const formGroup = service.createCompanyFormGroup();

        const company = service.getCompany(formGroup) as any;

        expect(company).toMatchObject({});
      });

      it('should return ICompany', () => {
        const formGroup = service.createCompanyFormGroup(sampleWithRequiredData);

        const company = service.getCompany(formGroup) as any;

        expect(company).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompany should not enable id FormControl', () => {
        const formGroup = service.createCompanyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompany should disable id FormControl', () => {
        const formGroup = service.createCompanyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
