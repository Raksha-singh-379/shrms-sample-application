import dayjs from 'dayjs/esm';

import { Status } from 'app/entities/enumerations/status.model';

import { ILeaveType, NewLeaveType } from './leave-type.model';

export const sampleWithRequiredData: ILeaveType = {
  id: 21210,
};

export const sampleWithPartialData: ILeaveType = {
  id: 59639,
  noOfDays: 'Principal',
  lastModified: dayjs('2023-02-07T14:55'),
  createdBy: 'programming invoice',
  deleted: true,
};

export const sampleWithFullData: ILeaveType = {
  id: 42071,
  leaveType: 'Denmark Books Response',
  noOfDays: 'Strategist',
  status: Status['ACTIVE'],
  lastModified: dayjs('2023-02-07T14:43'),
  lastModifiedBy: 'connecting upward-trending analyzer',
  createdBy: 'Avenue Borders Market',
  createdOn: dayjs('2023-02-07T19:14'),
  deleted: false,
};

export const sampleWithNewData: NewLeaveType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
