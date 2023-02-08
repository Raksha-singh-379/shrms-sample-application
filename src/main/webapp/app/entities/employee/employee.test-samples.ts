import dayjs from 'dayjs/esm';

import { Gender } from 'app/entities/enumerations/gender.model';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 7813,
  username: 'transmitting',
  password: 'scale Mexican Administrator',
  employeeId: 'Granite',
};

export const sampleWithPartialData: IEmployee = {
  id: 46107,
  username: 'Accountability invoice Berkshire',
  password: 'viral Rubber Fundamental',
  email: 'Stanford16@yahoo.com',
  department: 'Iranian embrace',
  gender: Gender['MALE'],
  employeeId: 'haptic haptic deposit',
  createdOn: dayjs('2023-02-07T21:06'),
  reportedTo: 3076,
  regionId: 11720,
};

export const sampleWithFullData: IEmployee = {
  id: 2373,
  firstName: 'Jackeline',
  lastName: 'Crooks',
  username: 'Investor feed Chicken',
  password: 'azure',
  email: 'Damion.Pagac21@hotmail.com',
  phone: '659.790.8948',
  mobile: 'Loan Brand Crossroad',
  department: 'Planner optimal Cambridgeshire',
  designation: 'bluetooth Synergized',
  gender: Gender['FEMALE'],
  employeeId: 'invoice program',
  joindate: dayjs('2023-02-07T23:28'),
  createdBy: 'synergistic Product Botswana',
  createdOn: dayjs('2023-02-07T16:50'),
  lastModified: dayjs('2023-02-07T06:30'),
  lastModifiedBy: 'paradigms bypassing',
  deleted: false,
  reportedTo: 60682,
  companyId: 13039,
  branchId: 6738,
  regionId: 85521,
};

export const sampleWithNewData: NewEmployee = {
  username: 'Specialist overriding',
  password: 'Dram',
  employeeId: 'bypassing overriding communities',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
