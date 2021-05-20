export interface ITypeRemise {
  id?: number;
  numero?: number | null;
  libille?: string | null;
  condition?: string | null;
  mantantLibre?: boolean | null;
  montantUnitaire?: number | null;
}

export const defaultValue: Readonly<ITypeRemise> = {
  mantantLibre: false,
};
