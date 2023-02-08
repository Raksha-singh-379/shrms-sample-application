import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IWorkExperience, NewWorkExperience } from '../work-experience.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWorkExperience for edit and NewWorkExperienceFormGroupInput for create.
 */
type WorkExperienceFormGroupInput = IWorkExperience | PartialWithRequiredKeyOf<NewWorkExperience>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IWorkExperience | NewWorkExperience> = Omit<T, 'startDate' | 'endDate' | 'lastModified' | 'createdOn'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type WorkExperienceFormRawValue = FormValueOf<IWorkExperience>;

type NewWorkExperienceFormRawValue = FormValueOf<NewWorkExperience>;

type WorkExperienceFormDefaults = Pick<NewWorkExperience, 'id' | 'startDate' | 'endDate' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type WorkExperienceFormGroupContent = {
  id: FormControl<WorkExperienceFormRawValue['id'] | NewWorkExperience['id']>;
  jobTitle: FormControl<WorkExperienceFormRawValue['jobTitle']>;
  companyName: FormControl<WorkExperienceFormRawValue['companyName']>;
  address: FormControl<WorkExperienceFormRawValue['address']>;
  startDate: FormControl<WorkExperienceFormRawValue['startDate']>;
  endDate: FormControl<WorkExperienceFormRawValue['endDate']>;
  lastModified: FormControl<WorkExperienceFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<WorkExperienceFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<WorkExperienceFormRawValue['createdBy']>;
  createdOn: FormControl<WorkExperienceFormRawValue['createdOn']>;
  isDeleted: FormControl<WorkExperienceFormRawValue['isDeleted']>;
  employeeId: FormControl<WorkExperienceFormRawValue['employeeId']>;
  yearOfExp: FormControl<WorkExperienceFormRawValue['yearOfExp']>;
  jobDesc: FormControl<WorkExperienceFormRawValue['jobDesc']>;
  employee: FormControl<WorkExperienceFormRawValue['employee']>;
};

export type WorkExperienceFormGroup = FormGroup<WorkExperienceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WorkExperienceFormService {
  createWorkExperienceFormGroup(workExperience: WorkExperienceFormGroupInput = { id: null }): WorkExperienceFormGroup {
    const workExperienceRawValue = this.convertWorkExperienceToWorkExperienceRawValue({
      ...this.getFormDefaults(),
      ...workExperience,
    });
    return new FormGroup<WorkExperienceFormGroupContent>({
      id: new FormControl(
        { value: workExperienceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      jobTitle: new FormControl(workExperienceRawValue.jobTitle),
      companyName: new FormControl(workExperienceRawValue.companyName),
      address: new FormControl(workExperienceRawValue.address),
      startDate: new FormControl(workExperienceRawValue.startDate),
      endDate: new FormControl(workExperienceRawValue.endDate),
      lastModified: new FormControl(workExperienceRawValue.lastModified),
      lastModifiedBy: new FormControl(workExperienceRawValue.lastModifiedBy),
      createdBy: new FormControl(workExperienceRawValue.createdBy),
      createdOn: new FormControl(workExperienceRawValue.createdOn),
      isDeleted: new FormControl(workExperienceRawValue.isDeleted),
      employeeId: new FormControl(workExperienceRawValue.employeeId),
      yearOfExp: new FormControl(workExperienceRawValue.yearOfExp),
      jobDesc: new FormControl(workExperienceRawValue.jobDesc),
      employee: new FormControl(workExperienceRawValue.employee),
    });
  }

  getWorkExperience(form: WorkExperienceFormGroup): IWorkExperience | NewWorkExperience {
    return this.convertWorkExperienceRawValueToWorkExperience(
      form.getRawValue() as WorkExperienceFormRawValue | NewWorkExperienceFormRawValue
    );
  }

  resetForm(form: WorkExperienceFormGroup, workExperience: WorkExperienceFormGroupInput): void {
    const workExperienceRawValue = this.convertWorkExperienceToWorkExperienceRawValue({ ...this.getFormDefaults(), ...workExperience });
    form.reset(
      {
        ...workExperienceRawValue,
        id: { value: workExperienceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): WorkExperienceFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertWorkExperienceRawValueToWorkExperience(
    rawWorkExperience: WorkExperienceFormRawValue | NewWorkExperienceFormRawValue
  ): IWorkExperience | NewWorkExperience {
    return {
      ...rawWorkExperience,
      startDate: dayjs(rawWorkExperience.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawWorkExperience.endDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawWorkExperience.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawWorkExperience.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertWorkExperienceToWorkExperienceRawValue(
    workExperience: IWorkExperience | (Partial<NewWorkExperience> & WorkExperienceFormDefaults)
  ): WorkExperienceFormRawValue | PartialWithRequiredKeyOf<NewWorkExperienceFormRawValue> {
    return {
      ...workExperience,
      startDate: workExperience.startDate ? workExperience.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: workExperience.endDate ? workExperience.endDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: workExperience.lastModified ? workExperience.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: workExperience.createdOn ? workExperience.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
