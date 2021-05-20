import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './commande-inscriptions.reducer';
import { ICommandeInscriptions } from 'app/shared/model/commande-inscriptions.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICommandeInscriptionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CommandeInscriptions = (props: ICommandeInscriptionsProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { commandeInscriptionsList, match, loading } = props;
  return (
    <div>
      <h2 id="commande-inscriptions-heading" data-cy="CommandeInscriptionsHeading">
        <Translate contentKey="insApplicationApp.commandeInscriptions.home.title">Commande Inscriptions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.commandeInscriptions.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.commandeInscriptions.home.createLabel">Create new Commande Inscriptions</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {commandeInscriptionsList && commandeInscriptionsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.commandeInscriptions.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.commandeInscriptions.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.commandeInscriptions.totalAvantRemise">Total Avant Remise</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.commandeInscriptions.totalRemise">Total Remise</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.commandeInscriptions.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.commandeInscriptions.facture">Facture</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.commandeInscriptions.inscription">Inscription</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.commandeInscriptions.remise">Remise</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {commandeInscriptionsList.map((commandeInscriptions, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${commandeInscriptions.id}`} color="link" size="sm">
                      {commandeInscriptions.id}
                    </Button>
                  </td>
                  <td>{commandeInscriptions.numero}</td>
                  <td>{commandeInscriptions.totalAvantRemise}</td>
                  <td>{commandeInscriptions.totalRemise}</td>
                  <td>
                    <Translate contentKey={`insApplicationApp.Etatcommande.${commandeInscriptions.status}`} />
                  </td>
                  <td>
                    {commandeInscriptions.facture ? (
                      <Link to={`facture/${commandeInscriptions.facture.id}`}>{commandeInscriptions.facture.numero}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {commandeInscriptions.inscription ? (
                      <Link to={`inscription/${commandeInscriptions.inscription.id}`}>{commandeInscriptions.inscription.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {commandeInscriptions.remise ? (
                      <Link to={`remise/${commandeInscriptions.remise.id}`}>{commandeInscriptions.remise.numero}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${commandeInscriptions.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${commandeInscriptions.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${commandeInscriptions.id}/delete`}
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
              <Translate contentKey="insApplicationApp.commandeInscriptions.home.notFound">No Commande Inscriptions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ commandeInscriptions }: IRootState) => ({
  commandeInscriptionsList: commandeInscriptions.entities,
  loading: commandeInscriptions.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommandeInscriptions);
