import dayjs from 'dayjs';
import { ICategory } from 'app/shared/model/category.model';

export interface ILasession {
  id?: number;
  code?: string;
  description?: string;
  tarif?: number | null;
  debut?: string | null;
  fin?: string | null;
  category?: ICategory | null;
}

export const defaultValue: Readonly<ILasession> = {};
