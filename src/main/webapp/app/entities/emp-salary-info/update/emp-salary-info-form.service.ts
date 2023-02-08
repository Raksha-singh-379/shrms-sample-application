import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmpSalaryInfo, NewEmpSalaryInfo } from '../emp-salary-info.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmpSalaryInfo for edit and NewEmpSalaryInfoFormGroupInput for create.
 */
type EmpSalaryInfoFormGroupInput = IEmpSalaryInfo | PartialWithRequiredKeyOf<NewEmpSalaryInfo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmpSalaryInfo | NewEmpSalaryInfo> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type EmpSalaryInfoFormRawValue = FormValueOf<IEmpSalaryInfo>;

type NewEmpSalaryInfoFormRawValue = FormValueOf<NewEmpSalaryInfo>;

type EmpSalaryInfoFormDefaults = Pick<
  NewEmpSalaryInfo,
  'id' | 'isPfContribution' | 'isEsiContribution' | 'lastModified' | 'createdOn' | 'deleted'
>;

type EmpSalaryInfoFormGroupContent = {
  id: FormControl<EmpSalaryInfoFormRawValue['id'] | NewEmpSalaryInfo['id']>;
  salaryBasis: FormControl<EmpSalaryInfoFormRawValue['salaryBasis']>;
  amount: FormControl<EmpSalaryInfoFormRawValue['amount']>;
  paymentType: FormControl<EmpSalaryInfoFormRawValue['paymentType']>;
  isPfContribution: FormControl<EmpSalaryInfoFormRawValue['isPfContribution']>;
  pfNumber: FormControl<EmpSalaryInfoFormRawValue['pfNumber']>;
  pfRate: FormControl<EmpSalaryInfoFormRawValue['pfRate']>;
  additionalPfRate: FormControl<EmpSalaryInfoFormRawValue['additionalPfRate']>;
  totalPfRate: FormControl<EmpSalaryInfoFormRawValue['totalPfRate']>;
  isEsiContribution: FormControl<EmpSalaryInfoFormRawValue['isEsiContribution']>;
  esiNumber: FormControl<EmpSalaryInfoFormRawValue['esiNumber']>;
  esiRate: FormControl<EmpSalaryInfoFormRawValue['esiRate']>;
  additionalEsiRate: FormControl<EmpSalaryInfoFormRawValue['additionalEsiRate']>;
  totalEsiRate: FormControl<EmpSalaryInfoFormRawValue['totalEsiRate']>;
  employeId: FormControl<EmpSalaryInfoFormRawValue['employeId']>;
  lastModified: FormControl<EmpSalaryInfoFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<EmpSalaryInfoFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<EmpSalaryInfoFormRawValue['createdBy']>;
  createdOn: FormControl<EmpSalaryInfoFormRawValue['createdOn']>;
  deleted: FormControl<EmpSalaryInfoFormRawValue['deleted']>;
  employee: FormControl<EmpSalaryInfoFormRawValue['employee']>;
};

export type EmpSalaryInfoFormGroup = FormGroup<EmpSalaryInfoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmpSalaryInfoFormService {
  createEmpSalaryInfoFormGroup(empSalaryInfo: EmpSalaryInfoFormGroupInput = { id: null }): EmpSalaryInfoFormGroup {
    const empSalaryInfoRawValue = this.convertEmpSalaryInfoToEmpSalaryInfoRawValue({
      ...this.getFormDefaults(),
      ...empSalaryInfo,
    });
    return new FormGroup<EmpSalaryInfoFormGroupContent>({
      id: new FormControl(
        { value: empSalaryInfoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      salaryBasis: new FormControl(empSalaryInfoRawValue.salaryBasis),
      amount: new FormControl(empSalaryInfoRawValue.amount),
      paymentType: new FormControl(empSalaryInfoRawValue.paymentType),
      isPfContribution: new FormControl(empSalaryInfoRawValue.isPfContribution),
      pfNumber: new FormControl(empSalaryInfoRawValue.pfNumber),
      pfRate: new FormControl(empSalaryInfoRawValue.pfRate),
      additionalPfRate: new FormControl(empSalaryInfoRawValue.additionalPfRate),
      totalPfRate: new FormControl(empSalaryInfoRawValue.totalPfRate),
      isEsiContribution: new FormControl(empSalaryInfoRawValue.isEsiContribution),
      esiNumber: new FormControl(empSalaryInfoRawValue.esiNumber),
      esiRate: new FormControl(empSalaryInfoRawValue.esiRate),
      additionalEsiRate: new FormControl(empSalaryInfoRawValue.additionalEsiRate),
      totalEsiRate: new FormControl(empSalaryInfoRawValue.totalEsiRate),
      employeId: new FormControl(empSalaryInfoRawValue.employeId),
      lastModified: new FormControl(empSalaryInfoRawValue.lastModified),
      lastModifiedBy: new FormControl(empSalaryInfoRawValue.lastModifiedBy),
      createdBy: new FormControl(empSalaryInfoRawValue.createdBy),
      createdOn: new FormControl(empSalaryInfoRawValue.createdOn),
      deleted: new FormControl(empSalaryInfoRawValue.deleted),
      employee: new FormControl(empSalaryInfoRawValue.employee),
    });
  }

  getEmpSalaryInfo(form: EmpSalaryInfoFormGroup): IEmpSalaryInfo | NewEmpSalaryInfo {
    return this.convertEmpSalaryInfoRawValueToEmpSalaryInfo(form.getRawValue() as EmpSalaryInfoFormRawValue | NewEmpSalaryInfoFormRawValue);
  }

  resetForm(form: EmpSalaryInfoFormGroup, empSalaryInfo: EmpSalaryInfoFormGroupInput): void {
    const empSalaryInfoRawValue = this.convertEmpSalaryInfoToEmpSalaryInfoRawValue({ ...this.getFormDefaults(), ...empSalaryInfo });
    form.reset(
      {
        ...empSalaryInfoRawValue,
        id: { value: empSalaryInfoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EmpSalaryInfoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isPfContribution: false,
      isEsiContribution: false,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertEmpSalaryInfoRawValueToEmpSalaryInfo(
    rawEmpSalaryInfo: EmpSalaryInfoFormRawValue | NewEmpSalaryInfoFormRawValue
  ): IEmpSalaryInfo | NewEmpSalaryInfo {
    return {
      ...rawEmpSalaryInfo,
      lastModified: dayjs(rawEmpSalaryInfo.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawEmpSalaryInfo.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertEmpSalaryInfoToEmpSalaryInfoRawValue(
    empSalaryInfo: IEmpSalaryInfo | (Partial<NewEmpSalaryInfo> & EmpSalaryInfoFormDefaults)
  ): EmpSalaryInfoFormRawValue | PartialWithRequiredKeyOf<NewEmpSalaryInfoFormRawValue> {
    return {
      ...empSalaryInfo,
      lastModified: empSalaryInfo.lastModified ? empSalaryInfo.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: empSalaryInfo.createdOn ? empSalaryInfo.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
