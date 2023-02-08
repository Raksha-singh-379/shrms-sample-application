import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILeaveApplication, NewLeaveApplication } from '../leave-application.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILeaveApplication for edit and NewLeaveApplicationFormGroupInput for create.
 */
type LeaveApplicationFormGroupInput = ILeaveApplication | PartialWithRequiredKeyOf<NewLeaveApplication>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILeaveApplication | NewLeaveApplication> = Omit<T, 'formDate' | 'toDate' | 'lastModified' | 'createdOn'> & {
  formDate?: string | null;
  toDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type LeaveApplicationFormRawValue = FormValueOf<ILeaveApplication>;

type NewLeaveApplicationFormRawValue = FormValueOf<NewLeaveApplication>;

type LeaveApplicationFormDefaults = Pick<NewLeaveApplication, 'id' | 'formDate' | 'toDate' | 'lastModified' | 'createdOn' | 'deleted'>;

type LeaveApplicationFormGroupContent = {
  id: FormControl<LeaveApplicationFormRawValue['id'] | NewLeaveApplication['id']>;
  leaveType: FormControl<LeaveApplicationFormRawValue['leaveType']>;
  balanceLeave: FormControl<LeaveApplicationFormRawValue['balanceLeave']>;
  noOfDays: FormControl<LeaveApplicationFormRawValue['noOfDays']>;
  reason: FormControl<LeaveApplicationFormRawValue['reason']>;
  year: FormControl<LeaveApplicationFormRawValue['year']>;
  formDate: FormControl<LeaveApplicationFormRawValue['formDate']>;
  toDate: FormControl<LeaveApplicationFormRawValue['toDate']>;
  status: FormControl<LeaveApplicationFormRawValue['status']>;
  leaveStatus: FormControl<LeaveApplicationFormRawValue['leaveStatus']>;
  lastModified: FormControl<LeaveApplicationFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LeaveApplicationFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<LeaveApplicationFormRawValue['createdBy']>;
  createdOn: FormControl<LeaveApplicationFormRawValue['createdOn']>;
  deleted: FormControl<LeaveApplicationFormRawValue['deleted']>;
  employee: FormControl<LeaveApplicationFormRawValue['employee']>;
  leavePolicy: FormControl<LeaveApplicationFormRawValue['leavePolicy']>;
};

export type LeaveApplicationFormGroup = FormGroup<LeaveApplicationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LeaveApplicationFormService {
  createLeaveApplicationFormGroup(leaveApplication: LeaveApplicationFormGroupInput = { id: null }): LeaveApplicationFormGroup {
    const leaveApplicationRawValue = this.convertLeaveApplicationToLeaveApplicationRawValue({
      ...this.getFormDefaults(),
      ...leaveApplication,
    });
    return new FormGroup<LeaveApplicationFormGroupContent>({
      id: new FormControl(
        { value: leaveApplicationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      leaveType: new FormControl(leaveApplicationRawValue.leaveType),
      balanceLeave: new FormControl(leaveApplicationRawValue.balanceLeave),
      noOfDays: new FormControl(leaveApplicationRawValue.noOfDays),
      reason: new FormControl(leaveApplicationRawValue.reason),
      year: new FormControl(leaveApplicationRawValue.year),
      formDate: new FormControl(leaveApplicationRawValue.formDate),
      toDate: new FormControl(leaveApplicationRawValue.toDate),
      status: new FormControl(leaveApplicationRawValue.status),
      leaveStatus: new FormControl(leaveApplicationRawValue.leaveStatus),
      lastModified: new FormControl(leaveApplicationRawValue.lastModified),
      lastModifiedBy: new FormControl(leaveApplicationRawValue.lastModifiedBy),
      createdBy: new FormControl(leaveApplicationRawValue.createdBy),
      createdOn: new FormControl(leaveApplicationRawValue.createdOn),
      deleted: new FormControl(leaveApplicationRawValue.deleted),
      employee: new FormControl(leaveApplicationRawValue.employee),
      leavePolicy: new FormControl(leaveApplicationRawValue.leavePolicy),
    });
  }

  getLeaveApplication(form: LeaveApplicationFormGroup): ILeaveApplication | NewLeaveApplication {
    return this.convertLeaveApplicationRawValueToLeaveApplication(
      form.getRawValue() as LeaveApplicationFormRawValue | NewLeaveApplicationFormRawValue
    );
  }

  resetForm(form: LeaveApplicationFormGroup, leaveApplication: LeaveApplicationFormGroupInput): void {
    const leaveApplicationRawValue = this.convertLeaveApplicationToLeaveApplicationRawValue({
      ...this.getFormDefaults(),
      ...leaveApplication,
    });
    form.reset(
      {
        ...leaveApplicationRawValue,
        id: { value: leaveApplicationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LeaveApplicationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      formDate: currentTime,
      toDate: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertLeaveApplicationRawValueToLeaveApplication(
    rawLeaveApplication: LeaveApplicationFormRawValue | NewLeaveApplicationFormRawValue
  ): ILeaveApplication | NewLeaveApplication {
    return {
      ...rawLeaveApplication,
      formDate: dayjs(rawLeaveApplication.formDate, DATE_TIME_FORMAT),
      toDate: dayjs(rawLeaveApplication.toDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawLeaveApplication.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawLeaveApplication.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertLeaveApplicationToLeaveApplicationRawValue(
    leaveApplication: ILeaveApplication | (Partial<NewLeaveApplication> & LeaveApplicationFormDefaults)
  ): LeaveApplicationFormRawValue | PartialWithRequiredKeyOf<NewLeaveApplicationFormRawValue> {
    return {
      ...leaveApplication,
      formDate: leaveApplication.formDate ? leaveApplication.formDate.format(DATE_TIME_FORMAT) : undefined,
      toDate: leaveApplication.toDate ? leaveApplication.toDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: leaveApplication.lastModified ? leaveApplication.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: leaveApplication.createdOn ? leaveApplication.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
