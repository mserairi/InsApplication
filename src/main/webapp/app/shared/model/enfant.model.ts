import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { TypeGenre } from 'app/shared/model/enumerations/type-genre.model';

export interface IEnfant {
  id?: number;
  nom?: string;
  prenom?: string;
  dateNaissance?: string | null;
  genre?: TypeGenre | null;
  nomParent2?: string | null;
  prenomParent2?: string | null;
  mobParent2?: string | null;
  emailParent2?: string | null;
  infoSante?: string | null;
  autorisationImage?: boolean | null;
  nomContact?: string | null;
  mobContact?: string | null;
  parent?: IUser;
}

export const defaultValue: Readonly<IEnfant> = {
  autorisationImage: false,
};
