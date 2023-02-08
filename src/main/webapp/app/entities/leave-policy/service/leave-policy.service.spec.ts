import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILeavePolicy } from '../leave-policy.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../leave-policy.test-samples';

import { LeavePolicyService, RestLeavePolicy } from './leave-policy.service';

const requireRestSample: RestLeavePolicy = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('LeavePolicy Service', () => {
  let service: LeavePolicyService;
  let httpMock: HttpTestingController;
  let expectedResult: ILeavePolicy | ILeavePolicy[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LeavePolicyService);
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

    it('should create a LeavePolicy', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const leavePolicy = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(leavePolicy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LeavePolicy', () => {
      const leavePolicy = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(leavePolicy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LeavePolicy', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LeavePolicy', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LeavePolicy', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLeavePolicyToCollectionIfMissing', () => {
      it('should add a LeavePolicy to an empty array', () => {
        const leavePolicy: ILeavePolicy = sampleWithRequiredData;
        expectedResult = service.addLeavePolicyToCollectionIfMissing([], leavePolicy);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(leavePolicy);
      });

      it('should not add a LeavePolicy to an array that contains it', () => {
        const leavePolicy: ILeavePolicy = sampleWithRequiredData;
        const leavePolicyCollection: ILeavePolicy[] = [
          {
            ...leavePolicy,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLeavePolicyToCollectionIfMissing(leavePolicyCollection, leavePolicy);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LeavePolicy to an array that doesn't contain it", () => {
        const leavePolicy: ILeavePolicy = sampleWithRequiredData;
        const leavePolicyCollection: ILeavePolicy[] = [sampleWithPartialData];
        expectedResult = service.addLeavePolicyToCollectionIfMissing(leavePolicyCollection, leavePolicy);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(leavePolicy);
      });

      it('should add only unique LeavePolicy to an array', () => {
        const leavePolicyArray: ILeavePolicy[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const leavePolicyCollection: ILeavePolicy[] = [sampleWithRequiredData];
        expectedResult = service.addLeavePolicyToCollectionIfMissing(leavePolicyCollection, ...leavePolicyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const leavePolicy: ILeavePolicy = sampleWithRequiredData;
        const leavePolicy2: ILeavePolicy = sampleWithPartialData;
        expectedResult = service.addLeavePolicyToCollectionIfMissing([], leavePolicy, leavePolicy2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(leavePolicy);
        expect(expectedResult).toContain(leavePolicy2);
      });

      it('should accept null and undefined values', () => {
        const leavePolicy: ILeavePolicy = sampleWithRequiredData;
        expectedResult = service.addLeavePolicyToCollectionIfMissing([], null, leavePolicy, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(leavePolicy);
      });

      it('should return initial array if no LeavePolicy is added', () => {
        const leavePolicyCollection: ILeavePolicy[] = [sampleWithRequiredData];
        expectedResult = service.addLeavePolicyToCollectionIfMissing(leavePolicyCollection, undefined, null);
        expect(expectedResult).toEqual(leavePolicyCollection);
      });
    });

    describe('compareLeavePolicy', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLeavePolicy(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLeavePolicy(entity1, entity2);
        const compareResult2 = service.compareLeavePolicy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLeavePolicy(entity1, entity2);
        const compareResult2 = service.compareLeavePolicy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLeavePolicy(entity1, entity2);
        const compareResult2 = service.compareLeavePolicy(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
