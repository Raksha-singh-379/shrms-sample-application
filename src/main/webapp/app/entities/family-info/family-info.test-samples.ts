import dayjs from 'dayjs/esm';

import { Relationship } from 'app/entities/enumerations/relationship.model';

import { IFamilyInfo, NewFamilyInfo } from './family-info.model';

export const sampleWithRequiredData: IFamilyInfo = {
  id: 35605,
};

export const sampleWithPartialData: IFamilyInfo = {
  id: 22997,
  lastModifiedBy: 'orchestration streamline',
  createdBy: 'Producer',
  createdOn: dayjs('2023-02-08T00:17'),
  employeeId: 84671,
};

export const sampleWithFullData: IFamilyInfo = {
  id: 28900,
  name: 'copying Isle synthesize',
  dob: dayjs('2023-02-07'),
  relation: Relationship['BROTHER'],
  phoneNo: 'e-services Bacon Toys',
  address: 'Nakfa',
  lastModified: dayjs('2023-02-07T22:55'),
  lastModifiedBy: 'Metal SDD',
  createdBy: 'transmitting Kentucky connecting',
  createdOn: dayjs('2023-02-07T20:52'),
  deleted: true,
  employeeId: 77106,
};

export const sampleWithNewData: NewFamilyInfo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
