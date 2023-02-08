import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContacts, NewContacts } from '../contacts.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContacts for edit and NewContactsFormGroupInput for create.
 */
type ContactsFormGroupInput = IContacts | PartialWithRequiredKeyOf<NewContacts>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IContacts | NewContacts> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type ContactsFormRawValue = FormValueOf<IContacts>;

type NewContactsFormRawValue = FormValueOf<NewContacts>;

type ContactsFormDefaults = Pick<NewContacts, 'id' | 'lastModified' | 'createdOn' | 'deleted'>;

type ContactsFormGroupContent = {
  id: FormControl<ContactsFormRawValue['id'] | NewContacts['id']>;
  name: FormControl<ContactsFormRawValue['name']>;
  type: FormControl<ContactsFormRawValue['type']>;
  relation: FormControl<ContactsFormRawValue['relation']>;
  phoneNo1: FormControl<ContactsFormRawValue['phoneNo1']>;
  phoneNo2: FormControl<ContactsFormRawValue['phoneNo2']>;
  lastModified: FormControl<ContactsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ContactsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<ContactsFormRawValue['createdBy']>;
  createdOn: FormControl<ContactsFormRawValue['createdOn']>;
  deleted: FormControl<ContactsFormRawValue['deleted']>;
  employeeId: FormControl<ContactsFormRawValue['employeeId']>;
  employee: FormControl<ContactsFormRawValue['employee']>;
};

export type ContactsFormGroup = FormGroup<ContactsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContactsFormService {
  createContactsFormGroup(contacts: ContactsFormGroupInput = { id: null }): ContactsFormGroup {
    const contactsRawValue = this.convertContactsToContactsRawValue({
      ...this.getFormDefaults(),
      ...contacts,
    });
    return new FormGroup<ContactsFormGroupContent>({
      id: new FormControl(
        { value: contactsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(contactsRawValue.name),
      type: new FormControl(contactsRawValue.type),
      relation: new FormControl(contactsRawValue.relation),
      phoneNo1: new FormControl(contactsRawValue.phoneNo1),
      phoneNo2: new FormControl(contactsRawValue.phoneNo2),
      lastModified: new FormControl(contactsRawValue.lastModified),
      lastModifiedBy: new FormControl(contactsRawValue.lastModifiedBy),
      createdBy: new FormControl(contactsRawValue.createdBy),
      createdOn: new FormControl(contactsRawValue.createdOn),
      deleted: new FormControl(contactsRawValue.deleted),
      employeeId: new FormControl(contactsRawValue.employeeId),
      employee: new FormControl(contactsRawValue.employee),
    });
  }

  getContacts(form: ContactsFormGroup): IContacts | NewContacts {
    return this.convertContactsRawValueToContacts(form.getRawValue() as ContactsFormRawValue | NewContactsFormRawValue);
  }

  resetForm(form: ContactsFormGroup, contacts: ContactsFormGroupInput): void {
    const contactsRawValue = this.convertContactsToContactsRawValue({ ...this.getFormDefaults(), ...contacts });
    form.reset(
      {
        ...contactsRawValue,
        id: { value: contactsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ContactsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      deleted: false,
    };
  }

  private convertContactsRawValueToContacts(rawContacts: ContactsFormRawValue | NewContactsFormRawValue): IContacts | NewContacts {
    return {
      ...rawContacts,
      lastModified: dayjs(rawContacts.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawContacts.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertContactsToContactsRawValue(
    contacts: IContacts | (Partial<NewContacts> & ContactsFormDefaults)
  ): ContactsFormRawValue | PartialWithRequiredKeyOf<NewContactsFormRawValue> {
    return {
      ...contacts,
      lastModified: contacts.lastModified ? contacts.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: contacts.createdOn ? contacts.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
