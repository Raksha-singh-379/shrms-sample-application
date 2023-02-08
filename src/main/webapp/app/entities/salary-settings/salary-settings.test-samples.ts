import dayjs from 'dayjs/esm';

import { ISalarySettings, NewSalarySettings } from './salary-settings.model';

export const sampleWithRequiredData: ISalarySettings = {
  id: 18645,
};

export const sampleWithPartialData: ISalarySettings = {
  id: 17493,
  da: 90199,
  salaryFrom: dayjs('2023-02-07T07:12'),
  lastModified: dayjs('2023-02-07T16:20'),
  createdBy: 'synthesizing Books',
  createdOn: dayjs('2023-02-07T09:06'),
  deleted: true,
};

export const sampleWithFullData: ISalarySettings = {
  id: 93136,
  da: 35194,
  hra: 80054,
  employeeshare: 81767,
  companyshare: 93357,
  salaryFrom: dayjs('2023-02-08T03:52'),
  salaryTo: dayjs('2023-02-07T21:48'),
  lastModified: dayjs('2023-02-07T16:55'),
  lastModifiedBy: 'Frozen',
  createdBy: 'connect Group Rwanda',
  createdOn: dayjs('2023-02-07T16:05'),
  deleted: true,
};

export const sampleWithNewData: NewSalarySettings = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
