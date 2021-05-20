import { ICategory } from 'app/shared/model/category.model';
import { ICours } from 'app/shared/model/cours.model';

export interface ISousCategory {
  id?: number;
  libille?: string;
  description?: string;
  category?: ICategory | null;
  cours?: ICours[] | null;
}

export const defaultValue: Readonly<ISousCategory> = {};
