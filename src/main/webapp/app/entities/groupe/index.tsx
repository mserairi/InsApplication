import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Groupe from './groupe';
import GroupeDetail from './groupe-detail';
import GroupeUpdate from './groupe-update';
import GroupeDeleteDialog from './groupe-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GroupeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GroupeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GroupeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Groupe} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GroupeDeleteDialog} />
  </>
);

export default Routes;
