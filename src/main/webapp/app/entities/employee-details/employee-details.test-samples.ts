import dayjs from 'dayjs/esm';

import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';

import { IEmployeeDetails, NewEmployeeDetails } from './employee-details.model';

export const sampleWithRequiredData: IEmployeeDetails = {
  id: 43079,
};

export const sampleWithPartialData: IEmployeeDetails = {
  id: 2026,
  nationality: 'connecting Garden Account',
  maritalStatus: MaritalStatus['MARRIED'],
  religion: 'Customer Forks',
  isSpousEmployed: true,
  createdBy: 'tan Loan',
  deleted: true,
  employeeId: 94709,
  age: 1097,
  fatherName: 'indigo invoice',
  motherName: 'Account revolutionary',
  expertise: 'Haven human-resource',
  areaInterest: 'Coordinator synthesizing',
};

export const sampleWithFullData: IEmployeeDetails = {
  id: 61424,
  passportNo: 'Communications value-added',
  passportExpDate: dayjs('2023-02-07T15:40'),
  telephoneNo: 'Officer',
  nationality: 'Delaware bottom-line Chicken',
  maritalStatus: MaritalStatus['SINGLE'],
  religion: 'Freeway zero',
  isSpousEmployed: true,
  noOfChildren: 51339,
  createdBy: 'Synergized',
  createdOn: dayjs('2023-02-08T04:12'),
  lastModified: dayjs('2023-02-07T13:26'),
  lastModifiedBy: 'zero',
  deleted: true,
  employeeId: 30541,
  age: 8535,
  fatherName: 'back-end 1080p',
  motherName: 'Technician B2B',
  aadharNo: 'Automated orchestration',
  bloodGroup: BloodGroup['AB_POSITIVE'],
  dob: dayjs('2023-02-07'),
  expertise: 'Bhutan',
  hobbies: 'asynchronous didactic e-business',
  areaInterest: 'tan Intuitive',
  languageKnown: 'driver',
  description: 'cross-platform open-source',
};

export const sampleWithNewData: NewEmployeeDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
