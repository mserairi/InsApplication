import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CommandeInscriptions from './commande-inscriptions';
import CommandeInscriptionsDetail from './commande-inscriptions-detail';
import CommandeInscriptionsUpdate from './commande-inscriptions-update';
import CommandeInscriptionsDeleteDialog from './commande-inscriptions-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CommandeInscriptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CommandeInscriptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CommandeInscriptionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={CommandeInscriptions} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CommandeInscriptionsDeleteDialog} />
  </>
);

export default Routes;
