import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Remise from './remise';
import RemiseDetail from './remise-detail';
import RemiseUpdate from './remise-update';
import RemiseDeleteDialog from './remise-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RemiseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RemiseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RemiseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Remise} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RemiseDeleteDialog} />
  </>
);

export default Routes;
