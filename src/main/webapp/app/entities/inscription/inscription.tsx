import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './inscription.reducer';
import { IInscription } from 'app/shared/model/inscription.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInscriptionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Inscription = (props: IInscriptionProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { inscriptionList, match, loading } = props;
  return (
    <div>
      <h2 id="inscription-heading" data-cy="InscriptionHeading">
        <Translate contentKey="insApplicationApp.inscription.home.title">Inscriptions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.inscription.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.inscription.home.createLabel">Create new Inscription</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inscriptionList && inscriptionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.inscription.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.inscription.dateinscription">Dateinscription</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.inscription.lasession">Lasession</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.inscription.concerne">Concerne</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.inscription.inscrits">Inscrits</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inscriptionList.map((inscription, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${inscription.id}`} color="link" size="sm">
                      {inscription.id}
                    </Button>
                  </td>
                  <td>
                    {inscription.dateinscription ? (
                      <TextFormat type="date" value={inscription.dateinscription} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{inscription.lasession}</td>
                  <td>
                    {inscription.concerne ? <Link to={`category/${inscription.concerne.id}`}>{inscription.concerne.libile}</Link> : ''}
                  </td>
                  <td>
                    {inscription.inscrits
                      ? inscription.inscrits.map((val, j) => (
                          <span key={j}>
                            <Link to={`enfant/${val.id}`}>{val.id}</Link>
                            {j === inscription.inscrits.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${inscription.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${inscription.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${inscription.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.inscription.home.notFound">No Inscriptions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ inscription }: IRootState) => ({
  inscriptionList: inscription.entities,
  loading: inscription.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Inscription);
