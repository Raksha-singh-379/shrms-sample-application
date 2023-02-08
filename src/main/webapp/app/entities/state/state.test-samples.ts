import dayjs from 'dayjs/esm';

import { IState, NewState } from './state.model';

export const sampleWithRequiredData: IState = {
  id: 97627,
  stateName: 'Games microchip',
};

export const sampleWithPartialData: IState = {
  id: 54311,
  stateName: 'full-range',
  lgdCode: 2959,
  deleted: 'Terrace generate online',
  lastModifiedBy: 'sticky e-services web-readiness',
};

export const sampleWithFullData: IState = {
  id: 93946,
  stateName: 'channels Meadows Distributed',
  lgdCode: 47963,
  createdOn: dayjs('2023-02-07T19:52'),
  createdBy: 'Small redefine',
  deleted: 'Buckinghamshire drive',
  lastModified: dayjs('2023-02-07T12:17'),
  lastModifiedBy: 'maroon Wooden Small',
};

export const sampleWithNewData: NewState = {
  stateName: 'local',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
