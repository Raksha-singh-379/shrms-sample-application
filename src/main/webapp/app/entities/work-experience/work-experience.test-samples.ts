import dayjs from 'dayjs/esm';

import { IWorkExperience, NewWorkExperience } from './work-experience.model';

export const sampleWithRequiredData: IWorkExperience = {
  id: 92187,
};

export const sampleWithPartialData: IWorkExperience = {
  id: 72935,
  jobTitle: 'Investor Web Assistant',
  companyName: 'real-time',
  employeeId: 44521,
  jobDesc: 'proactive',
};

export const sampleWithFullData: IWorkExperience = {
  id: 70973,
  jobTitle: 'Global Interactions Director',
  companyName: 'Computer',
  address: 'methodologies Clothing',
  startDate: dayjs('2023-02-07T15:31'),
  endDate: dayjs('2023-02-08T00:34'),
  lastModified: dayjs('2023-02-07T21:05'),
  lastModifiedBy: '9(E.U.A.-9) Frozen SAS',
  createdBy: 'users Handmade sensor',
  createdOn: dayjs('2023-02-07T15:45'),
  isDeleted: false,
  employeeId: 53220,
  yearOfExp: 16624,
  jobDesc: 'Rustic neural',
};

export const sampleWithNewData: NewWorkExperience = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
