import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Enfant from './enfant';
import Category from './category';
import Inscription from './inscription';
import SousCategory from './sous-category';
import Lasession from './lasession';
import CommandeInscriptions from './commande-inscriptions';
import TypeRemise from './type-remise';
import Remise from './remise';
import Facture from './facture';
import Paiement from './paiement';
import Cours from './cours';
import Groupe from './groupe';
import Creneau from './creneau';
import Salle from './salle';
import Formation from './formation';
import UserExtras from './user-extras';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}enfant`} component={Enfant} />
      <ErrorBoundaryRoute path={`${match.url}category`} component={Category} />
      <ErrorBoundaryRoute path={`${match.url}inscription`} component={Inscription} />
      <ErrorBoundaryRoute path={`${match.url}sous-category`} component={SousCategory} />
      <ErrorBoundaryRoute path={`${match.url}lasession`} component={Lasession} />
      <ErrorBoundaryRoute path={`${match.url}commande-inscriptions`} component={CommandeInscriptions} />
      <ErrorBoundaryRoute path={`${match.url}type-remise`} component={TypeRemise} />
      <ErrorBoundaryRoute path={`${match.url}remise`} component={Remise} />
      <ErrorBoundaryRoute path={`${match.url}facture`} component={Facture} />
      <ErrorBoundaryRoute path={`${match.url}paiement`} component={Paiement} />
      <ErrorBoundaryRoute path={`${match.url}cours`} component={Cours} />
      <ErrorBoundaryRoute path={`${match.url}groupe`} component={Groupe} />
      <ErrorBoundaryRoute path={`${match.url}creneau`} component={Creneau} />
      <ErrorBoundaryRoute path={`${match.url}salle`} component={Salle} />
      <ErrorBoundaryRoute path={`${match.url}formation`} component={Formation} />
      <ErrorBoundaryRoute path={`${match.url}user-extras`} component={UserExtras} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
