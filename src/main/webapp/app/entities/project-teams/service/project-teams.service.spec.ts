import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjectTeams } from '../project-teams.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../project-teams.test-samples';

import { ProjectTeamsService, RestProjectTeams } from './project-teams.service';

const requireRestSample: RestProjectTeams = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('ProjectTeams Service', () => {
  let service: ProjectTeamsService;
  let httpMock: HttpTestingController;
  let expectedResult: IProjectTeams | IProjectTeams[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProjectTeamsService);
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

    it('should create a ProjectTeams', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const projectTeams = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(projectTeams).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProjectTeams', () => {
      const projectTeams = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(projectTeams).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProjectTeams', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProjectTeams', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProjectTeams', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProjectTeamsToCollectionIfMissing', () => {
      it('should add a ProjectTeams to an empty array', () => {
        const projectTeams: IProjectTeams = sampleWithRequiredData;
        expectedResult = service.addProjectTeamsToCollectionIfMissing([], projectTeams);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectTeams);
      });

      it('should not add a ProjectTeams to an array that contains it', () => {
        const projectTeams: IProjectTeams = sampleWithRequiredData;
        const projectTeamsCollection: IProjectTeams[] = [
          {
            ...projectTeams,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProjectTeamsToCollectionIfMissing(projectTeamsCollection, projectTeams);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProjectTeams to an array that doesn't contain it", () => {
        const projectTeams: IProjectTeams = sampleWithRequiredData;
        const projectTeamsCollection: IProjectTeams[] = [sampleWithPartialData];
        expectedResult = service.addProjectTeamsToCollectionIfMissing(projectTeamsCollection, projectTeams);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectTeams);
      });

      it('should add only unique ProjectTeams to an array', () => {
        const projectTeamsArray: IProjectTeams[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const projectTeamsCollection: IProjectTeams[] = [sampleWithRequiredData];
        expectedResult = service.addProjectTeamsToCollectionIfMissing(projectTeamsCollection, ...projectTeamsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const projectTeams: IProjectTeams = sampleWithRequiredData;
        const projectTeams2: IProjectTeams = sampleWithPartialData;
        expectedResult = service.addProjectTeamsToCollectionIfMissing([], projectTeams, projectTeams2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectTeams);
        expect(expectedResult).toContain(projectTeams2);
      });

      it('should accept null and undefined values', () => {
        const projectTeams: IProjectTeams = sampleWithRequiredData;
        expectedResult = service.addProjectTeamsToCollectionIfMissing([], null, projectTeams, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectTeams);
      });

      it('should return initial array if no ProjectTeams is added', () => {
        const projectTeamsCollection: IProjectTeams[] = [sampleWithRequiredData];
        expectedResult = service.addProjectTeamsToCollectionIfMissing(projectTeamsCollection, undefined, null);
        expect(expectedResult).toEqual(projectTeamsCollection);
      });
    });

    describe('compareProjectTeams', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProjectTeams(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProjectTeams(entity1, entity2);
        const compareResult2 = service.compareProjectTeams(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProjectTeams(entity1, entity2);
        const compareResult2 = service.compareProjectTeams(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProjectTeams(entity1, entity2);
        const compareResult2 = service.compareProjectTeams(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
