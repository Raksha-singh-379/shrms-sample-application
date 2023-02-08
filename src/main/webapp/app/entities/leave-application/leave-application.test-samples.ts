import dayjs from 'dayjs/esm';

import { Status } from 'app/entities/enumerations/status.model';
import { LeaveStatus } from 'app/entities/enumerations/leave-status.model';

import { ILeaveApplication, NewLeaveApplication } from './leave-application.model';

export const sampleWithRequiredData: ILeaveApplication = {
  id: 91940,
};

export const sampleWithPartialData: ILeaveApplication = {
  id: 95755,
  noOfDays: 19260,
  reason: 'Global',
  toDate: dayjs('2023-02-07T07:36'),
  status: Status['INACTIVE'],
  lastModified: dayjs('2023-02-07T17:28'),
  lastModifiedBy: 'Franc',
  createdBy: 'Ergonomic',
  createdOn: dayjs('2023-02-07T17:40'),
};

export const sampleWithFullData: ILeaveApplication = {
  id: 54055,
  leaveType: 'Pants Savings',
  balanceLeave: 1506,
  noOfDays: 29909,
  reason: 'platforms demand-driven Bacon',
  year: 39420,
  formDate: dayjs('2023-02-07T11:44'),
  toDate: dayjs('2023-02-07T17:05'),
  status: Status['ACTIVE'],
  leaveStatus: LeaveStatus['APPROVED'],
  lastModified: dayjs('2023-02-08T05:57'),
  lastModifiedBy: 'Pants',
  createdBy: 'innovative embrace GB',
  createdOn: dayjs('2023-02-07T12:16'),
  deleted: true,
};

export const sampleWithNewData: NewLeaveApplication = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
