import { ITypeRemise } from 'app/shared/model/type-remise.model';

export interface IRemise {
  id?: number;
  numero?: number | null;
  montant?: number | null;
  descreption?: string | null;
  typeremise?: ITypeRemise | null;
}

export const defaultValue: Readonly<IRemise> = {};
