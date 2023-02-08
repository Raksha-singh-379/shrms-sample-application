import dayjs from 'dayjs/esm';

import { ITaluka, NewTaluka } from './taluka.model';

export const sampleWithRequiredData: ITaluka = {
  id: 3499,
  talukaName: 'Handmade quantify web-readiness',
};

export const sampleWithPartialData: ITaluka = {
  id: 22461,
  talukaName: 'navigating Innovative Direct',
  lgdCode: 61243,
  createdOn: dayjs('2023-02-07T17:45'),
  createdBy: 'Branding Avon',
  deleted: 'Focused generating',
  lastModified: dayjs('2023-02-08T01:14'),
  lastModifiedBy: 'Investment Lane Planner',
};

export const sampleWithFullData: ITaluka = {
  id: 79209,
  talukaName: 'Games',
  lgdCode: 39831,
  createdOn: dayjs('2023-02-08T04:52'),
  createdBy: 'Borders Sleek Chicken',
  deleted: 'compressing Cambridgeshire',
  lastModified: dayjs('2023-02-08T05:40'),
  lastModifiedBy: 'attitude-oriented',
};

export const sampleWithNewData: NewTaluka = {
  talukaName: 'Applications',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
