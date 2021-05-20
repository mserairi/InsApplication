import dayjs from 'dayjs';
import { ISalle } from 'app/shared/model/salle.model';
import { IGroupe } from 'app/shared/model/groupe.model';
import { TYPECRENEAU } from 'app/shared/model/enumerations/typecreneau.model';
import { JourSemaine } from 'app/shared/model/enumerations/jour-semaine.model';

export interface ICreneau {
  id?: number;
  typeCreneau?: TYPECRENEAU;
  jour?: JourSemaine | null;
  deb?: string | null;
  fin?: string | null;
  salle?: ISalle | null;
  groupe?: IGroupe | null;
}

export const defaultValue: Readonly<ICreneau> = {};
