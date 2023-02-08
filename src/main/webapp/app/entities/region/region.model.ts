import dayjs from 'dayjs/esm';
import { ICompany } from 'app/entities/company/company.model';

export interface IRegion {
  id: number;
  regionName?: string | null;
  description?: string | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  deleted?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewRegion = Omit<IRegion, 'id'> & { id: null };
