import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { ILeavePolicy } from 'app/entities/leave-policy/leave-policy.model';
import { Status } from 'app/entities/enumerations/status.model';
import { LeaveStatus } from 'app/entities/enumerations/leave-status.model';

export interface ILeaveApplication {
  id: number;
  leaveType?: string | null;
  balanceLeave?: number | null;
  noOfDays?: number | null;
  reason?: string | null;
  year?: number | null;
  formDate?: dayjs.Dayjs | null;
  toDate?: dayjs.Dayjs | null;
  status?: Status | null;
  leaveStatus?: LeaveStatus | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  employee?: Pick<IEmployee, 'id'> | null;
  leavePolicy?: Pick<ILeavePolicy, 'id'> | null;
}

export type NewLeaveApplication = Omit<ILeaveApplication, 'id'> & { id: null };
