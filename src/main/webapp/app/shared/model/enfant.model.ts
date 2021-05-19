import { ICategory } from 'app/shared/model/category.model';
import { IUser } from 'app/shared/model/user.model';

export interface IEnfant {
  id?: number;
  nom?: string;
  prenom?: string;
  age?: number | null;
  suivres?: ICategory[] | null;
  parents?: IUser[] | null;
}

export const defaultValue: Readonly<IEnfant> = {};
