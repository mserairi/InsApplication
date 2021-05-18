import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './enfant.reducer';
import { IEnfant } from 'app/shared/model/enfant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnfantProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Enfant = (props: IEnfantProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { enfantList, match, loading } = props;
  return (
    <div>
      <h2 id="enfant-heading" data-cy="EnfantHeading">
        <Translate contentKey="insApplicationApp.enfant.home.title">Enfants</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.enfant.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.enfant.home.createLabel">Create new Enfant</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {enfantList && enfantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.enfant.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.enfant.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.enfant.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.enfant.age">Age</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.enfant.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {enfantList.map((enfant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${enfant.id}`} color="link" size="sm">
                      {enfant.id}
                    </Button>
                  </td>
                  <td>{enfant.nom}</td>
                  <td>{enfant.prenom}</td>
                  <td>{enfant.age}</td>
                  <td>{enfant.user ? enfant.user.id : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${enfant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${enfant.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${enfant.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.enfant.home.notFound">No Enfants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ enfant }: IRootState) => ({
  enfantList: enfant.entities,
  loading: enfant.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Enfant);
