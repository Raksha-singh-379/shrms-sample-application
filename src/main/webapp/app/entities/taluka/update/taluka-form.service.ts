import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITaluka, NewTaluka } from '../taluka.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITaluka for edit and NewTalukaFormGroupInput for create.
 */
type TalukaFormGroupInput = ITaluka | PartialWithRequiredKeyOf<NewTaluka>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITaluka | NewTaluka> = Omit<T, 'createdOn' | 'lastModified'> & {
  createdOn?: string | null;
  lastModified?: string | null;
};

type TalukaFormRawValue = FormValueOf<ITaluka>;

type NewTalukaFormRawValue = FormValueOf<NewTaluka>;

type TalukaFormDefaults = Pick<NewTaluka, 'id' | 'createdOn' | 'lastModified'>;

type TalukaFormGroupContent = {
  id: FormControl<TalukaFormRawValue['id'] | NewTaluka['id']>;
  talukaName: FormControl<TalukaFormRawValue['talukaName']>;
  lgdCode: FormControl<TalukaFormRawValue['lgdCode']>;
  createdOn: FormControl<TalukaFormRawValue['createdOn']>;
  createdBy: FormControl<TalukaFormRawValue['createdBy']>;
  deleted: FormControl<TalukaFormRawValue['deleted']>;
  lastModified: FormControl<TalukaFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<TalukaFormRawValue['lastModifiedBy']>;
};

export type TalukaFormGroup = FormGroup<TalukaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TalukaFormService {
  createTalukaFormGroup(taluka: TalukaFormGroupInput = { id: null }): TalukaFormGroup {
    const talukaRawValue = this.convertTalukaToTalukaRawValue({
      ...this.getFormDefaults(),
      ...taluka,
    });
    return new FormGroup<TalukaFormGroupContent>({
      id: new FormControl(
        { value: talukaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      talukaName: new FormControl(talukaRawValue.talukaName, {
        validators: [Validators.required],
      }),
      lgdCode: new FormControl(talukaRawValue.lgdCode),
      createdOn: new FormControl(talukaRawValue.createdOn),
      createdBy: new FormControl(talukaRawValue.createdBy),
      deleted: new FormControl(talukaRawValue.deleted),
      lastModified: new FormControl(talukaRawValue.lastModified),
      lastModifiedBy: new FormControl(talukaRawValue.lastModifiedBy),
    });
  }

  getTaluka(form: TalukaFormGroup): ITaluka | NewTaluka {
    return this.convertTalukaRawValueToTaluka(form.getRawValue() as TalukaFormRawValue | NewTalukaFormRawValue);
  }

  resetForm(form: TalukaFormGroup, taluka: TalukaFormGroupInput): void {
    const talukaRawValue = this.convertTalukaToTalukaRawValue({ ...this.getFormDefaults(), ...taluka });
    form.reset(
      {
        ...talukaRawValue,
        id: { value: talukaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TalukaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdOn: currentTime,
      lastModified: currentTime,
    };
  }

  private convertTalukaRawValueToTaluka(rawTaluka: TalukaFormRawValue | NewTalukaFormRawValue): ITaluka | NewTaluka {
    return {
      ...rawTaluka,
      createdOn: dayjs(rawTaluka.createdOn, DATE_TIME_FORMAT),
      lastModified: dayjs(rawTaluka.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertTalukaToTalukaRawValue(
    taluka: ITaluka | (Partial<NewTaluka> & TalukaFormDefaults)
  ): TalukaFormRawValue | PartialWithRequiredKeyOf<NewTalukaFormRawValue> {
    return {
      ...taluka,
      createdOn: taluka.createdOn ? taluka.createdOn.format(DATE_TIME_FORMAT) : undefined,
      lastModified: taluka.lastModified ? taluka.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
