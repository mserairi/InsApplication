import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Creneau from './creneau';
import CreneauDetail from './creneau-detail';
import CreneauUpdate from './creneau-update';
import CreneauDeleteDialog from './creneau-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CreneauUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CreneauUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CreneauDetail} />
      <ErrorBoundaryRoute path={match.url} component={Creneau} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CreneauDeleteDialog} />
  </>
);

export default Routes;
