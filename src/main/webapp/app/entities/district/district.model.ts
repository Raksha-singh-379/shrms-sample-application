import dayjs from 'dayjs/esm';

export interface IDistrict {
  id: number;
  districtName?: string | null;
  lgdCode?: number | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  deleted?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
}

export type NewDistrict = Omit<IDistrict, 'id'> & { id: null };
