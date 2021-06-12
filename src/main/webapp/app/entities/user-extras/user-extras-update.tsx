import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-extras.reducer';
import { IUserExtras } from 'app/shared/model/user-extras.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserExtrasUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserExtrasUpdate = (props: IUserExtrasUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { userExtrasEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/user-extras');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...userExtrasEntity,
        ...values,
        user: users.find(it => it.id.toString() === values.userId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="insApplicationApp.userExtras.home.createOrEditLabel" data-cy="UserExtrasCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.userExtras.home.createOrEditLabel">Create or edit a UserExtras</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userExtrasEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-extras-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="user-extras-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="mobLabel" for="user-extras-mob">
                  <Translate contentKey="insApplicationApp.userExtras.mob">Mob</Translate>
                </Label>
                <AvField
                  id="user-extras-mob"
                  data-cy="mob"
                  type="text"
                  name="mob"
                  validate={{
                    pattern: { value: '^\\d{10,10}$', errorMessage: translate('entity.validation.pattern', { pattern: '^\\d{10,10}$' }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="adresseLabel" for="user-extras-adresse">
                  <Translate contentKey="insApplicationApp.userExtras.adresse">Adresse</Translate>
                </Label>
                <AvField id="user-extras-adresse" data-cy="adresse" type="text" name="adresse" />
              </AvGroup>
              <AvGroup>
                <Label id="genreLabel" for="user-extras-genre">
                  <Translate contentKey="insApplicationApp.userExtras.genre">Genre</Translate>
                </Label>
                <AvInput
                  id="user-extras-genre"
                  data-cy="genre"
                  type="select"
                  className="form-control"
                  name="genre"
                  value={(!isNew && userExtrasEntity.genre) || 'MASCULIN'}
                >
                  <option value="MASCULIN">{translate('insApplicationApp.TypeGenre.MASCULIN')}</option>
                  <option value="FEMININ">{translate('insApplicationApp.TypeGenre.FEMININ')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="user-extras-user">
                  <Translate contentKey="insApplicationApp.userExtras.user">User</Translate>
                </Label>
                <AvInput id="user-extras-user" data-cy="user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/user-extras" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  userExtrasEntity: storeState.userExtras.entity,
  loading: storeState.userExtras.loading,
  updating: storeState.userExtras.updating,
  updateSuccess: storeState.userExtras.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserExtrasUpdate);
