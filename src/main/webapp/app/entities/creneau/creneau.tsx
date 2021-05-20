import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './creneau.reducer';
import { ICreneau } from 'app/shared/model/creneau.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICreneauProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Creneau = (props: ICreneauProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { creneauList, match, loading } = props;
  return (
    <div>
      <h2 id="creneau-heading" data-cy="CreneauHeading">
        <Translate contentKey="insApplicationApp.creneau.home.title">Creneaus</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.creneau.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.creneau.home.createLabel">Create new Creneau</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {creneauList && creneauList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.creneau.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.creneau.typeCreneau">Type Creneau</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.creneau.jour">Jour</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.creneau.deb">Deb</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.creneau.fin">Fin</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.creneau.salle">Salle</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.creneau.groupe">Groupe</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {creneauList.map((creneau, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${creneau.id}`} color="link" size="sm">
                      {creneau.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`insApplicationApp.TYPECRENEAU.${creneau.typeCreneau}`} />
                  </td>
                  <td>
                    <Translate contentKey={`insApplicationApp.JourSemaine.${creneau.jour}`} />
                  </td>
                  <td>{creneau.deb ? <TextFormat type="date" value={creneau.deb} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{creneau.fin ? <TextFormat type="date" value={creneau.fin} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{creneau.salle ? <Link to={`salle/${creneau.salle.id}`}>{creneau.salle.numero}</Link> : ''}</td>
                  <td>{creneau.groupe ? <Link to={`groupe/${creneau.groupe.id}`}>{creneau.groupe.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${creneau.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${creneau.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${creneau.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.creneau.home.notFound">No Creneaus found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ creneau }: IRootState) => ({
  creneauList: creneau.entities,
  loading: creneau.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Creneau);
