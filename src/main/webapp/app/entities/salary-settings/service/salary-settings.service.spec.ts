import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISalarySettings } from '../salary-settings.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../salary-settings.test-samples';

import { SalarySettingsService, RestSalarySettings } from './salary-settings.service';

const requireRestSample: RestSalarySettings = {
  ...sampleWithRequiredData,
  salaryFrom: sampleWithRequiredData.salaryFrom?.toJSON(),
  salaryTo: sampleWithRequiredData.salaryTo?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('SalarySettings Service', () => {
  let service: SalarySettingsService;
  let httpMock: HttpTestingController;
  let expectedResult: ISalarySettings | ISalarySettings[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SalarySettingsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a SalarySettings', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const salarySettings = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(salarySettings).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalarySettings', () => {
      const salarySettings = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(salarySettings).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalarySettings', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalarySettings', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SalarySettings', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSalarySettingsToCollectionIfMissing', () => {
      it('should add a SalarySettings to an empty array', () => {
        const salarySettings: ISalarySettings = sampleWithRequiredData;
        expectedResult = service.addSalarySettingsToCollectionIfMissing([], salarySettings);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salarySettings);
      });

      it('should not add a SalarySettings to an array that contains it', () => {
        const salarySettings: ISalarySettings = sampleWithRequiredData;
        const salarySettingsCollection: ISalarySettings[] = [
          {
            ...salarySettings,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSalarySettingsToCollectionIfMissing(salarySettingsCollection, salarySettings);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalarySettings to an array that doesn't contain it", () => {
        const salarySettings: ISalarySettings = sampleWithRequiredData;
        const salarySettingsCollection: ISalarySettings[] = [sampleWithPartialData];
        expectedResult = service.addSalarySettingsToCollectionIfMissing(salarySettingsCollection, salarySettings);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salarySettings);
      });

      it('should add only unique SalarySettings to an array', () => {
        const salarySettingsArray: ISalarySettings[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const salarySettingsCollection: ISalarySettings[] = [sampleWithRequiredData];
        expectedResult = service.addSalarySettingsToCollectionIfMissing(salarySettingsCollection, ...salarySettingsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salarySettings: ISalarySettings = sampleWithRequiredData;
        const salarySettings2: ISalarySettings = sampleWithPartialData;
        expectedResult = service.addSalarySettingsToCollectionIfMissing([], salarySettings, salarySettings2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salarySettings);
        expect(expectedResult).toContain(salarySettings2);
      });

      it('should accept null and undefined values', () => {
        const salarySettings: ISalarySettings = sampleWithRequiredData;
        expectedResult = service.addSalarySettingsToCollectionIfMissing([], null, salarySettings, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salarySettings);
      });

      it('should return initial array if no SalarySettings is added', () => {
        const salarySettingsCollection: ISalarySettings[] = [sampleWithRequiredData];
        expectedResult = service.addSalarySettingsToCollectionIfMissing(salarySettingsCollection, undefined, null);
        expect(expectedResult).toEqual(salarySettingsCollection);
      });
    });

    describe('compareSalarySettings', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSalarySettings(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSalarySettings(entity1, entity2);
        const compareResult2 = service.compareSalarySettings(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSalarySettings(entity1, entity2);
        const compareResult2 = service.compareSalarySettings(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSalarySettings(entity1, entity2);
        const compareResult2 = service.compareSalarySettings(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
