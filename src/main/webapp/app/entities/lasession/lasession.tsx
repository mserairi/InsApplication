import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './lasession.reducer';
import { ILasession } from 'app/shared/model/lasession.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILasessionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Lasession = (props: ILasessionProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { lasessionList, match, loading } = props;
  return (
    <div>
      <h2 id="lasession-heading" data-cy="LasessionHeading">
        <Translate contentKey="insApplicationApp.lasession.home.title">Lasessions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.lasession.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.lasession.home.createLabel">Create new Lasession</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lasessionList && lasessionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.lasession.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.lasession.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.lasession.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.lasession.tarif">Tarif</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.lasession.debut">Debut</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.lasession.fin">Fin</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.lasession.category">Category</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lasessionList.map((lasession, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${lasession.id}`} color="link" size="sm">
                      {lasession.id}
                    </Button>
                  </td>
                  <td>{lasession.code}</td>
                  <td>{lasession.description}</td>
                  <td>{lasession.tarif}</td>
                  <td>{lasession.debut ? <TextFormat type="date" value={lasession.debut} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{lasession.fin ? <TextFormat type="date" value={lasession.fin} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{lasession.category ? <Link to={`category/${lasession.category.id}`}>{lasession.category.libile}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${lasession.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${lasession.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${lasession.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.lasession.home.notFound">No Lasessions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ lasession }: IRootState) => ({
  lasessionList: lasession.entities,
  loading: lasession.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Lasession);
