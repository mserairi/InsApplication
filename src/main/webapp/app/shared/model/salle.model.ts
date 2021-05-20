export interface ISalle {
  id?: number;
  code?: string;
  batiment?: string;
  etage?: number;
}

export const defaultValue: Readonly<ISalle> = {};
