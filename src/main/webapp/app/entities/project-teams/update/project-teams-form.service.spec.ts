import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../project-teams.test-samples';

import { ProjectTeamsFormService } from './project-teams-form.service';

describe('ProjectTeams Form Service', () => {
  let service: ProjectTeamsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectTeamsFormService);
  });

  describe('Service methods', () => {
    describe('createProjectTeamsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProjectTeamsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            teamMemberType: expect.any(Object),
            projectId: expect.any(Object),
            employeId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });

      it('passing IProjectTeams should create a new form with FormGroup', () => {
        const formGroup = service.createProjectTeamsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            teamMemberType: expect.any(Object),
            projectId: expect.any(Object),
            employeId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });
    });

    describe('getProjectTeams', () => {
      it('should return NewProjectTeams for default ProjectTeams initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProjectTeamsFormGroup(sampleWithNewData);

        const projectTeams = service.getProjectTeams(formGroup) as any;

        expect(projectTeams).toMatchObject(sampleWithNewData);
      });

      it('should return NewProjectTeams for empty ProjectTeams initial value', () => {
        const formGroup = service.createProjectTeamsFormGroup();

        const projectTeams = service.getProjectTeams(formGroup) as any;

        expect(projectTeams).toMatchObject({});
      });

      it('should return IProjectTeams', () => {
        const formGroup = service.createProjectTeamsFormGroup(sampleWithRequiredData);

        const projectTeams = service.getProjectTeams(formGroup) as any;

        expect(projectTeams).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProjectTeams should not enable id FormControl', () => {
        const formGroup = service.createProjectTeamsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProjectTeams should disable id FormControl', () => {
        const formGroup = service.createProjectTeamsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
