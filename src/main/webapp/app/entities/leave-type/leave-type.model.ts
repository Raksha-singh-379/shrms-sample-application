import dayjs from 'dayjs/esm';
import { Status } from 'app/entities/enumerations/status.model';

export interface ILeaveType {
  id: number;
  leaveType?: string | null;
  noOfDays?: string | null;
  status?: Status | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
}

export type NewLeaveType = Omit<ILeaveType, 'id'> & { id: null };
