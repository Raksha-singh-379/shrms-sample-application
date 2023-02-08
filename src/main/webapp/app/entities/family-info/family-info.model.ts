import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { Relationship } from 'app/entities/enumerations/relationship.model';

export interface IFamilyInfo {
  id: number;
  name?: string | null;
  dob?: dayjs.Dayjs | null;
  relation?: Relationship | null;
  phoneNo?: string | null;
  address?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  employeeId?: number | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewFamilyInfo = Omit<IFamilyInfo, 'id'> & { id: null };
