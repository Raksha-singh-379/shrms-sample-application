import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBanksDetails } from '../banks-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../banks-details.test-samples';

import { BanksDetailsService, RestBanksDetails } from './banks-details.service';

const requireRestSample: RestBanksDetails = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('BanksDetails Service', () => {
  let service: BanksDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IBanksDetails | IBanksDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BanksDetailsService);
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

    it('should create a BanksDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const banksDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(banksDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BanksDetails', () => {
      const banksDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(banksDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BanksDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BanksDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BanksDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBanksDetailsToCollectionIfMissing', () => {
      it('should add a BanksDetails to an empty array', () => {
        const banksDetails: IBanksDetails = sampleWithRequiredData;
        expectedResult = service.addBanksDetailsToCollectionIfMissing([], banksDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(banksDetails);
      });

      it('should not add a BanksDetails to an array that contains it', () => {
        const banksDetails: IBanksDetails = sampleWithRequiredData;
        const banksDetailsCollection: IBanksDetails[] = [
          {
            ...banksDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBanksDetailsToCollectionIfMissing(banksDetailsCollection, banksDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BanksDetails to an array that doesn't contain it", () => {
        const banksDetails: IBanksDetails = sampleWithRequiredData;
        const banksDetailsCollection: IBanksDetails[] = [sampleWithPartialData];
        expectedResult = service.addBanksDetailsToCollectionIfMissing(banksDetailsCollection, banksDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(banksDetails);
      });

      it('should add only unique BanksDetails to an array', () => {
        const banksDetailsArray: IBanksDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const banksDetailsCollection: IBanksDetails[] = [sampleWithRequiredData];
        expectedResult = service.addBanksDetailsToCollectionIfMissing(banksDetailsCollection, ...banksDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const banksDetails: IBanksDetails = sampleWithRequiredData;
        const banksDetails2: IBanksDetails = sampleWithPartialData;
        expectedResult = service.addBanksDetailsToCollectionIfMissing([], banksDetails, banksDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(banksDetails);
        expect(expectedResult).toContain(banksDetails2);
      });

      it('should accept null and undefined values', () => {
        const banksDetails: IBanksDetails = sampleWithRequiredData;
        expectedResult = service.addBanksDetailsToCollectionIfMissing([], null, banksDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(banksDetails);
      });

      it('should return initial array if no BanksDetails is added', () => {
        const banksDetailsCollection: IBanksDetails[] = [sampleWithRequiredData];
        expectedResult = service.addBanksDetailsToCollectionIfMissing(banksDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(banksDetailsCollection);
      });
    });

    describe('compareBanksDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBanksDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBanksDetails(entity1, entity2);
        const compareResult2 = service.compareBanksDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBanksDetails(entity1, entity2);
        const compareResult2 = service.compareBanksDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBanksDetails(entity1, entity2);
        const compareResult2 = service.compareBanksDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
