import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Formation from './formation';
import FormationDetail from './formation-detail';
import FormationUpdate from './formation-update';
import FormationDeleteDialog from './formation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FormationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FormationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FormationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Formation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FormationDeleteDialog} />
  </>
);

export default Routes;
