import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBanksDetails, NewBanksDetails } from '../banks-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBanksDetails for edit and NewBanksDetailsFormGroupInput for create.
 */
type BanksDetailsFormGroupInput = IBanksDetails | PartialWithRequiredKeyOf<NewBanksDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IBanksDetails | NewBanksDetails> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type BanksDetailsFormRawValue = FormValueOf<IBanksDetails>;

type NewBanksDetailsFormRawValue = FormValueOf<NewBanksDetails>;

type BanksDetailsFormDefaults = Pick<NewBanksDetails, 'id' | 'lastModified' | 'createdOn' | 'deleted'>;

type BanksDetailsFormGroupContent = {
  id: FormControl<BanksDetailsFormRawValue['id'] | NewBanksDetails['id']>;
  accountNo: FormControl<BanksDetailsFormRawValue['accountNo']>;
  bankName: FormControl<BanksDetailsFormRawValue['bankName']>;
  ifscCode: FormControl<BanksDetailsFormRawValue['ifscCode']>;
  pan: FormControl<BanksDetailsFormRawValue['pan']>;
  branch: FormControl<BanksDetailsFormRawValue['branch']>;
  lastModified: FormControl<BanksDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<BanksDetailsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<BanksDetailsFormRawValue['createdBy']>;
  createdOn: FormControl<BanksDetailsFormRawValue['createdOn']>;
  deleted: FormControl<BanksDetailsFormRawValue['deleted']>;
  employeeId: FormControl<BanksDetailsFormRawValue['employeeId']>;
  employee: FormControl<BanksDetailsFormRawValue['employee']>;
};

export type BanksDetailsFormGroup = FormGroup<BanksDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BanksDetailsFormService {
  createBanksDetailsFormGroup(banksDetails: BanksDetailsFormGroupInput = { id: null }): BanksDetailsFormGroup {
    const banksDetailsRawValue = this.convertBanksDetailsToBanksDetailsRawValue({
      ...this.getFormDefaults(),
      ...banksDetails,
    });
    return new FormGroup<BanksDetailsFormGroupContent>({
      id: new FormControl(
        { value: banksDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      accountNo: new FormControl(banksDetailsRawValue.accountNo),
      bankName: new FormControl(banksDetailsRawValue.bankName),
      ifscCode: new FormControl(banksDetailsRawValue.ifscCode),
      pan: new FormControl(banksDetailsRawValue.pan),
      branch: new FormControl(banksDetailsRawValue.branch),
      lastModified: new FormControl(banksDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(banksDetailsRawValue.lastModifiedBy),
      createdBy: new FormControl(banksDetailsRawValue.createdBy),
      createdOn: new FormControl(banksDetailsRawValue.createdOn),
      deleted: new FormControl(banksDetailsRawValue.deleted),
      employeeId: new FormControl(banksDetailsRawValue.employeeId),
      employee: new FormControl(banksDetailsRawValue.employee),
    });
  }

  getBanksDetails(form: BanksDetailsFormGroup): IBanksDetails | NewBanksDetails {
    return this.convertBanksDetailsRawValueToBanksDetails(form.getRawValue() as BanksDetailsFormRawValue | NewBanksDetailsFormRawValue);
  }

  resetForm(form: BanksDetailsFormGroup, banksDetails: BanksDetailsFormGroupInput): void {
    const banksDetailsRawValue = this.convertBanksDetailsToBanksDetailsRawValue({ ...this.getFormDefaults(), ...banksDetails });
    form.reset(
      {
        ...banksDetailsRawValue,
        id: { value: banksDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BanksDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertBanksDetailsRawValueToBanksDetails(
    rawBanksDetails: BanksDetailsFormRawValue | NewBanksDetailsFormRawValue
  ): IBanksDetails | NewBanksDetails {
    return {
      ...rawBanksDetails,
      lastModified: dayjs(rawBanksDetails.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawBanksDetails.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertBanksDetailsToBanksDetailsRawValue(
    banksDetails: IBanksDetails | (Partial<NewBanksDetails> & BanksDetailsFormDefaults)
  ): BanksDetailsFormRawValue | PartialWithRequiredKeyOf<NewBanksDetailsFormRawValue> {
    return {
      ...banksDetails,
      lastModified: banksDetails.lastModified ? banksDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: banksDetails.createdOn ? banksDetails.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
