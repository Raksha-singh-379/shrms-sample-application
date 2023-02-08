import dayjs from 'dayjs/esm';

import { AddressType } from 'app/entities/enumerations/address-type.model';

import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 88754,
};

export const sampleWithPartialData: IAddress = {
  id: 83562,
  type: AddressType['EMPLOYMENT_ADDRESS'],
  line1: 'bleeding-edge Colorado Buckinghamshire',
  state: 'Mississippi Persevering',
  pincode: 'Ameliorated',
  hasPrefered: true,
  landMark: 'Focused Intelligent District',
  lastModifiedBy: 'Chair viral Soap',
  createdBy: 'bypass United hack',
  employeeId: 76316,
};

export const sampleWithFullData: IAddress = {
  id: 35347,
  type: AddressType['PERMANENT_ADDRESS'],
  line1: 'Birr experiences Re-contextualized',
  line2: 'Compatible black maximize',
  country: 'Norfolk Island',
  state: 'Paraguay',
  pincode: 'Island Investment',
  hasPrefered: true,
  landMark: 'Consultant',
  lastModified: dayjs('2023-02-07T08:57'),
  lastModifiedBy: 'experiences Books',
  createdBy: 'Buckinghamshire',
  createdOn: dayjs('2023-02-07T09:03'),
  deleted: true,
  employeeId: 30696,
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
