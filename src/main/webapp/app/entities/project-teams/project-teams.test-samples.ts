import dayjs from 'dayjs/esm';

import { IProjectTeams, NewProjectTeams } from './project-teams.model';

export const sampleWithRequiredData: IProjectTeams = {
  id: 80206,
};

export const sampleWithPartialData: IProjectTeams = {
  id: 65772,
  employeId: 17496,
  lastModified: dayjs('2023-02-08T05:56'),
  createdBy: 'Tuna BEAC Pennsylvania',
  companyId: 3315,
};

export const sampleWithFullData: IProjectTeams = {
  id: 19457,
  teamMemberType: 'application',
  projectId: 69895,
  employeId: 53125,
  lastModified: dayjs('2023-02-07T09:26'),
  lastModifiedBy: 'Hat',
  createdBy: 'engineer Fresh alliance',
  createdOn: dayjs('2023-02-07T07:19'),
  isDeleted: true,
  companyId: 18502,
};

export const sampleWithNewData: NewProjectTeams = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
