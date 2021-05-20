import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Salle from './salle';
import SalleDetail from './salle-detail';
import SalleUpdate from './salle-update';
import SalleDeleteDialog from './salle-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SalleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SalleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SalleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Salle} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SalleDeleteDialog} />
  </>
);

export default Routes;
