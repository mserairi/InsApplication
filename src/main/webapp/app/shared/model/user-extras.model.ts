import { IUser } from 'app/shared/model/user.model';
import { TypeGenre } from 'app/shared/model/enumerations/type-genre.model';

export interface IUserExtras {
  id?: number;
  mob?: string | null;
  adresse?: string | null;
  genre?: TypeGenre | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IUserExtras> = {};
