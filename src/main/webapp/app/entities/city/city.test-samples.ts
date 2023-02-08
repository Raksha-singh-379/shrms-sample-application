import dayjs from 'dayjs/esm';

import { ICity, NewCity } from './city.model';

export const sampleWithRequiredData: ICity = {
  id: 79135,
  cityName: 'up multi-byte',
};

export const sampleWithPartialData: ICity = {
  id: 62664,
  cityName: 'EXE',
  deleted: 'Account',
  lastModified: dayjs('2023-02-07T06:42'),
  lastModifiedBy: 'Cambridgeshire Chair',
};

export const sampleWithFullData: ICity = {
  id: 96263,
  cityName: 'District',
  lgdCode: 10637,
  createdOn: dayjs('2023-02-07T18:51'),
  createdBy: 'Granite Associate',
  deleted: 'Oregon',
  lastModified: dayjs('2023-02-08T03:39'),
  lastModifiedBy: 'Handcrafted',
};

export const sampleWithNewData: NewCity = {
  cityName: 'Automotive',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
