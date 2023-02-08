import dayjs from 'dayjs/esm';
import { ProjectStatus } from 'app/entities/enumerations/project-status.model';

export interface IProjects {
  id: number;
  projectName?: string | null;
  description?: string | null;
  clientName?: string | null;
  cost?: number | null;
  costType?: string | null;
  priority?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  deadLine?: dayjs.Dayjs | null;
  status?: ProjectStatus | null;
  projectLead?: string | null;
  progressPercent?: number | null;
  openTasksNo?: number | null;
  completeTasksNo?: number | null;
  projectLogo?: string | null;
  projectLogoContentType?: string | null;
  projectFile?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  companyId?: number | null;
  employeeId?: number | null;
}

export type NewProjects = Omit<IProjects, 'id'> & { id: null };
