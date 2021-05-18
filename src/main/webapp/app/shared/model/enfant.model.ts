import { IUser } from 'app/shared/model/user.model';

export interface IEnfant {
  id?: number;
  nom?: string;
  prenom?: string;
  age?: number | null;
  parent?: IUser | null;
}

export const defaultValue: Readonly<IEnfant> = {};
