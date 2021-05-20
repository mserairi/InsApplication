import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TypeRemise from './type-remise';
import TypeRemiseDetail from './type-remise-detail';
import TypeRemiseUpdate from './type-remise-update';
import TypeRemiseDeleteDialog from './type-remise-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TypeRemiseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TypeRemiseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TypeRemiseDetail} />
      <ErrorBoundaryRoute path={match.url} component={TypeRemise} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TypeRemiseDeleteDialog} />
  </>
);

export default Routes;
