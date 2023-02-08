import dayjs from 'dayjs/esm';

import { IDesignation, NewDesignation } from './designation.model';

export const sampleWithRequiredData: IDesignation = {
  id: 18278,
};

export const sampleWithPartialData: IDesignation = {
  id: 55630,
  designationName: 'Innovative',
  lastModified: dayjs('2023-02-08T01:28'),
  createdBy: 'solutions plug-and-play Electronics',
};

export const sampleWithFullData: IDesignation = {
  id: 39299,
  designationName: 'De-engineered Account',
  description: 'generate connect',
  lastModified: dayjs('2023-02-08T01:10'),
  lastModifiedBy: 'overriding withdrawal',
  createdBy: 'real-time Awesome Monaco',
  createdOn: dayjs('2023-02-07T23:46'),
  deleted: false,
};

export const sampleWithNewData: NewDesignation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
