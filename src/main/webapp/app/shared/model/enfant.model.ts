import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IInscription } from 'app/shared/model/inscription.model';
import { IGroupe } from 'app/shared/model/groupe.model';

export interface IEnfant {
  id?: number;
  nom?: string;
  prenom?: string;
  dateNaissance?: string | null;
  autorisationImage?: boolean | null;
  infoSante?: string | null;
  parents?: IUser[];
  suivres?: IInscription[] | null;
  groupes?: IGroupe[] | null;
}

export const defaultValue: Readonly<IEnfant> = {
  autorisationImage: false,
};
