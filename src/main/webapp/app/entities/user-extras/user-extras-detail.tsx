import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-extras.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserExtrasDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserExtrasDetail = (props: IUserExtrasDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userExtrasEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userExtrasDetailsHeading">
          <Translate contentKey="insApplicationApp.userExtras.detail.title">UserExtras</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userExtrasEntity.id}</dd>
          <dt>
            <span id="mob">
              <Translate contentKey="insApplicationApp.userExtras.mob">Mob</Translate>
            </span>
          </dt>
          <dd>{userExtrasEntity.mob}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="insApplicationApp.userExtras.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{userExtrasEntity.adresse}</dd>
          <dt>
            <span id="genre">
              <Translate contentKey="insApplicationApp.userExtras.genre">Genre</Translate>
            </span>
          </dt>
          <dd>{userExtrasEntity.genre}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.userExtras.user">User</Translate>
          </dt>
          <dd>{userExtrasEntity.user ? userExtrasEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-extras" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-extras/${userExtrasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userExtras }: IRootState) => ({
  userExtrasEntity: userExtras.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserExtrasDetail);
