import dayjs from 'dayjs/esm';
import { Gender } from 'app/entities/enumerations/gender.model';

export interface IEmployee {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  username?: string | null;
  password?: string | null;
  email?: string | null;
  phone?: string | null;
  mobile?: string | null;
  department?: string | null;
  designation?: string | null;
  gender?: Gender | null;
  employeeId?: string | null;
  joindate?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  deleted?: boolean | null;
  reportedTo?: number | null;
  companyId?: number | null;
  branchId?: number | null;
  regionId?: number | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
