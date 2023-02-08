import dayjs from 'dayjs/esm';

export interface ICity {
  id: number;
  cityName?: string | null;
  lgdCode?: number | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  deleted?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
}

export type NewCity = Omit<ICity, 'id'> & { id: null };
