import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../projects.test-samples';

import { ProjectsFormService } from './projects-form.service';

describe('Projects Form Service', () => {
  let service: ProjectsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectsFormService);
  });

  describe('Service methods', () => {
    describe('createProjectsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProjectsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            projectName: expect.any(Object),
            description: expect.any(Object),
            clientName: expect.any(Object),
            cost: expect.any(Object),
            costType: expect.any(Object),
            priority: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            deadLine: expect.any(Object),
            status: expect.any(Object),
            projectLead: expect.any(Object),
            progressPercent: expect.any(Object),
            openTasksNo: expect.any(Object),
            completeTasksNo: expect.any(Object),
            projectLogo: expect.any(Object),
            projectFile: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
            companyId: expect.any(Object),
            employeeId: expect.any(Object),
          })
        );
      });

      it('passing IProjects should create a new form with FormGroup', () => {
        const formGroup = service.createProjectsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            projectName: expect.any(Object),
            description: expect.any(Object),
            clientName: expect.any(Object),
            cost: expect.any(Object),
            costType: expect.any(Object),
            priority: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            deadLine: expect.any(Object),
            status: expect.any(Object),
            projectLead: expect.any(Object),
            progressPercent: expect.any(Object),
            openTasksNo: expect.any(Object),
            completeTasksNo: expect.any(Object),
            projectLogo: expect.any(Object),
            projectFile: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            deleted: expect.any(Object),
            companyId: expect.any(Object),
            employeeId: expect.any(Object),
          })
        );
      });
    });

    describe('getProjects', () => {
      it('should return NewProjects for default Projects initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProjectsFormGroup(sampleWithNewData);

        const projects = service.getProjects(formGroup) as any;

        expect(projects).toMatchObject(sampleWithNewData);
      });

      it('should return NewProjects for empty Projects initial value', () => {
        const formGroup = service.createProjectsFormGroup();

        const projects = service.getProjects(formGroup) as any;

        expect(projects).toMatchObject({});
      });

      it('should return IProjects', () => {
        const formGroup = service.createProjectsFormGroup(sampleWithRequiredData);

        const projects = service.getProjects(formGroup) as any;

        expect(projects).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProjects should not enable id FormControl', () => {
        const formGroup = service.createProjectsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProjects should disable id FormControl', () => {
        const formGroup = service.createProjectsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
