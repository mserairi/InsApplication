import dayjs from 'dayjs';
import { EtatFacture } from 'app/shared/model/enumerations/etat-facture.model';

export interface IFacture {
  id?: number;
  numero?: number | null;
  date?: string | null;
  status?: EtatFacture | null;
}

export const defaultValue: Readonly<IFacture> = {};
