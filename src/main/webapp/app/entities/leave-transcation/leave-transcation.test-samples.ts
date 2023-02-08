import dayjs from 'dayjs/esm';

import { Status } from 'app/entities/enumerations/status.model';

import { ILeaveTranscation, NewLeaveTranscation } from './leave-transcation.model';

export const sampleWithRequiredData: ILeaveTranscation = {
  id: 15921,
};

export const sampleWithPartialData: ILeaveTranscation = {
  id: 56976,
  leaveType: 'Avon systems',
  monthDate: dayjs('2023-02-08T00:05'),
  status: Status['ACTIVE'],
  lastModified: dayjs('2023-02-08T03:33'),
  createdBy: 'Savings',
  createdOn: dayjs('2023-02-08T03:25'),
};

export const sampleWithFullData: ILeaveTranscation = {
  id: 69850,
  leaveType: 'array unleash Island',
  empId: 'HTTP cross-media',
  monthDate: dayjs('2023-02-07T20:59'),
  status: Status['INACTIVE'],
  year: 20396,
  lastModified: dayjs('2023-02-07T11:43'),
  lastModifiedBy: 'Minnesota exuding',
  createdBy: 'scalable experiences',
  createdOn: dayjs('2023-02-08T00:53'),
  deleted: true,
};

export const sampleWithNewData: NewLeaveTranscation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
