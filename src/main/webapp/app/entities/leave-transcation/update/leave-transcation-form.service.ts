import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILeaveTranscation, NewLeaveTranscation } from '../leave-transcation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILeaveTranscation for edit and NewLeaveTranscationFormGroupInput for create.
 */
type LeaveTranscationFormGroupInput = ILeaveTranscation | PartialWithRequiredKeyOf<NewLeaveTranscation>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILeaveTranscation | NewLeaveTranscation> = Omit<T, 'monthDate' | 'lastModified' | 'createdOn'> & {
  monthDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type LeaveTranscationFormRawValue = FormValueOf<ILeaveTranscation>;

type NewLeaveTranscationFormRawValue = FormValueOf<NewLeaveTranscation>;

type LeaveTranscationFormDefaults = Pick<NewLeaveTranscation, 'id' | 'monthDate' | 'lastModified' | 'createdOn' | 'deleted'>;

type LeaveTranscationFormGroupContent = {
  id: FormControl<LeaveTranscationFormRawValue['id'] | NewLeaveTranscation['id']>;
  leaveType: FormControl<LeaveTranscationFormRawValue['leaveType']>;
  empId: FormControl<LeaveTranscationFormRawValue['empId']>;
  monthDate: FormControl<LeaveTranscationFormRawValue['monthDate']>;
  status: FormControl<LeaveTranscationFormRawValue['status']>;
  year: FormControl<LeaveTranscationFormRawValue['year']>;
  lastModified: FormControl<LeaveTranscationFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LeaveTranscationFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<LeaveTranscationFormRawValue['createdBy']>;
  createdOn: FormControl<LeaveTranscationFormRawValue['createdOn']>;
  deleted: FormControl<LeaveTranscationFormRawValue['deleted']>;
};

export type LeaveTranscationFormGroup = FormGroup<LeaveTranscationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LeaveTranscationFormService {
  createLeaveTranscationFormGroup(leaveTranscation: LeaveTranscationFormGroupInput = { id: null }): LeaveTranscationFormGroup {
    const leaveTranscationRawValue = this.convertLeaveTranscationToLeaveTranscationRawValue({
      ...this.getFormDefaults(),
      ...leaveTranscation,
    });
    return new FormGroup<LeaveTranscationFormGroupContent>({
      id: new FormControl(
        { value: leaveTranscationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      leaveType: new FormControl(leaveTranscationRawValue.leaveType),
      empId: new FormControl(leaveTranscationRawValue.empId),
      monthDate: new FormControl(leaveTranscationRawValue.monthDate),
      status: new FormControl(leaveTranscationRawValue.status),
      year: new FormControl(leaveTranscationRawValue.year),
      lastModified: new FormControl(leaveTranscationRawValue.lastModified),
      lastModifiedBy: new FormControl(leaveTranscationRawValue.lastModifiedBy),
      createdBy: new FormControl(leaveTranscationRawValue.createdBy),
      createdOn: new FormControl(leaveTranscationRawValue.createdOn),
      deleted: new FormControl(leaveTranscationRawValue.deleted),
    });
  }

  getLeaveTranscation(form: LeaveTranscationFormGroup): ILeaveTranscation | NewLeaveTranscation {
    return this.convertLeaveTranscationRawValueToLeaveTranscation(
      form.getRawValue() as LeaveTranscationFormRawValue | NewLeaveTranscationFormRawValue
    );
  }

  resetForm(form: LeaveTranscationFormGroup, leaveTranscation: LeaveTranscationFormGroupInput): void {
    const leaveTranscationRawValue = this.convertLeaveTranscationToLeaveTranscationRawValue({
      ...this.getFormDefaults(),
      ...leaveTranscation,
    });
    form.reset(
      {
        ...leaveTranscationRawValue,
        id: { value: leaveTranscationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LeaveTranscationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      monthDate: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertLeaveTranscationRawValueToLeaveTranscation(
    rawLeaveTranscation: LeaveTranscationFormRawValue | NewLeaveTranscationFormRawValue
  ): ILeaveTranscation | NewLeaveTranscation {
    return {
      ...rawLeaveTranscation,
      monthDate: dayjs(rawLeaveTranscation.monthDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawLeaveTranscation.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawLeaveTranscation.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertLeaveTranscationToLeaveTranscationRawValue(
    leaveTranscation: ILeaveTranscation | (Partial<NewLeaveTranscation> & LeaveTranscationFormDefaults)
  ): LeaveTranscationFormRawValue | PartialWithRequiredKeyOf<NewLeaveTranscationFormRawValue> {
    return {
      ...leaveTranscation,
      monthDate: leaveTranscation.monthDate ? leaveTranscation.monthDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: leaveTranscation.lastModified ? leaveTranscation.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: leaveTranscation.createdOn ? leaveTranscation.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
