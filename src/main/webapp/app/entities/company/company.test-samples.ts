import dayjs from 'dayjs/esm';

import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: 32440,
  regNumber: 'Djibouti Washington',
  gstin: 'Awesome Avon Avon',
};

export const sampleWithPartialData: ICompany = {
  id: 27933,
  address: 'Chips Ford',
  email: 'Bethany_Kris15@yahoo.com',
  mobileNumber: 'RAM withdrawal',
  regNumber: 'JSON payment syndicate',
  gstin: 'e-business',
  createdOn: dayjs('2023-02-08T03:05'),
};

export const sampleWithFullData: ICompany = {
  id: 97212,
  companyName: 'salmon back-end',
  contactPerson: 'SAS bus',
  address: 'Cotton Response',
  postalCode: 'Massachusetts parse Guinea-Bissau',
  email: 'Monique_Bednar62@hotmail.com',
  phoneNumber: 'Avon',
  mobileNumber: 'virtual wireless Frozen',
  websiteUrl: 'Southern Awesome',
  fax: 'Home',
  regNumber: 'Borders',
  gstin: 'methodologies',
  pan: 'Configurable maximize',
  tan: 'User-friendly capacitor SCSI',
  createdOn: dayjs('2023-02-07T11:34'),
  createdBy: 'edge',
  deleted: 'engage Pants front-end',
  lastModified: dayjs('2023-02-07T09:20'),
  lastModifiedBy: 'Iowa Cambridgeshire ADP',
};

export const sampleWithNewData: NewCompany = {
  regNumber: 'optical monitor envisioneer',
  gstin: 'violet Handcrafted Plastic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
