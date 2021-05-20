import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Cours from './cours';
import CoursDetail from './cours-detail';
import CoursUpdate from './cours-update';
import CoursDeleteDialog from './cours-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CoursUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CoursUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CoursDetail} />
      <ErrorBoundaryRoute path={match.url} component={Cours} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CoursDeleteDialog} />
  </>
);

export default Routes;
