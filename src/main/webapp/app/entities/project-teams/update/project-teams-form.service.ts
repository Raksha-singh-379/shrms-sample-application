import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProjectTeams, NewProjectTeams } from '../project-teams.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProjectTeams for edit and NewProjectTeamsFormGroupInput for create.
 */
type ProjectTeamsFormGroupInput = IProjectTeams | PartialWithRequiredKeyOf<NewProjectTeams>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProjectTeams | NewProjectTeams> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type ProjectTeamsFormRawValue = FormValueOf<IProjectTeams>;

type NewProjectTeamsFormRawValue = FormValueOf<NewProjectTeams>;

type ProjectTeamsFormDefaults = Pick<NewProjectTeams, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type ProjectTeamsFormGroupContent = {
  id: FormControl<ProjectTeamsFormRawValue['id'] | NewProjectTeams['id']>;
  teamMemberType: FormControl<ProjectTeamsFormRawValue['teamMemberType']>;
  projectId: FormControl<ProjectTeamsFormRawValue['projectId']>;
  employeId: FormControl<ProjectTeamsFormRawValue['employeId']>;
  lastModified: FormControl<ProjectTeamsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ProjectTeamsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<ProjectTeamsFormRawValue['createdBy']>;
  createdOn: FormControl<ProjectTeamsFormRawValue['createdOn']>;
  isDeleted: FormControl<ProjectTeamsFormRawValue['isDeleted']>;
  companyId: FormControl<ProjectTeamsFormRawValue['companyId']>;
};

export type ProjectTeamsFormGroup = FormGroup<ProjectTeamsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectTeamsFormService {
  createProjectTeamsFormGroup(projectTeams: ProjectTeamsFormGroupInput = { id: null }): ProjectTeamsFormGroup {
    const projectTeamsRawValue = this.convertProjectTeamsToProjectTeamsRawValue({
      ...this.getFormDefaults(),
      ...projectTeams,
    });
    return new FormGroup<ProjectTeamsFormGroupContent>({
      id: new FormControl(
        { value: projectTeamsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      teamMemberType: new FormControl(projectTeamsRawValue.teamMemberType),
      projectId: new FormControl(projectTeamsRawValue.projectId),
      employeId: new FormControl(projectTeamsRawValue.employeId),
      lastModified: new FormControl(projectTeamsRawValue.lastModified),
      lastModifiedBy: new FormControl(projectTeamsRawValue.lastModifiedBy),
      createdBy: new FormControl(projectTeamsRawValue.createdBy),
      createdOn: new FormControl(projectTeamsRawValue.createdOn),
      isDeleted: new FormControl(projectTeamsRawValue.isDeleted),
      companyId: new FormControl(projectTeamsRawValue.companyId),
    });
  }

  getProjectTeams(form: ProjectTeamsFormGroup): IProjectTeams | NewProjectTeams {
    return this.convertProjectTeamsRawValueToProjectTeams(form.getRawValue() as ProjectTeamsFormRawValue | NewProjectTeamsFormRawValue);
  }

  resetForm(form: ProjectTeamsFormGroup, projectTeams: ProjectTeamsFormGroupInput): void {
    const projectTeamsRawValue = this.convertProjectTeamsToProjectTeamsRawValue({ ...this.getFormDefaults(), ...projectTeams });
    form.reset(
      {
        ...projectTeamsRawValue,
        id: { value: projectTeamsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjectTeamsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertProjectTeamsRawValueToProjectTeams(
    rawProjectTeams: ProjectTeamsFormRawValue | NewProjectTeamsFormRawValue
  ): IProjectTeams | NewProjectTeams {
    return {
      ...rawProjectTeams,
      lastModified: dayjs(rawProjectTeams.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawProjectTeams.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertProjectTeamsToProjectTeamsRawValue(
    projectTeams: IProjectTeams | (Partial<NewProjectTeams> & ProjectTeamsFormDefaults)
  ): ProjectTeamsFormRawValue | PartialWithRequiredKeyOf<NewProjectTeamsFormRawValue> {
    return {
      ...projectTeams,
      lastModified: projectTeams.lastModified ? projectTeams.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: projectTeams.createdOn ? projectTeams.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
