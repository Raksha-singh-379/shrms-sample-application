import dayjs from 'dayjs/esm';

export interface IProjectTeams {
  id: number;
  teamMemberType?: string | null;
  projectId?: number | null;
  employeId?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  companyId?: number | null;
}

export type NewProjectTeams = Omit<IProjectTeams, 'id'> & { id: null };
