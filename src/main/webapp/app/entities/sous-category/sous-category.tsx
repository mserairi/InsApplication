import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './sous-category.reducer';
import { ISousCategory } from 'app/shared/model/sous-category.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISousCategoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SousCategory = (props: ISousCategoryProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { sousCategoryList, match, loading } = props;
  return (
    <div>
      <h2 id="sous-category-heading" data-cy="SousCategoryHeading">
        <Translate contentKey="insApplicationApp.sousCategory.home.title">Sous Categories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.sousCategory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.sousCategory.home.createLabel">Create new Sous Category</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sousCategoryList && sousCategoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.sousCategory.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.sousCategory.libille">Libille</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.sousCategory.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.sousCategory.category">Category</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sousCategoryList.map((sousCategory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${sousCategory.id}`} color="link" size="sm">
                      {sousCategory.id}
                    </Button>
                  </td>
                  <td>{sousCategory.libille}</td>
                  <td>{sousCategory.description}</td>
                  <td>
                    {sousCategory.category ? <Link to={`category/${sousCategory.category.id}`}>{sousCategory.category.libile}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${sousCategory.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${sousCategory.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${sousCategory.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="insApplicationApp.sousCategory.home.notFound">No Sous Categories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ sousCategory }: IRootState) => ({
  sousCategoryList: sousCategory.entities,
  loading: sousCategory.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SousCategory);
