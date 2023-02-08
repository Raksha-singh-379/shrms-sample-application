import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHolidays, NewHolidays } from '../holidays.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHolidays for edit and NewHolidaysFormGroupInput for create.
 */
type HolidaysFormGroupInput = IHolidays | PartialWithRequiredKeyOf<NewHolidays>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IHolidays | NewHolidays> = Omit<T, 'holidayDate' | 'year' | 'lastModified' | 'createdOn'> & {
  holidayDate?: string | null;
  year?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type HolidaysFormRawValue = FormValueOf<IHolidays>;

type NewHolidaysFormRawValue = FormValueOf<NewHolidays>;

type HolidaysFormDefaults = Pick<NewHolidays, 'id' | 'holidayDate' | 'year' | 'lastModified' | 'createdOn' | 'deleted'>;

type HolidaysFormGroupContent = {
  id: FormControl<HolidaysFormRawValue['id'] | NewHolidays['id']>;
  holidayName: FormControl<HolidaysFormRawValue['holidayName']>;
  holidayDate: FormControl<HolidaysFormRawValue['holidayDate']>;
  day: FormControl<HolidaysFormRawValue['day']>;
  year: FormControl<HolidaysFormRawValue['year']>;
  lastModified: FormControl<HolidaysFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<HolidaysFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<HolidaysFormRawValue['createdBy']>;
  createdOn: FormControl<HolidaysFormRawValue['createdOn']>;
  deleted: FormControl<HolidaysFormRawValue['deleted']>;
  company: FormControl<HolidaysFormRawValue['company']>;
};

export type HolidaysFormGroup = FormGroup<HolidaysFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HolidaysFormService {
  createHolidaysFormGroup(holidays: HolidaysFormGroupInput = { id: null }): HolidaysFormGroup {
    const holidaysRawValue = this.convertHolidaysToHolidaysRawValue({
      ...this.getFormDefaults(),
      ...holidays,
    });
    return new FormGroup<HolidaysFormGroupContent>({
      id: new FormControl(
        { value: holidaysRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      holidayName: new FormControl(holidaysRawValue.holidayName, {
        validators: [Validators.required],
      }),
      holidayDate: new FormControl(holidaysRawValue.holidayDate),
      day: new FormControl(holidaysRawValue.day),
      year: new FormControl(holidaysRawValue.year),
      lastModified: new FormControl(holidaysRawValue.lastModified),
      lastModifiedBy: new FormControl(holidaysRawValue.lastModifiedBy),
      createdBy: new FormControl(holidaysRawValue.createdBy),
      createdOn: new FormControl(holidaysRawValue.createdOn),
      deleted: new FormControl(holidaysRawValue.deleted),
      company: new FormControl(holidaysRawValue.company),
    });
  }

  getHolidays(form: HolidaysFormGroup): IHolidays | NewHolidays {
    return this.convertHolidaysRawValueToHolidays(form.getRawValue() as HolidaysFormRawValue | NewHolidaysFormRawValue);
  }

  resetForm(form: HolidaysFormGroup, holidays: HolidaysFormGroupInput): void {
    const holidaysRawValue = this.convertHolidaysToHolidaysRawValue({ ...this.getFormDefaults(), ...holidays });
    form.reset(
      {
        ...holidaysRawValue,
        id: { value: holidaysRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): HolidaysFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      holidayDate: currentTime,
      year: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertHolidaysRawValueToHolidays(rawHolidays: HolidaysFormRawValue | NewHolidaysFormRawValue): IHolidays | NewHolidays {
    return {
      ...rawHolidays,
      holidayDate: dayjs(rawHolidays.holidayDate, DATE_TIME_FORMAT),
      year: dayjs(rawHolidays.year, DATE_TIME_FORMAT),
      lastModified: dayjs(rawHolidays.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawHolidays.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertHolidaysToHolidaysRawValue(
    holidays: IHolidays | (Partial<NewHolidays> & HolidaysFormDefaults)
  ): HolidaysFormRawValue | PartialWithRequiredKeyOf<NewHolidaysFormRawValue> {
    return {
      ...holidays,
      holidayDate: holidays.holidayDate ? holidays.holidayDate.format(DATE_TIME_FORMAT) : undefined,
      year: holidays.year ? holidays.year.format(DATE_TIME_FORMAT) : undefined,
      lastModified: holidays.lastModified ? holidays.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: holidays.createdOn ? holidays.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
