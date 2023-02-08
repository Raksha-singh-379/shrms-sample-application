import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmpSalaryInfo } from '../emp-salary-info.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../emp-salary-info.test-samples';

import { EmpSalaryInfoService, RestEmpSalaryInfo } from './emp-salary-info.service';

const requireRestSample: RestEmpSalaryInfo = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('EmpSalaryInfo Service', () => {
  let service: EmpSalaryInfoService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmpSalaryInfo | IEmpSalaryInfo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmpSalaryInfoService);
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

    it('should create a EmpSalaryInfo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const empSalaryInfo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(empSalaryInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmpSalaryInfo', () => {
      const empSalaryInfo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(empSalaryInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmpSalaryInfo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmpSalaryInfo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmpSalaryInfo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmpSalaryInfoToCollectionIfMissing', () => {
      it('should add a EmpSalaryInfo to an empty array', () => {
        const empSalaryInfo: IEmpSalaryInfo = sampleWithRequiredData;
        expectedResult = service.addEmpSalaryInfoToCollectionIfMissing([], empSalaryInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empSalaryInfo);
      });

      it('should not add a EmpSalaryInfo to an array that contains it', () => {
        const empSalaryInfo: IEmpSalaryInfo = sampleWithRequiredData;
        const empSalaryInfoCollection: IEmpSalaryInfo[] = [
          {
            ...empSalaryInfo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmpSalaryInfoToCollectionIfMissing(empSalaryInfoCollection, empSalaryInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmpSalaryInfo to an array that doesn't contain it", () => {
        const empSalaryInfo: IEmpSalaryInfo = sampleWithRequiredData;
        const empSalaryInfoCollection: IEmpSalaryInfo[] = [sampleWithPartialData];
        expectedResult = service.addEmpSalaryInfoToCollectionIfMissing(empSalaryInfoCollection, empSalaryInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empSalaryInfo);
      });

      it('should add only unique EmpSalaryInfo to an array', () => {
        const empSalaryInfoArray: IEmpSalaryInfo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const empSalaryInfoCollection: IEmpSalaryInfo[] = [sampleWithRequiredData];
        expectedResult = service.addEmpSalaryInfoToCollectionIfMissing(empSalaryInfoCollection, ...empSalaryInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const empSalaryInfo: IEmpSalaryInfo = sampleWithRequiredData;
        const empSalaryInfo2: IEmpSalaryInfo = sampleWithPartialData;
        expectedResult = service.addEmpSalaryInfoToCollectionIfMissing([], empSalaryInfo, empSalaryInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empSalaryInfo);
        expect(expectedResult).toContain(empSalaryInfo2);
      });

      it('should accept null and undefined values', () => {
        const empSalaryInfo: IEmpSalaryInfo = sampleWithRequiredData;
        expectedResult = service.addEmpSalaryInfoToCollectionIfMissing([], null, empSalaryInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empSalaryInfo);
      });

      it('should return initial array if no EmpSalaryInfo is added', () => {
        const empSalaryInfoCollection: IEmpSalaryInfo[] = [sampleWithRequiredData];
        expectedResult = service.addEmpSalaryInfoToCollectionIfMissing(empSalaryInfoCollection, undefined, null);
        expect(expectedResult).toEqual(empSalaryInfoCollection);
      });
    });

    describe('compareEmpSalaryInfo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmpSalaryInfo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmpSalaryInfo(entity1, entity2);
        const compareResult2 = service.compareEmpSalaryInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmpSalaryInfo(entity1, entity2);
        const compareResult2 = service.compareEmpSalaryInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmpSalaryInfo(entity1, entity2);
        const compareResult2 = service.compareEmpSalaryInfo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
