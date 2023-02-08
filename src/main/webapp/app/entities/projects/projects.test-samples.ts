import dayjs from 'dayjs/esm';

import { ProjectStatus } from 'app/entities/enumerations/project-status.model';

import { IProjects, NewProjects } from './projects.model';

export const sampleWithRequiredData: IProjects = {
  id: 18673,
};

export const sampleWithPartialData: IProjects = {
  id: 17807,
  cost: 86312,
  endDate: dayjs('2023-02-08T01:11'),
  deadLine: dayjs('2023-02-07T18:24'),
  completeTasksNo: 93907,
  projectFile: 'Tools',
  lastModified: dayjs('2023-02-07T14:29'),
  createdBy: 'IB Rustic',
  createdOn: dayjs('2023-02-07T13:54'),
  employeeId: 41938,
};

export const sampleWithFullData: IProjects = {
  id: 3132,
  projectName: 'users',
  description: 'solid',
  clientName: 'e-business Dakota silver',
  cost: 27756,
  costType: 'help-desk Concrete',
  priority: 'Chile',
  startDate: dayjs('2023-02-07T19:13'),
  endDate: dayjs('2023-02-07T09:05'),
  deadLine: dayjs('2023-02-07T23:54'),
  status: ProjectStatus['INPROGRESS'],
  projectLead: 'Awesome unleash',
  progressPercent: 35826,
  openTasksNo: 3029,
  completeTasksNo: 36896,
  projectLogo: '../fake-data/blob/hipster.png',
  projectLogoContentType: 'unknown',
  projectFile: 'conglomeration Louisiana Outdoors',
  lastModified: dayjs('2023-02-08T00:39'),
  lastModifiedBy: 'quantifying withdrawal',
  createdBy: 'asynchronous PCI',
  createdOn: dayjs('2023-02-07T12:43'),
  deleted: false,
  companyId: 94799,
  employeeId: 75525,
};

export const sampleWithNewData: NewProjects = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
