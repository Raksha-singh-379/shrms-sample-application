import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface IWorkExperience {
  id: number;
  jobTitle?: string | null;
  companyName?: string | null;
  address?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  employeeId?: number | null;
  yearOfExp?: number | null;
  jobDesc?: string | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewWorkExperience = Omit<IWorkExperience, 'id'> & { id: null };
