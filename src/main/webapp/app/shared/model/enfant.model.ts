export interface IEnfant {
  id?: number;
  nom?: string;
  prenom?: string;
  age?: number | null;
}

export const defaultValue: Readonly<IEnfant> = {};
