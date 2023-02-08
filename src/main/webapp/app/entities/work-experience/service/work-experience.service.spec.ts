import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWorkExperience } from '../work-experience.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../work-experience.test-samples';

import { WorkExperienceService, RestWorkExperience } from './work-experience.service';

const requireRestSample: RestWorkExperience = {
  ...sampleWithRequiredData,
  startDate: sampleWithRequiredData.startDate?.toJSON(),
  endDate: sampleWithRequiredData.endDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('WorkExperience Service', () => {
  let service: WorkExperienceService;
  let httpMock: HttpTestingController;
  let expectedResult: IWorkExperience | IWorkExperience[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WorkExperienceService);
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

    it('should create a WorkExperience', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const workExperience = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(workExperience).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a WorkExperience', () => {
      const workExperience = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(workExperience).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a WorkExperience', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of WorkExperience', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a WorkExperience', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWorkExperienceToCollectionIfMissing', () => {
      it('should add a WorkExperience to an empty array', () => {
        const workExperience: IWorkExperience = sampleWithRequiredData;
        expectedResult = service.addWorkExperienceToCollectionIfMissing([], workExperience);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workExperience);
      });

      it('should not add a WorkExperience to an array that contains it', () => {
        const workExperience: IWorkExperience = sampleWithRequiredData;
        const workExperienceCollection: IWorkExperience[] = [
          {
            ...workExperience,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWorkExperienceToCollectionIfMissing(workExperienceCollection, workExperience);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a WorkExperience to an array that doesn't contain it", () => {
        const workExperience: IWorkExperience = sampleWithRequiredData;
        const workExperienceCollection: IWorkExperience[] = [sampleWithPartialData];
        expectedResult = service.addWorkExperienceToCollectionIfMissing(workExperienceCollection, workExperience);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workExperience);
      });

      it('should add only unique WorkExperience to an array', () => {
        const workExperienceArray: IWorkExperience[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const workExperienceCollection: IWorkExperience[] = [sampleWithRequiredData];
        expectedResult = service.addWorkExperienceToCollectionIfMissing(workExperienceCollection, ...workExperienceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const workExperience: IWorkExperience = sampleWithRequiredData;
        const workExperience2: IWorkExperience = sampleWithPartialData;
        expectedResult = service.addWorkExperienceToCollectionIfMissing([], workExperience, workExperience2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workExperience);
        expect(expectedResult).toContain(workExperience2);
      });

      it('should accept null and undefined values', () => {
        const workExperience: IWorkExperience = sampleWithRequiredData;
        expectedResult = service.addWorkExperienceToCollectionIfMissing([], null, workExperience, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workExperience);
      });

      it('should return initial array if no WorkExperience is added', () => {
        const workExperienceCollection: IWorkExperience[] = [sampleWithRequiredData];
        expectedResult = service.addWorkExperienceToCollectionIfMissing(workExperienceCollection, undefined, null);
        expect(expectedResult).toEqual(workExperienceCollection);
      });
    });

    describe('compareWorkExperience', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWorkExperience(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareWorkExperience(entity1, entity2);
        const compareResult2 = service.compareWorkExperience(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareWorkExperience(entity1, entity2);
        const compareResult2 = service.compareWorkExperience(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareWorkExperience(entity1, entity2);
        const compareResult2 = service.compareWorkExperience(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
