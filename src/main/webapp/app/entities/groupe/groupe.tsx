import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './groupe.reducer';
import { IGroupe } from 'app/shared/model/groupe.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGroupeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Groupe = (props: IGroupeProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { groupeList, match, loading } = props;
  return (
    <div>
      <h2 id="groupe-heading" data-cy="GroupeHeading">
        <Translate contentKey="insApplicationApp.groupe.home.title">Groupes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.groupe.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.groupe.home.createLabel">Create new Groupe</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {groupeList && groupeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.groupe.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.groupe.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.groupe.libille">Libille</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.groupe.lasession">Lasession</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.groupe.nbrApprenant">Nbr Apprenant</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.groupe.cours">Cours</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.groupe.enfant">Enfant</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {groupeList.map((groupe, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${groupe.id}`} color="link" size="sm">
                      {groupe.id}
                    </Button>
                  </td>
                  <td>{groupe.numero}</td>
                  <td>{groupe.libille}</td>
                  <td>{groupe.lasession}</td>
                  <td>{groupe.nbrApprenant}</td>
                  <td>{groupe.cours ? <Link to={`cours/${groupe.cours.id}`}>{groupe.cours.id}</Link> : ''}</td>
                  <td>
                    {groupe.enfants
                      ? groupe.enfants.map((val, j) => (
                          <span key={j}>
                            <Link to={`enfant/${val.id}`}>{val.id}</Link>
                            {j === groupe.enfants.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${groupe.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${groupe.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${groupe.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.groupe.home.notFound">No Groupes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ groupe }: IRootState) => ({
  groupeList: groupe.entities,
  loading: groupe.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Groupe);
