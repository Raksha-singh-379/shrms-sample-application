import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjects } from '../projects.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../projects.test-samples';

import { ProjectsService, RestProjects } from './projects.service';

const requireRestSample: RestProjects = {
  ...sampleWithRequiredData,
  startDate: sampleWithRequiredData.startDate?.toJSON(),
  endDate: sampleWithRequiredData.endDate?.toJSON(),
  deadLine: sampleWithRequiredData.deadLine?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('Projects Service', () => {
  let service: ProjectsService;
  let httpMock: HttpTestingController;
  let expectedResult: IProjects | IProjects[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProjectsService);
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

    it('should create a Projects', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const projects = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(projects).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Projects', () => {
      const projects = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(projects).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Projects', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Projects', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Projects', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProjectsToCollectionIfMissing', () => {
      it('should add a Projects to an empty array', () => {
        const projects: IProjects = sampleWithRequiredData;
        expectedResult = service.addProjectsToCollectionIfMissing([], projects);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projects);
      });

      it('should not add a Projects to an array that contains it', () => {
        const projects: IProjects = sampleWithRequiredData;
        const projectsCollection: IProjects[] = [
          {
            ...projects,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProjectsToCollectionIfMissing(projectsCollection, projects);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Projects to an array that doesn't contain it", () => {
        const projects: IProjects = sampleWithRequiredData;
        const projectsCollection: IProjects[] = [sampleWithPartialData];
        expectedResult = service.addProjectsToCollectionIfMissing(projectsCollection, projects);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projects);
      });

      it('should add only unique Projects to an array', () => {
        const projectsArray: IProjects[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const projectsCollection: IProjects[] = [sampleWithRequiredData];
        expectedResult = service.addProjectsToCollectionIfMissing(projectsCollection, ...projectsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const projects: IProjects = sampleWithRequiredData;
        const projects2: IProjects = sampleWithPartialData;
        expectedResult = service.addProjectsToCollectionIfMissing([], projects, projects2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projects);
        expect(expectedResult).toContain(projects2);
      });

      it('should accept null and undefined values', () => {
        const projects: IProjects = sampleWithRequiredData;
        expectedResult = service.addProjectsToCollectionIfMissing([], null, projects, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projects);
      });

      it('should return initial array if no Projects is added', () => {
        const projectsCollection: IProjects[] = [sampleWithRequiredData];
        expectedResult = service.addProjectsToCollectionIfMissing(projectsCollection, undefined, null);
        expect(expectedResult).toEqual(projectsCollection);
      });
    });

    describe('compareProjects', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProjects(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProjects(entity1, entity2);
        const compareResult2 = service.compareProjects(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProjects(entity1, entity2);
        const compareResult2 = service.compareProjects(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProjects(entity1, entity2);
        const compareResult2 = service.compareProjects(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
