import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILeaveType, NewLeaveType } from '../leave-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILeaveType for edit and NewLeaveTypeFormGroupInput for create.
 */
type LeaveTypeFormGroupInput = ILeaveType | PartialWithRequiredKeyOf<NewLeaveType>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILeaveType | NewLeaveType> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type LeaveTypeFormRawValue = FormValueOf<ILeaveType>;

type NewLeaveTypeFormRawValue = FormValueOf<NewLeaveType>;

type LeaveTypeFormDefaults = Pick<NewLeaveType, 'id' | 'lastModified' | 'createdOn' | 'deleted'>;

type LeaveTypeFormGroupContent = {
  id: FormControl<LeaveTypeFormRawValue['id'] | NewLeaveType['id']>;
  leaveType: FormControl<LeaveTypeFormRawValue['leaveType']>;
  noOfDays: FormControl<LeaveTypeFormRawValue['noOfDays']>;
  status: FormControl<LeaveTypeFormRawValue['status']>;
  lastModified: FormControl<LeaveTypeFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LeaveTypeFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<LeaveTypeFormRawValue['createdBy']>;
  createdOn: FormControl<LeaveTypeFormRawValue['createdOn']>;
  deleted: FormControl<LeaveTypeFormRawValue['deleted']>;
};

export type LeaveTypeFormGroup = FormGroup<LeaveTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LeaveTypeFormService {
  createLeaveTypeFormGroup(leaveType: LeaveTypeFormGroupInput = { id: null }): LeaveTypeFormGroup {
    const leaveTypeRawValue = this.convertLeaveTypeToLeaveTypeRawValue({
      ...this.getFormDefaults(),
      ...leaveType,
    });
    return new FormGroup<LeaveTypeFormGroupContent>({
      id: new FormControl(
        { value: leaveTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      leaveType: new FormControl(leaveTypeRawValue.leaveType),
      noOfDays: new FormControl(leaveTypeRawValue.noOfDays),
      status: new FormControl(leaveTypeRawValue.status),
      lastModified: new FormControl(leaveTypeRawValue.lastModified),
      lastModifiedBy: new FormControl(leaveTypeRawValue.lastModifiedBy),
      createdBy: new FormControl(leaveTypeRawValue.createdBy),
      createdOn: new FormControl(leaveTypeRawValue.createdOn),
      deleted: new FormControl(leaveTypeRawValue.deleted),
    });
  }

  getLeaveType(form: LeaveTypeFormGroup): ILeaveType | NewLeaveType {
    return this.convertLeaveTypeRawValueToLeaveType(form.getRawValue() as LeaveTypeFormRawValue | NewLeaveTypeFormRawValue);
  }

  resetForm(form: LeaveTypeFormGroup, leaveType: LeaveTypeFormGroupInput): void {
    const leaveTypeRawValue = this.convertLeaveTypeToLeaveTypeRawValue({ ...this.getFormDefaults(), ...leaveType });
    form.reset(
      {
        ...leaveTypeRawValue,
        id: { value: leaveTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LeaveTypeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertLeaveTypeRawValueToLeaveType(rawLeaveType: LeaveTypeFormRawValue | NewLeaveTypeFormRawValue): ILeaveType | NewLeaveType {
    return {
      ...rawLeaveType,
      lastModified: dayjs(rawLeaveType.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawLeaveType.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertLeaveTypeToLeaveTypeRawValue(
    leaveType: ILeaveType | (Partial<NewLeaveType> & LeaveTypeFormDefaults)
  ): LeaveTypeFormRawValue | PartialWithRequiredKeyOf<NewLeaveTypeFormRawValue> {
    return {
      ...leaveType,
      lastModified: leaveType.lastModified ? leaveType.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: leaveType.createdOn ? leaveType.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
