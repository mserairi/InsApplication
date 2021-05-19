import { ICategory } from 'app/shared/model/category.model';

export interface ISousCategory {
  id?: number;
  libile?: string;
  description?: string;
  category?: ICategory | null;
}

export const defaultValue: Readonly<ISousCategory> = {};
