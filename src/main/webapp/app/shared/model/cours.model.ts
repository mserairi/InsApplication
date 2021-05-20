import { ISousCategory } from 'app/shared/model/sous-category.model';
import { IGroupe } from 'app/shared/model/groupe.model';
import { PERIODICITE } from 'app/shared/model/enumerations/periodicite.model';

export interface ICours {
  id?: number;
  numero?: string | null;
  libille?: string | null;
  description?: string | null;
  seuil?: number | null;
  duree?: number | null;
  periode?: PERIODICITE | null;
  frequence?: number | null;
  agiminrec?: number | null;
  agemaxrec?: number | null;
  souscategory?: ISousCategory | null;
  groupes?: IGroupe[] | null;
}

export const defaultValue: Readonly<ICours> = {};
