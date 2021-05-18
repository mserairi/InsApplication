import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Enfant from './enfant';
import EnfantDetail from './enfant-detail';
import EnfantUpdate from './enfant-update';
import EnfantDeleteDialog from './enfant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EnfantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EnfantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EnfantDetail} />
      <ErrorBoundaryRoute path={match.url} component={Enfant} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EnfantDeleteDialog} />
  </>
);

export default Routes;
