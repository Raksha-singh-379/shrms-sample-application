import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProjects, NewProjects } from '../projects.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProjects for edit and NewProjectsFormGroupInput for create.
 */
type ProjectsFormGroupInput = IProjects | PartialWithRequiredKeyOf<NewProjects>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProjects | NewProjects> = Omit<T, 'startDate' | 'endDate' | 'deadLine' | 'lastModified' | 'createdOn'> & {
  startDate?: string | null;
  endDate?: string | null;
  deadLine?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type ProjectsFormRawValue = FormValueOf<IProjects>;

type NewProjectsFormRawValue = FormValueOf<NewProjects>;

type ProjectsFormDefaults = Pick<NewProjects, 'id' | 'startDate' | 'endDate' | 'deadLine' | 'lastModified' | 'createdOn' | 'deleted'>;

type ProjectsFormGroupContent = {
  id: FormControl<ProjectsFormRawValue['id'] | NewProjects['id']>;
  projectName: FormControl<ProjectsFormRawValue['projectName']>;
  description: FormControl<ProjectsFormRawValue['description']>;
  clientName: FormControl<ProjectsFormRawValue['clientName']>;
  cost: FormControl<ProjectsFormRawValue['cost']>;
  costType: FormControl<ProjectsFormRawValue['costType']>;
  priority: FormControl<ProjectsFormRawValue['priority']>;
  startDate: FormControl<ProjectsFormRawValue['startDate']>;
  endDate: FormControl<ProjectsFormRawValue['endDate']>;
  deadLine: FormControl<ProjectsFormRawValue['deadLine']>;
  status: FormControl<ProjectsFormRawValue['status']>;
  projectLead: FormControl<ProjectsFormRawValue['projectLead']>;
  progressPercent: FormControl<ProjectsFormRawValue['progressPercent']>;
  openTasksNo: FormControl<ProjectsFormRawValue['openTasksNo']>;
  completeTasksNo: FormControl<ProjectsFormRawValue['completeTasksNo']>;
  projectLogo: FormControl<ProjectsFormRawValue['projectLogo']>;
  projectLogoContentType: FormControl<ProjectsFormRawValue['projectLogoContentType']>;
  projectFile: FormControl<ProjectsFormRawValue['projectFile']>;
  lastModified: FormControl<ProjectsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ProjectsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<ProjectsFormRawValue['createdBy']>;
  createdOn: FormControl<ProjectsFormRawValue['createdOn']>;
  deleted: FormControl<ProjectsFormRawValue['deleted']>;
  companyId: FormControl<ProjectsFormRawValue['companyId']>;
  employeeId: FormControl<ProjectsFormRawValue['employeeId']>;
};

export type ProjectsFormGroup = FormGroup<ProjectsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectsFormService {
  createProjectsFormGroup(projects: ProjectsFormGroupInput = { id: null }): ProjectsFormGroup {
    const projectsRawValue = this.convertProjectsToProjectsRawValue({
      ...this.getFormDefaults(),
      ...projects,
    });
    return new FormGroup<ProjectsFormGroupContent>({
      id: new FormControl(
        { value: projectsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      projectName: new FormControl(projectsRawValue.projectName),
      description: new FormControl(projectsRawValue.description),
      clientName: new FormControl(projectsRawValue.clientName),
      cost: new FormControl(projectsRawValue.cost),
      costType: new FormControl(projectsRawValue.costType),
      priority: new FormControl(projectsRawValue.priority),
      startDate: new FormControl(projectsRawValue.startDate),
      endDate: new FormControl(projectsRawValue.endDate),
      deadLine: new FormControl(projectsRawValue.deadLine),
      status: new FormControl(projectsRawValue.status),
      projectLead: new FormControl(projectsRawValue.projectLead),
      progressPercent: new FormControl(projectsRawValue.progressPercent),
      openTasksNo: new FormControl(projectsRawValue.openTasksNo),
      completeTasksNo: new FormControl(projectsRawValue.completeTasksNo),
      projectLogo: new FormControl(projectsRawValue.projectLogo),
      projectLogoContentType: new FormControl(projectsRawValue.projectLogoContentType),
      projectFile: new FormControl(projectsRawValue.projectFile),
      lastModified: new FormControl(projectsRawValue.lastModified),
      lastModifiedBy: new FormControl(projectsRawValue.lastModifiedBy),
      createdBy: new FormControl(projectsRawValue.createdBy),
      createdOn: new FormControl(projectsRawValue.createdOn),
      deleted: new FormControl(projectsRawValue.deleted),
      companyId: new FormControl(projectsRawValue.companyId),
      employeeId: new FormControl(projectsRawValue.employeeId),
    });
  }

  getProjects(form: ProjectsFormGroup): IProjects | NewProjects {
    return this.convertProjectsRawValueToProjects(form.getRawValue() as ProjectsFormRawValue | NewProjectsFormRawValue);
  }

  resetForm(form: ProjectsFormGroup, projects: ProjectsFormGroupInput): void {
    const projectsRawValue = this.convertProjectsToProjectsRawValue({ ...this.getFormDefaults(), ...projects });
    form.reset(
      {
        ...projectsRawValue,
        id: { value: projectsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjectsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
      deadLine: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertProjectsRawValueToProjects(rawProjects: ProjectsFormRawValue | NewProjectsFormRawValue): IProjects | NewProjects {
    return {
      ...rawProjects,
      startDate: dayjs(rawProjects.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawProjects.endDate, DATE_TIME_FORMAT),
      deadLine: dayjs(rawProjects.deadLine, DATE_TIME_FORMAT),
      lastModified: dayjs(rawProjects.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawProjects.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertProjectsToProjectsRawValue(
    projects: IProjects | (Partial<NewProjects> & ProjectsFormDefaults)
  ): ProjectsFormRawValue | PartialWithRequiredKeyOf<NewProjectsFormRawValue> {
    return {
      ...projects,
      startDate: projects.startDate ? projects.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: projects.endDate ? projects.endDate.format(DATE_TIME_FORMAT) : undefined,
      deadLine: projects.deadLine ? projects.deadLine.format(DATE_TIME_FORMAT) : undefined,
      lastModified: projects.lastModified ? projects.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: projects.createdOn ? projects.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
