import dayjs from 'dayjs';
import { ICategory } from 'app/shared/model/category.model';
import { IEnfant } from 'app/shared/model/enfant.model';

export interface IInscription {
  id?: number;
  dateinscription?: string | null;
  lasession?: string;
  concerne?: ICategory | null;
  inscrits?: IEnfant[] | null;
}

export const defaultValue: Readonly<IInscription> = {};
