import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SousCategory from './sous-category';
import SousCategoryDetail from './sous-category-detail';
import SousCategoryUpdate from './sous-category-update';
import SousCategoryDeleteDialog from './sous-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SousCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SousCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SousCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={SousCategory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SousCategoryDeleteDialog} />
  </>
);

export default Routes;
