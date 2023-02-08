import dayjs from 'dayjs/esm';
import { Status } from 'app/entities/enumerations/status.model';

export interface ILeaveTranscation {
  id: number;
  leaveType?: string | null;
  empId?: string | null;
  monthDate?: dayjs.Dayjs | null;
  status?: Status | null;
  year?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
}

export type NewLeaveTranscation = Omit<ILeaveTranscation, 'id'> & { id: null };
