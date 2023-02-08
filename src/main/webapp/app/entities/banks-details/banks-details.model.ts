import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface IBanksDetails {
  id: number;
  accountNo?: number | null;
  bankName?: string | null;
  ifscCode?: string | null;
  pan?: string | null;
  branch?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  employeeId?: number | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewBanksDetails = Omit<IBanksDetails, 'id'> & { id: null };
