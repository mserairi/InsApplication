import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './paiement.reducer';
import { IPaiement } from 'app/shared/model/paiement.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaiementProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Paiement = (props: IPaiementProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { paiementList, match, loading } = props;
  return (
    <div>
      <h2 id="paiement-heading" data-cy="PaiementHeading">
        <Translate contentKey="insApplicationApp.paiement.home.title">Paiements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.paiement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.paiement.home.createLabel">Create new Paiement</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {paiementList && paiementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.paiement.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.paiement.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.paiement.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.paiement.montant">Montant</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.paiement.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.paiement.facture">Facture</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paiementList.map((paiement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${paiement.id}`} color="link" size="sm">
                      {paiement.id}
                    </Button>
                  </td>
                  <td>{paiement.numero}</td>
                  <td>{paiement.date ? <TextFormat type="date" value={paiement.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{paiement.montant}</td>
                  <td>
                    <Translate contentKey={`insApplicationApp.TypePaiement.${paiement.type}`} />
                  </td>
                  <td>{paiement.facture ? <Link to={`facture/${paiement.facture.id}`}>{paiement.facture.numero}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${paiement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${paiement.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${paiement.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.paiement.home.notFound">No Paiements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ paiement }: IRootState) => ({
  paiementList: paiement.entities,
  loading: paiement.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Paiement);
