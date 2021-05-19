export interface ICategory {
  id?: number;
  libile?: string;
  description?: string;
  tarif?: number | null;
  sousCat?: ICategory | null;
}

export const defaultValue: Readonly<ICategory> = {};
