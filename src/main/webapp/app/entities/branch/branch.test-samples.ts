import dayjs from 'dayjs/esm';

import { BranchType } from 'app/entities/enumerations/branch-type.model';

import { IBranch, NewBranch } from './branch.model';

export const sampleWithRequiredData: IBranch = {
  id: 244,
  branchName: 'blue hack Intelligent',
};

export const sampleWithPartialData: IBranch = {
  id: 70268,
  branchName: 'Triple-buffered Metrics',
  description: 'deposit',
  ibanCode: 'matrix Quality Ecuador',
  phone: '716-646-5373 x851',
  email: 'Martine.Stanton64@hotmail.com',
  branchType: BranchType['ZONAL_OFFICE'],
  createdOn: dayjs('2023-02-07T07:23'),
  deleted: 'responsive',
  lastModifiedBy: 'Steel conglomeration action-items',
};

export const sampleWithFullData: IBranch = {
  id: 98563,
  branchName: 'protocol Health Polarised',
  description: 'Networked',
  branchcode: 'Florida Configuration International',
  ifscCode: 'calculate',
  micrCode: 'Handmade',
  swiftCode: 'Solutions',
  ibanCode: 'Investment',
  isHeadOffice: false,
  routingTransitNo: 'quantify Cheese',
  phone: '451.779.9685 x56671',
  email: 'Uriah48@yahoo.com',
  webSite: 'backing invoice Director',
  fax: 'web silver',
  isActivate: false,
  branchType: BranchType['ZONAL_OFFICE'],
  createdOn: dayjs('2023-02-07T23:41'),
  createdBy: 'Table Shirt Functionality',
  deleted: 'Re-contextualized',
  lastModified: dayjs('2023-02-07T18:18'),
  lastModifiedBy: 'Steel',
};

export const sampleWithNewData: NewBranch = {
  branchName: 'Shilling Organized',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
