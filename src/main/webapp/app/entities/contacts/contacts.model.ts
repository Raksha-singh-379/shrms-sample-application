import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { ContactType } from 'app/entities/enumerations/contact-type.model';
import { Relationship } from 'app/entities/enumerations/relationship.model';

export interface IContacts {
  id: number;
  name?: string | null;
  type?: ContactType | null;
  relation?: Relationship | null;
  phoneNo1?: string | null;
  phoneNo2?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  employeeId?: number | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewContacts = Omit<IContacts, 'id'> & { id: null };
