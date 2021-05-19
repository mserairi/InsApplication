import { IEnfant } from 'app/shared/model/enfant.model';

export interface ICategory {
  id?: number;
  libile?: string;
  description?: string;
  tarif?: number | null;
  sousCat?: ICategory | null;
  inscrits?: IEnfant[] | null;
}

export const defaultValue: Readonly<ICategory> = {};
