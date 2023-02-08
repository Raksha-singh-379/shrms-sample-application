import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILeaveTranscation } from '../leave-transcation.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../leave-transcation.test-samples';

import { LeaveTranscationService, RestLeaveTranscation } from './leave-transcation.service';

const requireRestSample: RestLeaveTranscation = {
  ...sampleWithRequiredData,
  monthDate: sampleWithRequiredData.monthDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('LeaveTranscation Service', () => {
  let service: LeaveTranscationService;
  let httpMock: HttpTestingController;
  let expectedResult: ILeaveTranscation | ILeaveTranscation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LeaveTranscationService);
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

    it('should create a LeaveTranscation', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const leaveTranscation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(leaveTranscation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LeaveTranscation', () => {
      const leaveTranscation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(leaveTranscation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LeaveTranscation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LeaveTranscation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LeaveTranscation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLeaveTranscationToCollectionIfMissing', () => {
      it('should add a LeaveTranscation to an empty array', () => {
        const leaveTranscation: ILeaveTranscation = sampleWithRequiredData;
        expectedResult = service.addLeaveTranscationToCollectionIfMissing([], leaveTranscation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(leaveTranscation);
      });

      it('should not add a LeaveTranscation to an array that contains it', () => {
        const leaveTranscation: ILeaveTranscation = sampleWithRequiredData;
        const leaveTranscationCollection: ILeaveTranscation[] = [
          {
            ...leaveTranscation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLeaveTranscationToCollectionIfMissing(leaveTranscationCollection, leaveTranscation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LeaveTranscation to an array that doesn't contain it", () => {
        const leaveTranscation: ILeaveTranscation = sampleWithRequiredData;
        const leaveTranscationCollection: ILeaveTranscation[] = [sampleWithPartialData];
        expectedResult = service.addLeaveTranscationToCollectionIfMissing(leaveTranscationCollection, leaveTranscation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(leaveTranscation);
      });

      it('should add only unique LeaveTranscation to an array', () => {
        const leaveTranscationArray: ILeaveTranscation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const leaveTranscationCollection: ILeaveTranscation[] = [sampleWithRequiredData];
        expectedResult = service.addLeaveTranscationToCollectionIfMissing(leaveTranscationCollection, ...leaveTranscationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const leaveTranscation: ILeaveTranscation = sampleWithRequiredData;
        const leaveTranscation2: ILeaveTranscation = sampleWithPartialData;
        expectedResult = service.addLeaveTranscationToCollectionIfMissing([], leaveTranscation, leaveTranscation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(leaveTranscation);
        expect(expectedResult).toContain(leaveTranscation2);
      });

      it('should accept null and undefined values', () => {
        const leaveTranscation: ILeaveTranscation = sampleWithRequiredData;
        expectedResult = service.addLeaveTranscationToCollectionIfMissing([], null, leaveTranscation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(leaveTranscation);
      });

      it('should return initial array if no LeaveTranscation is added', () => {
        const leaveTranscationCollection: ILeaveTranscation[] = [sampleWithRequiredData];
        expectedResult = service.addLeaveTranscationToCollectionIfMissing(leaveTranscationCollection, undefined, null);
        expect(expectedResult).toEqual(leaveTranscationCollection);
      });
    });

    describe('compareLeaveTranscation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLeaveTranscation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLeaveTranscation(entity1, entity2);
        const compareResult2 = service.compareLeaveTranscation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLeaveTranscation(entity1, entity2);
        const compareResult2 = service.compareLeaveTranscation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLeaveTranscation(entity1, entity2);
        const compareResult2 = service.compareLeaveTranscation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
