import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './remise.reducer';
import { IRemise } from 'app/shared/model/remise.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRemiseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Remise = (props: IRemiseProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { remiseList, match, loading } = props;
  return (
    <div>
      <h2 id="remise-heading" data-cy="RemiseHeading">
        <Translate contentKey="insApplicationApp.remise.home.title">Remises</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.remise.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.remise.home.createLabel">Create new Remise</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {remiseList && remiseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.remise.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.remise.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.remise.montant">Montant</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.remise.descreption">Descreption</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.remise.typeremise">Typeremise</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {remiseList.map((remise, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${remise.id}`} color="link" size="sm">
                      {remise.id}
                    </Button>
                  </td>
                  <td>{remise.numero}</td>
                  <td>{remise.montant}</td>
                  <td>{remise.descreption}</td>
                  <td>{remise.typeremise ? <Link to={`type-remise/${remise.typeremise.id}`}>{remise.typeremise.numero}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${remise.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${remise.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${remise.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.remise.home.notFound">No Remises found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ remise }: IRootState) => ({
  remiseList: remise.entities,
  loading: remise.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Remise);
