import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICity, NewCity } from '../city.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICity for edit and NewCityFormGroupInput for create.
 */
type CityFormGroupInput = ICity | PartialWithRequiredKeyOf<NewCity>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICity | NewCity> = Omit<T, 'createdOn' | 'lastModified'> & {
  createdOn?: string | null;
  lastModified?: string | null;
};

type CityFormRawValue = FormValueOf<ICity>;

type NewCityFormRawValue = FormValueOf<NewCity>;

type CityFormDefaults = Pick<NewCity, 'id' | 'createdOn' | 'lastModified'>;

type CityFormGroupContent = {
  id: FormControl<CityFormRawValue['id'] | NewCity['id']>;
  cityName: FormControl<CityFormRawValue['cityName']>;
  lgdCode: FormControl<CityFormRawValue['lgdCode']>;
  createdOn: FormControl<CityFormRawValue['createdOn']>;
  createdBy: FormControl<CityFormRawValue['createdBy']>;
  deleted: FormControl<CityFormRawValue['deleted']>;
  lastModified: FormControl<CityFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<CityFormRawValue['lastModifiedBy']>;
};

export type CityFormGroup = FormGroup<CityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CityFormService {
  createCityFormGroup(city: CityFormGroupInput = { id: null }): CityFormGroup {
    const cityRawValue = this.convertCityToCityRawValue({
      ...this.getFormDefaults(),
      ...city,
    });
    return new FormGroup<CityFormGroupContent>({
      id: new FormControl(
        { value: cityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cityName: new FormControl(cityRawValue.cityName, {
        validators: [Validators.required],
      }),
      lgdCode: new FormControl(cityRawValue.lgdCode),
      createdOn: new FormControl(cityRawValue.createdOn),
      createdBy: new FormControl(cityRawValue.createdBy),
      deleted: new FormControl(cityRawValue.deleted),
      lastModified: new FormControl(cityRawValue.lastModified),
      lastModifiedBy: new FormControl(cityRawValue.lastModifiedBy),
    });
  }

  getCity(form: CityFormGroup): ICity | NewCity {
    return this.convertCityRawValueToCity(form.getRawValue() as CityFormRawValue | NewCityFormRawValue);
  }

  resetForm(form: CityFormGroup, city: CityFormGroupInput): void {
    const cityRawValue = this.convertCityToCityRawValue({ ...this.getFormDefaults(), ...city });
    form.reset(
      {
        ...cityRawValue,
        id: { value: cityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CityFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdOn: currentTime,
      lastModified: currentTime,
    };
  }

  private convertCityRawValueToCity(rawCity: CityFormRawValue | NewCityFormRawValue): ICity | NewCity {
    return {
      ...rawCity,
      createdOn: dayjs(rawCity.createdOn, DATE_TIME_FORMAT),
      lastModified: dayjs(rawCity.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertCityToCityRawValue(
    city: ICity | (Partial<NewCity> & CityFormDefaults)
  ): CityFormRawValue | PartialWithRequiredKeyOf<NewCityFormRawValue> {
    return {
      ...city,
      createdOn: city.createdOn ? city.createdOn.format(DATE_TIME_FORMAT) : undefined,
      lastModified: city.lastModified ? city.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
