import dayjs from 'dayjs/esm';

import { IHolidays, NewHolidays } from './holidays.model';

export const sampleWithRequiredData: IHolidays = {
  id: 61471,
  holidayName: 'Gorgeous',
};

export const sampleWithPartialData: IHolidays = {
  id: 49048,
  holidayName: 'navigating Central facilitate',
  createdBy: 'empower disintermediate',
  createdOn: dayjs('2023-02-08T02:09'),
  deleted: true,
};

export const sampleWithFullData: IHolidays = {
  id: 68469,
  holidayName: 'benchmark',
  holidayDate: dayjs('2023-02-07T23:41'),
  day: 'schemas',
  year: dayjs('2023-02-07T16:58'),
  lastModified: dayjs('2023-02-07T23:58'),
  lastModifiedBy: 'generating Outdoors',
  createdBy: 'virtual Games Wyoming',
  createdOn: dayjs('2023-02-07T18:30'),
  deleted: true,
};

export const sampleWithNewData: NewHolidays = {
  holidayName: 'transmitting Fish Practical',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
