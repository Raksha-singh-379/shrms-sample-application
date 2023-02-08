import dayjs from 'dayjs/esm';

export interface ISalarySettings {
  id: number;
  da?: number | null;
  hra?: number | null;
  employeeshare?: number | null;
  companyshare?: number | null;
  salaryFrom?: dayjs.Dayjs | null;
  salaryTo?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
}

export type NewSalarySettings = Omit<ISalarySettings, 'id'> & { id: null };
