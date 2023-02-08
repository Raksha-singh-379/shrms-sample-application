import dayjs from 'dayjs/esm';
import { ICompany } from 'app/entities/company/company.model';

export interface IDesignation {
  id: number;
  designationName?: string | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewDesignation = Omit<IDesignation, 'id'> & { id: null };
