import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { SalaryBasis } from 'app/entities/enumerations/salary-basis.model';

export interface IEmpSalaryInfo {
  id: number;
  salaryBasis?: SalaryBasis | null;
  amount?: number | null;
  paymentType?: string | null;
  isPfContribution?: boolean | null;
  pfNumber?: string | null;
  pfRate?: number | null;
  additionalPfRate?: string | null;
  totalPfRate?: number | null;
  isEsiContribution?: boolean | null;
  esiNumber?: string | null;
  esiRate?: number | null;
  additionalEsiRate?: string | null;
  totalEsiRate?: number | null;
  employeId?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewEmpSalaryInfo = Omit<IEmpSalaryInfo, 'id'> & { id: null };
