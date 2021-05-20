import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './type-remise.reducer';
import { ITypeRemise } from 'app/shared/model/type-remise.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITypeRemiseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const TypeRemise = (props: ITypeRemiseProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { typeRemiseList, match, loading } = props;
  return (
    <div>
      <h2 id="type-remise-heading" data-cy="TypeRemiseHeading">
        <Translate contentKey="insApplicationApp.typeRemise.home.title">Type Remises</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.typeRemise.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.typeRemise.home.createLabel">Create new Type Remise</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {typeRemiseList && typeRemiseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.typeRemise.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.typeRemise.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.typeRemise.libille">Libille</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.typeRemise.condition">Condition</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.typeRemise.mantantLibre">Mantant Libre</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.typeRemise.montantUnitaire">Montant Unitaire</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {typeRemiseList.map((typeRemise, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${typeRemise.id}`} color="link" size="sm">
                      {typeRemise.id}
                    </Button>
                  </td>
                  <td>{typeRemise.numero}</td>
                  <td>{typeRemise.libille}</td>
                  <td>{typeRemise.condition}</td>
                  <td>{typeRemise.mantantLibre ? 'true' : 'false'}</td>
                  <td>{typeRemise.montantUnitaire}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${typeRemise.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${typeRemise.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${typeRemise.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.typeRemise.home.notFound">No Type Remises found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ typeRemise }: IRootState) => ({
  typeRemiseList: typeRemise.entities,
  loading: typeRemise.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypeRemise);
