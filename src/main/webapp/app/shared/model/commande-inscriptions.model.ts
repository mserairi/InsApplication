import { IFacture } from 'app/shared/model/facture.model';
import { IInscription } from 'app/shared/model/inscription.model';
import { IRemise } from 'app/shared/model/remise.model';
import { Etatcommande } from 'app/shared/model/enumerations/etatcommande.model';

export interface ICommandeInscriptions {
  id?: number;
  numero?: number | null;
  totalAvantRemise?: number | null;
  totalRemise?: number | null;
  status?: Etatcommande | null;
  facture?: IFacture | null;
  inscription?: IInscription | null;
  remise?: IRemise | null;
}

export const defaultValue: Readonly<ICommandeInscriptions> = {};
