import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { Degree } from 'app/entities/enumerations/degree.model';

export interface IEducation {
  id: number;
  institution?: string | null;
  subject?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  degree?: Degree | null;
  grade?: string | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  employeeId?: number | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewEducation = Omit<IEducation, 'id'> & { id: null };
