import dayjs from 'dayjs/esm';
import { ICompany } from 'app/entities/company/company.model';

export interface IDepartment {
  id: number;
  departmentName?: string | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewDepartment = Omit<IDepartment, 'id'> & { id: null };
