import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';

export interface IEmployeeDetails {
  id: number;
  passportNo?: string | null;
  passportExpDate?: dayjs.Dayjs | null;
  telephoneNo?: string | null;
  nationality?: string | null;
  maritalStatus?: MaritalStatus | null;
  religion?: string | null;
  isSpousEmployed?: boolean | null;
  noOfChildren?: number | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  deleted?: boolean | null;
  employeeId?: number | null;
  age?: number | null;
  fatherName?: string | null;
  motherName?: string | null;
  aadharNo?: string | null;
  bloodGroup?: BloodGroup | null;
  dob?: dayjs.Dayjs | null;
  expertise?: string | null;
  hobbies?: string | null;
  areaInterest?: string | null;
  languageKnown?: string | null;
  description?: string | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewEmployeeDetails = Omit<IEmployeeDetails, 'id'> & { id: null };
