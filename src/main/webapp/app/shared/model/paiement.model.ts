import dayjs from 'dayjs';
import { IFacture } from 'app/shared/model/facture.model';
import { TypePaiement } from 'app/shared/model/enumerations/type-paiement.model';

export interface IPaiement {
  id?: number;
  numero?: number | null;
  date?: string | null;
  montant?: number | null;
  type?: TypePaiement | null;
  facture?: IFacture | null;
}

export const defaultValue: Readonly<IPaiement> = {};
