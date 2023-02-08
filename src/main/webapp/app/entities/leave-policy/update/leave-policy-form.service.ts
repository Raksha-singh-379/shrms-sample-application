import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILeavePolicy, NewLeavePolicy } from '../leave-policy.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILeavePolicy for edit and NewLeavePolicyFormGroupInput for create.
 */
type LeavePolicyFormGroupInput = ILeavePolicy | PartialWithRequiredKeyOf<NewLeavePolicy>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILeavePolicy | NewLeavePolicy> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type LeavePolicyFormRawValue = FormValueOf<ILeavePolicy>;

type NewLeavePolicyFormRawValue = FormValueOf<NewLeavePolicy>;

type LeavePolicyFormDefaults = Pick<NewLeavePolicy, 'id' | 'isCarryForword' | 'hasproRataLeave' | 'lastModified' | 'createdOn' | 'deleted'>;

type LeavePolicyFormGroupContent = {
  id: FormControl<LeavePolicyFormRawValue['id'] | NewLeavePolicy['id']>;
  leaveType: FormControl<LeavePolicyFormRawValue['leaveType']>;
  isCarryForword: FormControl<LeavePolicyFormRawValue['isCarryForword']>;
  employeeType: FormControl<LeavePolicyFormRawValue['employeeType']>;
  genderLeave: FormControl<LeavePolicyFormRawValue['genderLeave']>;
  status: FormControl<LeavePolicyFormRawValue['status']>;
  totalLeave: FormControl<LeavePolicyFormRawValue['totalLeave']>;
  maxLeave: FormControl<LeavePolicyFormRawValue['maxLeave']>;
  hasproRataLeave: FormControl<LeavePolicyFormRawValue['hasproRataLeave']>;
  description: FormControl<LeavePolicyFormRawValue['description']>;
  lastModified: FormControl<LeavePolicyFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LeavePolicyFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<LeavePolicyFormRawValue['createdBy']>;
  createdOn: FormControl<LeavePolicyFormRawValue['createdOn']>;
  deleted: FormControl<LeavePolicyFormRawValue['deleted']>;
};

export type LeavePolicyFormGroup = FormGroup<LeavePolicyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LeavePolicyFormService {
  createLeavePolicyFormGroup(leavePolicy: LeavePolicyFormGroupInput = { id: null }): LeavePolicyFormGroup {
    const leavePolicyRawValue = this.convertLeavePolicyToLeavePolicyRawValue({
      ...this.getFormDefaults(),
      ...leavePolicy,
    });
    return new FormGroup<LeavePolicyFormGroupContent>({
      id: new FormControl(
        { value: leavePolicyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      leaveType: new FormControl(leavePolicyRawValue.leaveType),
      isCarryForword: new FormControl(leavePolicyRawValue.isCarryForword),
      employeeType: new FormControl(leavePolicyRawValue.employeeType),
      genderLeave: new FormControl(leavePolicyRawValue.genderLeave),
      status: new FormControl(leavePolicyRawValue.status),
      totalLeave: new FormControl(leavePolicyRawValue.totalLeave),
      maxLeave: new FormControl(leavePolicyRawValue.maxLeave),
      hasproRataLeave: new FormControl(leavePolicyRawValue.hasproRataLeave),
      description: new FormControl(leavePolicyRawValue.description),
      lastModified: new FormControl(leavePolicyRawValue.lastModified),
      lastModifiedBy: new FormControl(leavePolicyRawValue.lastModifiedBy),
      createdBy: new FormControl(leavePolicyRawValue.createdBy),
      createdOn: new FormControl(leavePolicyRawValue.createdOn),
      deleted: new FormControl(leavePolicyRawValue.deleted),
    });
  }

  getLeavePolicy(form: LeavePolicyFormGroup): ILeavePolicy | NewLeavePolicy {
    return this.convertLeavePolicyRawValueToLeavePolicy(form.getRawValue() as LeavePolicyFormRawValue | NewLeavePolicyFormRawValue);
  }

  resetForm(form: LeavePolicyFormGroup, leavePolicy: LeavePolicyFormGroupInput): void {
    const leavePolicyRawValue = this.convertLeavePolicyToLeavePolicyRawValue({ ...this.getFormDefaults(), ...leavePolicy });
    form.reset(
      {
        ...leavePolicyRawValue,
        id: { value: leavePolicyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LeavePolicyFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isCarryForword: false,
      hasproRataLeave: false,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertLeavePolicyRawValueToLeavePolicy(
    rawLeavePolicy: LeavePolicyFormRawValue | NewLeavePolicyFormRawValue
  ): ILeavePolicy | NewLeavePolicy {
    return {
      ...rawLeavePolicy,
      lastModified: dayjs(rawLeavePolicy.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawLeavePolicy.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertLeavePolicyToLeavePolicyRawValue(
    leavePolicy: ILeavePolicy | (Partial<NewLeavePolicy> & LeavePolicyFormDefaults)
  ): LeavePolicyFormRawValue | PartialWithRequiredKeyOf<NewLeavePolicyFormRawValue> {
    return {
      ...leavePolicy,
      lastModified: leavePolicy.lastModified ? leavePolicy.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: leavePolicy.createdOn ? leavePolicy.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
