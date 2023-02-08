import dayjs from 'dayjs/esm';

import { EmployeeType } from 'app/entities/enumerations/employee-type.model';
import { GenderLeave } from 'app/entities/enumerations/gender-leave.model';
import { Status } from 'app/entities/enumerations/status.model';

import { ILeavePolicy, NewLeavePolicy } from './leave-policy.model';

export const sampleWithRequiredData: ILeavePolicy = {
  id: 78331,
};

export const sampleWithPartialData: ILeavePolicy = {
  id: 51664,
  totalLeave: 'Account Ball',
  maxLeave: 'Sports Florida',
  hasproRataLeave: false,
  lastModified: dayjs('2023-02-08T00:10'),
  lastModifiedBy: 'Human',
};

export const sampleWithFullData: ILeavePolicy = {
  id: 14403,
  leaveType: 'Central up',
  isCarryForword: false,
  employeeType: EmployeeType['TRAINEE'],
  genderLeave: GenderLeave['FEMALE_ONLY'],
  status: Status['ACTIVE'],
  totalLeave: 'Account Montenegro',
  maxLeave: 'sensor Rubber',
  hasproRataLeave: true,
  description: 'indigo',
  lastModified: dayjs('2023-02-08T03:04'),
  lastModifiedBy: 'Gloves Wooden',
  createdBy: 'groupware',
  createdOn: dayjs('2023-02-07T10:52'),
  deleted: false,
};

export const sampleWithNewData: NewLeavePolicy = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
