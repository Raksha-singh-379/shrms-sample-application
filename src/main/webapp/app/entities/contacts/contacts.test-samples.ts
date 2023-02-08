import dayjs from 'dayjs/esm';

import { ContactType } from 'app/entities/enumerations/contact-type.model';
import { Relationship } from 'app/entities/enumerations/relationship.model';

import { IContacts, NewContacts } from './contacts.model';

export const sampleWithRequiredData: IContacts = {
  id: 22084,
};

export const sampleWithPartialData: IContacts = {
  id: 55483,
  type: ContactType['SECONDARY'],
  relation: Relationship['SPOUS'],
  lastModified: dayjs('2023-02-07T21:47'),
  lastModifiedBy: 'Dynamic',
  createdOn: dayjs('2023-02-07T13:39'),
};

export const sampleWithFullData: IContacts = {
  id: 69190,
  name: 'Communications Card',
  type: ContactType['PRIMARY'],
  relation: Relationship['CHILD'],
  phoneNo1: 'Danish PCI Direct',
  phoneNo2: 'National matrices',
  lastModified: dayjs('2023-02-07T06:20'),
  lastModifiedBy: 'ADP communities Specialist',
  createdBy: 'Granite',
  createdOn: dayjs('2023-02-08T03:44'),
  deleted: false,
  employeeId: 92306,
};

export const sampleWithNewData: NewContacts = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
