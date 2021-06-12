import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './formation.reducer';
import { IFormation } from 'app/shared/model/formation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFormationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Formation = (props: IFormationProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { formationList, match, loading } = props;
  return (
    <div>
      <h2 id="formation-heading" data-cy="FormationHeading">
        <Translate contentKey="insApplicationApp.formation.home.title">Formations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.formation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.formation.home.createLabel">Create new Formation</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {formationList && formationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.formation.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.formation.libille">Libille</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.formation.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.formation.ouvertureInscription">Ouverture Inscription</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.formation.fermetureInscription">Fermeture Inscription</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.formation.seuilInscrits">Seuil Inscrits</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.formation.tarif">Tarif</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.formation.category">Category</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {formationList.map((formation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${formation.id}`} color="link" size="sm">
                      {formation.id}
                    </Button>
                  </td>
                  <td>{formation.libille}</td>
                  <td>{formation.description}</td>
                  <td>
                    {formation.ouvertureInscription ? (
                      <TextFormat type="date" value={formation.ouvertureInscription} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {formation.fermetureInscription ? (
                      <TextFormat type="date" value={formation.fermetureInscription} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{formation.seuilInscrits}</td>
                  <td>{formation.tarif}</td>
                  <td>{formation.category ? <Link to={`category/${formation.category.id}`}>{formation.category.libille}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${formation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${formation.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${formation.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.formation.home.notFound">No Formations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ formation }: IRootState) => ({
  formationList: formation.entities,
  loading: formation.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Formation);
