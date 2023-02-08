import dayjs from 'dayjs/esm';
import { EmployeeType } from 'app/entities/enumerations/employee-type.model';
import { GenderLeave } from 'app/entities/enumerations/gender-leave.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface ILeavePolicy {
  id: number;
  leaveType?: string | null;
  isCarryForword?: boolean | null;
  employeeType?: EmployeeType | null;
  genderLeave?: GenderLeave | null;
  status?: Status | null;
  totalLeave?: string | null;
  maxLeave?: string | null;
  hasproRataLeave?: boolean | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
}

export type NewLeavePolicy = Omit<ILeavePolicy, 'id'> & { id: null };
