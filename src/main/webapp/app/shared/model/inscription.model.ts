import dayjs from 'dayjs';
import { IEnfant } from 'app/shared/model/enfant.model';
import { IFormation } from 'app/shared/model/formation.model';
import { EtatInscription } from 'app/shared/model/enumerations/etat-inscription.model';

export interface IInscription {
  id?: number;
  dateinscription?: string | null;
  status?: EtatInscription | null;
  remarques?: string | null;
  instoLAT?: boolean | null;
  inscrit?: IEnfant;
  formation?: IFormation;
}

export const defaultValue: Readonly<IInscription> = {
  instoLAT: false,
};
