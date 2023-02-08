import dayjs from 'dayjs/esm';

import { IDistrict, NewDistrict } from './district.model';

export const sampleWithRequiredData: IDistrict = {
  id: 85653,
  districtName: 'Soap invoice Dong',
};

export const sampleWithPartialData: IDistrict = {
  id: 46092,
  districtName: 'copy',
  createdOn: dayjs('2023-02-07T10:17'),
  deleted: 'wireless executive',
  lastModified: dayjs('2023-02-08T01:34'),
};

export const sampleWithFullData: IDistrict = {
  id: 48646,
  districtName: 'Virtual deposit bypassing',
  lgdCode: 2924,
  createdOn: dayjs('2023-02-07T19:39'),
  createdBy: 'transmitting',
  deleted: 'ivory Dollar',
  lastModified: dayjs('2023-02-07T13:17'),
  lastModifiedBy: 'experiences Usability',
};

export const sampleWithNewData: NewDistrict = {
  districtName: 'Rican Chicken action-items',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
