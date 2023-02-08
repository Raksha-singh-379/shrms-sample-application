import dayjs from 'dayjs/esm';

import { IRegion, NewRegion } from './region.model';

export const sampleWithRequiredData: IRegion = {
  id: 27363,
  regionName: 'back-end Practical',
};

export const sampleWithPartialData: IRegion = {
  id: 12726,
  regionName: 'Strategist Berkshire quantifying',
  description: 'salmon Product',
  deleted: 'Soft',
  lastModified: dayjs('2023-02-08T04:26'),
  lastModifiedBy: 'Administrator Grocery',
};

export const sampleWithFullData: IRegion = {
  id: 23487,
  regionName: 'Horizontal Shoes',
  description: 'turquoise mesh Executive',
  createdOn: dayjs('2023-02-08T04:15'),
  createdBy: 'Cross-group',
  deleted: 'Prairie Mouse ROI',
  lastModified: dayjs('2023-02-07T12:51'),
  lastModifiedBy: 'Wooden blue',
};

export const sampleWithNewData: NewRegion = {
  regionName: 'green Cheese calculate',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
