import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import enfant, {
  EnfantState
} from 'app/entities/enfant/enfant.reducer';
// prettier-ignore
import category, {
  CategoryState
} from 'app/entities/category/category.reducer';
// prettier-ignore
import inscription, {
  InscriptionState
} from 'app/entities/inscription/inscription.reducer';
// prettier-ignore
import sousCategory, {
  SousCategoryState
} from 'app/entities/sous-category/sous-category.reducer';
// prettier-ignore
import lasession, {
  LasessionState
} from 'app/entities/lasession/lasession.reducer';
// prettier-ignore
import commandeInscriptions, {
  CommandeInscriptionsState
} from 'app/entities/commande-inscriptions/commande-inscriptions.reducer';
// prettier-ignore
import typeRemise, {
  TypeRemiseState
} from 'app/entities/type-remise/type-remise.reducer';
// prettier-ignore
import remise, {
  RemiseState
} from 'app/entities/remise/remise.reducer';
// prettier-ignore
import facture, {
  FactureState
} from 'app/entities/facture/facture.reducer';
// prettier-ignore
import paiement, {
  PaiementState
} from 'app/entities/paiement/paiement.reducer';
// prettier-ignore
import cours, {
  CoursState
} from 'app/entities/cours/cours.reducer';
// prettier-ignore
import groupe, {
  GroupeState
} from 'app/entities/groupe/groupe.reducer';
// prettier-ignore
import creneau, {
  CreneauState
} from 'app/entities/creneau/creneau.reducer';
// prettier-ignore
import salle, {
  SalleState
} from 'app/entities/salle/salle.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly enfant: EnfantState;
  readonly category: CategoryState;
  readonly inscription: InscriptionState;
  readonly sousCategory: SousCategoryState;
  readonly lasession: LasessionState;
  readonly commandeInscriptions: CommandeInscriptionsState;
  readonly typeRemise: TypeRemiseState;
  readonly remise: RemiseState;
  readonly facture: FactureState;
  readonly paiement: PaiementState;
  readonly cours: CoursState;
  readonly groupe: GroupeState;
  readonly creneau: CreneauState;
  readonly salle: SalleState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  enfant,
  category,
  inscription,
  sousCategory,
  lasession,
  commandeInscriptions,
  typeRemise,
  remise,
  facture,
  paiement,
  cours,
  groupe,
  creneau,
  salle,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
