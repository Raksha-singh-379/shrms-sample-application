import dayjs from 'dayjs/esm';

import { IDepartment, NewDepartment } from './department.model';

export const sampleWithRequiredData: IDepartment = {
  id: 39095,
};

export const sampleWithPartialData: IDepartment = {
  id: 15133,
  departmentName: 'Incredible Games Table',
  lastModified: dayjs('2023-02-08T01:14'),
  lastModifiedBy: 'Morocco',
  deleted: false,
};

export const sampleWithFullData: IDepartment = {
  id: 97334,
  departmentName: 'portal Dollar green',
  description: 'Cambridgeshire target Metal',
  lastModified: dayjs('2023-02-07T06:57'),
  lastModifiedBy: 'Games black',
  createdBy: 'well-modulated website',
  createdOn: dayjs('2023-02-08T00:40'),
  deleted: false,
};

export const sampleWithNewData: NewDepartment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
