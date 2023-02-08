import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmployeeDetails, NewEmployeeDetails } from '../employee-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployeeDetails for edit and NewEmployeeDetailsFormGroupInput for create.
 */
type EmployeeDetailsFormGroupInput = IEmployeeDetails | PartialWithRequiredKeyOf<NewEmployeeDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmployeeDetails | NewEmployeeDetails> = Omit<T, 'passportExpDate' | 'createdOn' | 'lastModified'> & {
  passportExpDate?: string | null;
  createdOn?: string | null;
  lastModified?: string | null;
};

type EmployeeDetailsFormRawValue = FormValueOf<IEmployeeDetails>;

type NewEmployeeDetailsFormRawValue = FormValueOf<NewEmployeeDetails>;

type EmployeeDetailsFormDefaults = Pick<
  NewEmployeeDetails,
  'id' | 'passportExpDate' | 'isSpousEmployed' | 'createdOn' | 'lastModified' | 'deleted'
>;

type EmployeeDetailsFormGroupContent = {
  id: FormControl<EmployeeDetailsFormRawValue['id'] | NewEmployeeDetails['id']>;
  passportNo: FormControl<EmployeeDetailsFormRawValue['passportNo']>;
  passportExpDate: FormControl<EmployeeDetailsFormRawValue['passportExpDate']>;
  telephoneNo: FormControl<EmployeeDetailsFormRawValue['telephoneNo']>;
  nationality: FormControl<EmployeeDetailsFormRawValue['nationality']>;
  maritalStatus: FormControl<EmployeeDetailsFormRawValue['maritalStatus']>;
  religion: FormControl<EmployeeDetailsFormRawValue['religion']>;
  isSpousEmployed: FormControl<EmployeeDetailsFormRawValue['isSpousEmployed']>;
  noOfChildren: FormControl<EmployeeDetailsFormRawValue['noOfChildren']>;
  createdBy: FormControl<EmployeeDetailsFormRawValue['createdBy']>;
  createdOn: FormControl<EmployeeDetailsFormRawValue['createdOn']>;
  lastModified: FormControl<EmployeeDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<EmployeeDetailsFormRawValue['lastModifiedBy']>;
  deleted: FormControl<EmployeeDetailsFormRawValue['deleted']>;
  employeeId: FormControl<EmployeeDetailsFormRawValue['employeeId']>;
  age: FormControl<EmployeeDetailsFormRawValue['age']>;
  fatherName: FormControl<EmployeeDetailsFormRawValue['fatherName']>;
  motherName: FormControl<EmployeeDetailsFormRawValue['motherName']>;
  aadharNo: FormControl<EmployeeDetailsFormRawValue['aadharNo']>;
  bloodGroup: FormControl<EmployeeDetailsFormRawValue['bloodGroup']>;
  dob: FormControl<EmployeeDetailsFormRawValue['dob']>;
  expertise: FormControl<EmployeeDetailsFormRawValue['expertise']>;
  hobbies: FormControl<EmployeeDetailsFormRawValue['hobbies']>;
  areaInterest: FormControl<EmployeeDetailsFormRawValue['areaInterest']>;
  languageKnown: FormControl<EmployeeDetailsFormRawValue['languageKnown']>;
  description: FormControl<EmployeeDetailsFormRawValue['description']>;
  employee: FormControl<EmployeeDetailsFormRawValue['employee']>;
};

