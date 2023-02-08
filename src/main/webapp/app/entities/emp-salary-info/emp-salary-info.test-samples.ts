import dayjs from 'dayjs/esm';

import { SalaryBasis } from 'app/entities/enumerations/salary-basis.model';

import { IEmpSalaryInfo, NewEmpSalaryInfo } from './emp-salary-info.model';

export const sampleWithRequiredData: IEmpSalaryInfo = {
  id: 47574,
};

export const sampleWithPartialData: IEmpSalaryInfo = {
  id: 1112,
  salaryBasis: SalaryBasis['MONTHLY'],
  amount: 86373,
  isPfContribution: false,
  pfRate: 53347,
  isEsiContribution: false,
  esiRate: 36624,
  additionalEsiRate: 'architect payment Kingdom',
  totalEsiRate: 17312,
  employeId: 45234,
  lastModified: dayjs('2023-02-07T12:32'),
  lastModifiedBy: 'embrace Personal',
  createdBy: 'SMS',
  deleted: true,
};

export const sampleWithFullData: IEmpSalaryInfo = {
  id: 71032,
  salaryBasis: SalaryBasis['DAILY'],
  amount: 51595,
  paymentType: 'Borders',
  isPfContribution: false,
  pfNumber: 'wireless indexing bluetooth',
  pfRate: 55940,
  additionalPfRate: 'input',
  totalPfRate: 55150,
  isEsiContribution: true,
  esiNumber: 'invoice Investment cross-platform',
  esiRate: 42145,
  additionalEsiRate: 'knowledge Toys',
  totalEsiRate: 91541,
  employeId: 94826,
  lastModified: dayjs('2023-02-08T05:55'),
  lastModifiedBy: 'overriding quantifying',
  createdBy: 'Shilling compress THX',
  createdOn: dayjs('2023-02-07T09:15'),
  deleted: true,
};

export const sampleWithNewData: NewEmpSalaryInfo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
