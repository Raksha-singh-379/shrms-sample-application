import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFamilyInfo, NewFamilyInfo } from '../family-info.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFamilyInfo for edit and NewFamilyInfoFormGroupInput for create.
 */
type FamilyInfoFormGroupInput = IFamilyInfo | PartialWithRequiredKeyOf<NewFamilyInfo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFamilyInfo | NewFamilyInfo> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type FamilyInfoFormRawValue = FormValueOf<IFamilyInfo>;

type NewFamilyInfoFormRawValue = FormValueOf<NewFamilyInfo>;

type FamilyInfoFormDefaults = Pick<NewFamilyInfo, 'id' | 'lastModified' | 'createdOn' | 'deleted'>;

type FamilyInfoFormGroupContent = {
  id: FormControl<FamilyInfoFormRawValue['id'] | NewFamilyInfo['id']>;
  name: FormControl<FamilyInfoFormRawValue['name']>;
  dob: FormControl<FamilyInfoFormRawValue['dob']>;
  relation: FormControl<FamilyInfoFormRawValue['relation']>;
  phoneNo: FormControl<FamilyInfoFormRawValue['phoneNo']>;
  address: FormControl<FamilyInfoFormRawValue['address']>;
  lastModified: FormControl<FamilyInfoFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<FamilyInfoFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<FamilyInfoFormRawValue['createdBy']>;
  createdOn: FormControl<FamilyInfoFormRawValue['createdOn']>;
  deleted: FormControl<FamilyInfoFormRawValue['deleted']>;
  employeeId: FormControl<FamilyInfoFormRawValue['employeeId']>;
  employee: FormControl<FamilyInfoFormRawValue['employee']>;
};

export type FamilyInfoFormGroup = FormGroup<FamilyInfoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FamilyInfoFormService {
  createFamilyInfoFormGroup(familyInfo: FamilyInfoFormGroupInput = { id: null }): FamilyInfoFormGroup {
    const familyInfoRawValue = this.convertFamilyInfoToFamilyInfoRawValue({
      ...this.getFormDefaults(),
      ...familyInfo,
    });
    return new FormGroup<FamilyInfoFormGroupContent>({
      id: new FormControl(
        { value: familyInfoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(familyInfoRawValue.name),
      dob: new FormControl(familyInfoRawValue.dob),
      relation: new FormControl(familyInfoRawValue.relation),
      phoneNo: new FormControl(familyInfoRawValue.phoneNo),
      address: new FormControl(familyInfoRawValue.address),
      lastModified: new FormControl(familyInfoRawValue.lastModified),
      lastModifiedBy: new FormControl(familyInfoRawValue.lastModifiedBy),
      createdBy: new FormControl(familyInfoRawValue.createdBy),
      createdOn: new FormControl(familyInfoRawValue.createdOn),
      deleted: new FormControl(familyInfoRawValue.deleted),
      employeeId: new FormControl(familyInfoRawValue.employeeId),
      employee: new FormControl(familyInfoRawValue.employee),
    });
  }

  getFamilyInfo(form: FamilyInfoFormGroup): IFamilyInfo | NewFamilyInfo {
    return this.convertFamilyInfoRawValueToFamilyInfo(form.getRawValue() as FamilyInfoFormRawValue | NewFamilyInfoFormRawValue);
  }

  resetForm(form: FamilyInfoFormGroup, familyInfo: FamilyInfoFormGroupInput): void {
    const familyInfoRawValue = this.convertFamilyInfoToFamilyInfoRawValue({ ...this.getFormDefaults(), ...familyInfo });
    form.reset(
      {
        ...familyInfoRawValue,
        id: { value: familyInfoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FamilyInfoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertFamilyInfoRawValueToFamilyInfo(
    rawFamilyInfo: FamilyInfoFormRawValue | NewFamilyInfoFormRawValue
  ): IFamilyInfo | NewFamilyInfo {
    return {
      ...rawFamilyInfo,
      lastModified: dayjs(rawFamilyInfo.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawFamilyInfo.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertFamilyInfoToFamilyInfoRawValue(
    familyInfo: IFamilyInfo | (Partial<NewFamilyInfo> & FamilyInfoFormDefaults)
  ): FamilyInfoFormRawValue | PartialWithRequiredKeyOf<NewFamilyInfoFormRawValue> {
    return {
      ...familyInfo,
      lastModified: familyInfo.lastModified ? familyInfo.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: familyInfo.createdOn ? familyInfo.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