export type EmployeeDetailsFormGroup = FormGroup<EmployeeDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeDetailsFormService {
  createEmployeeDetailsFormGroup(employeeDetails: EmployeeDetailsFormGroupInput = { id: null }): EmployeeDetailsFormGroup {
    const employeeDetailsRawValue = this.convertEmployeeDetailsToEmployeeDetailsRawValue({
      ...this.getFormDefaults(),
      ...employeeDetails,
    });
    return new FormGroup<EmployeeDetailsFormGroupContent>({
      id: new FormControl(
        { value: employeeDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      passportNo: new FormControl(employeeDetailsRawValue.passportNo),
      passportExpDate: new FormControl(employeeDetailsRawValue.passportExpDate),
      telephoneNo: new FormControl(employeeDetailsRawValue.telephoneNo),
      nationality: new FormControl(employeeDetailsRawValue.nationality),
      maritalStatus: new FormControl(employeeDetailsRawValue.maritalStatus),
      religion: new FormControl(employeeDetailsRawValue.religion),
      isSpousEmployed: new FormControl(employeeDetailsRawValue.isSpousEmployed),
      noOfChildren: new FormControl(employeeDetailsRawValue.noOfChildren),
      createdBy: new FormControl(employeeDetailsRawValue.createdBy),
      createdOn: new FormControl(employeeDetailsRawValue.createdOn),
      lastModified: new FormControl(employeeDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(employeeDetailsRawValue.lastModifiedBy),
      deleted: new FormControl(employeeDetailsRawValue.deleted),
      employeeId: new FormControl(employeeDetailsRawValue.employeeId),
      age: new FormControl(employeeDetailsRawValue.age),
      fatherName: new FormControl(employeeDetailsRawValue.fatherName),
      motherName: new FormControl(employeeDetailsRawValue.motherName),
      aadharNo: new FormControl(employeeDetailsRawValue.aadharNo),
      bloodGroup: new FormControl(employeeDetailsRawValue.bloodGroup),
      dob: new FormControl(employeeDetailsRawValue.dob),
      expertise: new FormControl(employeeDetailsRawValue.expertise),
      hobbies: new FormControl(employeeDetailsRawValue.hobbies),
      areaInterest: new FormControl(employeeDetailsRawValue.areaInterest),
      languageKnown: new FormControl(employeeDetailsRawValue.languageKnown),
      description: new FormControl(employeeDetailsRawValue.description),
      employee: new FormControl(employeeDetailsRawValue.employee),
    });
  }

  getEmployeeDetails(form: EmployeeDetailsFormGroup): IEmployeeDetails | NewEmployeeDetails {
    return this.convertEmployeeDetailsRawValueToEmployeeDetails(
      form.getRawValue() as EmployeeDetailsFormRawValue | NewEmployeeDetailsFormRawValue
    );
  }

  resetForm(form: EmployeeDetailsFormGroup, employeeDetails: EmployeeDetailsFormGroupInput): void {
    const employeeDetailsRawValue = this.convertEmployeeDetailsToEmployeeDetailsRawValue({ ...this.getFormDefaults(), ...employeeDetails });
    form.reset(
      {
        ...employeeDetailsRawValue,
        id: { value: employeeDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EmployeeDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      passportExpDate: currentTime,
      isSpousEmployed: false,
      createdOn: currentTime,
      lastModified: currentTime,
      deleted: false,
    };
  }

  private convertEmployeeDetailsRawValueToEmployeeDetails(
    rawEmployeeDetails: EmployeeDetailsFormRawValue | NewEmployeeDetailsFormRawValue
  ): IEmployeeDetails | NewEmployeeDetails {
    return {
      ...rawEmployeeDetails,
      passportExpDate: dayjs(rawEmployeeDetails.passportExpDate, DATE_TIME_FORMAT),
      createdOn: dayjs(rawEmployeeDetails.createdOn, DATE_TIME_FORMAT),
      lastModified: dayjs(rawEmployeeDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertEmployeeDetailsToEmployeeDetailsRawValue(
    employeeDetails: IEmployeeDetails | (Partial<NewEmployeeDetails> & EmployeeDetailsFormDefaults)
  ): EmployeeDetailsFormRawValue | PartialWithRequiredKeyOf<NewEmployeeDetailsFormRawValue> {
    return {
      ...employeeDetails,
      passportExpDate: employeeDetails.passportExpDate ? employeeDetails.passportExpDate.format(DATE_TIME_FORMAT) : undefined,
      createdOn: employeeDetails.createdOn ? employeeDetails.createdOn.format(DATE_TIME_FORMAT) : undefined,
      lastModified: employeeDetails.lastModified ? employeeDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
