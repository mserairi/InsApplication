import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-extras.reducer';
import { IUserExtras } from 'app/shared/model/user-extras.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserExtrasProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UserExtras = (props: IUserExtrasProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { userExtrasList, match, loading } = props;
  return (
    <div>
      <h2 id="user-extras-heading" data-cy="UserExtrasHeading">
        <Translate contentKey="insApplicationApp.userExtras.home.title">User Extras</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.userExtras.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.userExtras.home.createLabel">Create new User Extras</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userExtrasList && userExtrasList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.userExtras.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.userExtras.mob">Mob</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.userExtras.adresse">Adresse</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.userExtras.genre">Genre</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.userExtras.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userExtrasList.map((userExtras, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${userExtras.id}`} color="link" size="sm">
                      {userExtras.id}
                    </Button>
                  </td>
                  <td>{userExtras.mob}</td>
                  <td>{userExtras.adresse}</td>
                  <td>
                    <Translate contentKey={`insApplicationApp.TypeGenre.${userExtras.genre}`} />
                  </td>
                  <td>{userExtras.user ? userExtras.user.id : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${userExtras.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userExtras.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userExtras.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="insApplicationApp.userExtras.home.notFound">No User Extras found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ userExtras }: IRootState) => ({
  userExtrasList: userExtras.entities,
  loading: userExtras.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserExtras);
