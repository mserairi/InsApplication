import dayjs from 'dayjs';
import { ILasession } from 'app/shared/model/lasession.model';
import { IEnfant } from 'app/shared/model/enfant.model';

export interface IInscription {
  id?: number;
  dateinscription?: string | null;
  status?: boolean | null;
  concerne?: ILasession | null;
  inscrits?: IEnfant[] | null;
}

export const defaultValue: Readonly<IInscription> = {
  status: false,
};
