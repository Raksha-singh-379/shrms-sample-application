import dayjs from 'dayjs/esm';

import { IBanksDetails, NewBanksDetails } from './banks-details.model';

export const sampleWithRequiredData: IBanksDetails = {
  id: 92491,
};

export const sampleWithPartialData: IBanksDetails = {
  id: 68068,
  bankName: 'Extended parallelism',
  ifscCode: 'PNG',
  pan: 'Computer',
  branch: 'Vatu',
  lastModified: dayjs('2023-02-07T10:43'),
  createdOn: dayjs('2023-02-07T07:37'),
};

export const sampleWithFullData: IBanksDetails = {
  id: 56434,
  accountNo: 45252,
  bankName: 'microchip',
  ifscCode: 'bandwidth Tuna Mouse',
  pan: 'Account Mountains',
  branch: 'SMTP',
  lastModified: dayjs('2023-02-07T15:07'),
  lastModifiedBy: 'Towels Account',
  createdBy: 'synergies indexing',
  createdOn: dayjs('2023-02-07T16:35'),
  deleted: true,
  employeeId: 75764,
};

export const sampleWithNewData: NewBanksDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
