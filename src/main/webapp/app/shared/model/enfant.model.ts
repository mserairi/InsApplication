import { IUser } from 'app/shared/model/user.model';
import { IInscription } from 'app/shared/model/inscription.model';

export interface IEnfant {
  id?: number;
  nom?: string;
  prenom?: string;
  age?: number | null;
  parents?: IUser[] | null;
  suivres?: IInscription[] | null;
}

export const defaultValue: Readonly<IEnfant> = {};
