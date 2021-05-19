import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Lasession from './lasession';
import LasessionDetail from './lasession-detail';
import LasessionUpdate from './lasession-update';
import LasessionDeleteDialog from './lasession-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LasessionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LasessionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LasessionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Lasession} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LasessionDeleteDialog} />
  </>
);

export default Routes;
