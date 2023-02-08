import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDepartment, NewDepartment } from '../department.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDepartment for edit and NewDepartmentFormGroupInput for create.
 */
type DepartmentFormGroupInput = IDepartment | PartialWithRequiredKeyOf<NewDepartment>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDepartment | NewDepartment> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type DepartmentFormRawValue = FormValueOf<IDepartment>;

type NewDepartmentFormRawValue = FormValueOf<NewDepartment>;

type DepartmentFormDefaults = Pick<NewDepartment, 'id' | 'lastModified' | 'createdOn' | 'deleted'>;

type DepartmentFormGroupContent = {
  id: FormControl<DepartmentFormRawValue['id'] | NewDepartment['id']>;
  departmentName: FormControl<DepartmentFormRawValue['departmentName']>;
  description: FormControl<DepartmentFormRawValue['description']>;
  lastModified: FormControl<DepartmentFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<DepartmentFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<DepartmentFormRawValue['createdBy']>;
  createdOn: FormControl<DepartmentFormRawValue['createdOn']>;
  deleted: FormControl<DepartmentFormRawValue['deleted']>;
  company: FormControl<DepartmentFormRawValue['company']>;
};

export type DepartmentFormGroup = FormGroup<DepartmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DepartmentFormService {
  createDepartmentFormGroup(department: DepartmentFormGroupInput = { id: null }): DepartmentFormGroup {
    const departmentRawValue = this.convertDepartmentToDepartmentRawValue({
      ...this.getFormDefaults(),
      ...department,
    });
    return new FormGroup<DepartmentFormGroupContent>({
      id: new FormControl(
        { value: departmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      departmentName: new FormControl(departmentRawValue.departmentName),
      description: new FormControl(departmentRawValue.description),
      lastModified: new FormControl(departmentRawValue.lastModified),
      lastModifiedBy: new FormControl(departmentRawValue.lastModifiedBy),
      createdBy: new FormControl(departmentRawValue.createdBy),
      createdOn: new FormControl(departmentRawValue.createdOn),
      deleted: new FormControl(departmentRawValue.deleted),
      company: new FormControl(departmentRawValue.company),
    });
  }

  getDepartment(form: DepartmentFormGroup): IDepartment | NewDepartment {
    return this.convertDepartmentRawValueToDepartment(form.getRawValue() as DepartmentFormRawValue | NewDepartmentFormRawValue);
  }

  resetForm(form: DepartmentFormGroup, department: DepartmentFormGroupInput): void {
    const departmentRawValue = this.convertDepartmentToDepartmentRawValue({ ...this.getFormDefaults(), ...department });
    form.reset(
      {
        ...departmentRawValue,
        id: { value: departmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DepartmentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertDepartmentRawValueToDepartment(
    rawDepartment: DepartmentFormRawValue | NewDepartmentFormRawValue
  ): IDepartment | NewDepartment {
    return {
      ...rawDepartment,
      lastModified: dayjs(rawDepartment.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawDepartment.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertDepartmentToDepartmentRawValue(
    department: IDepartment | (Partial<NewDepartment> & DepartmentFormDefaults)
  ): DepartmentFormRawValue | PartialWithRequiredKeyOf<NewDepartmentFormRawValue> {
    return {
      ...department,
      lastModified: department.lastModified ? department.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: department.createdOn ? department.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
