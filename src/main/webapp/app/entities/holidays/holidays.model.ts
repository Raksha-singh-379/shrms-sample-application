import dayjs from 'dayjs/esm';
import { ICompany } from 'app/entities/company/company.model';

export interface IHolidays {
  id: number;
  holidayName?: string | null;
  holidayDate?: dayjs.Dayjs | null;
  day?: string | null;
  year?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewHolidays = Omit<IHolidays, 'id'> & { id: null };
