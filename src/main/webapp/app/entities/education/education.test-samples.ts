import dayjs from 'dayjs/esm';

import { Degree } from 'app/entities/enumerations/degree.model';

import { IEducation, NewEducation } from './education.model';

export const sampleWithRequiredData: IEducation = {
  id: 81904,
};

export const sampleWithPartialData: IEducation = {
  id: 29588,
  institution: 'Ergonomic leverage Concrete',
  endDate: dayjs('2023-02-07T18:31'),
  description: 'Fish',
  deleted: true,
  employeeId: 26097,
};

export const sampleWithFullData: IEducation = {
  id: 5999,
  institution: 'applications leverage dedicated',
  subject: 'Ergonomic',
  startDate: dayjs('2023-02-07T15:14'),
  endDate: dayjs('2023-02-07T16:30'),
  degree: Degree['OTHER'],
  grade: 'panel Guiana circuit',
  description: 'Money Steel Producer',
  lastModified: dayjs('2023-02-08T04:14'),
  lastModifiedBy: 'programming Checking Engineer',
  createdBy: 'Product',
  createdOn: dayjs('2023-02-07T10:42'),
  deleted: true,
  employeeId: 94960,
};

export const sampleWithNewData: NewEducation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
