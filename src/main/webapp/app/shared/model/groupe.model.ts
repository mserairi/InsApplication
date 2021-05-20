import { ICreneau } from 'app/shared/model/creneau.model';
import { ICours } from 'app/shared/model/cours.model';
import { IEnfant } from 'app/shared/model/enfant.model';

export interface IGroupe {
  id?: number;
  numero?: string | null;
  libille?: string | null;
  lasession?: string | null;
  nbrApprenant?: number | null;
  creneaus?: ICreneau[] | null;
  cours?: ICours | null;
  enfants?: IEnfant[] | null;
}

export const defaultValue: Readonly<IGroupe> = {};
