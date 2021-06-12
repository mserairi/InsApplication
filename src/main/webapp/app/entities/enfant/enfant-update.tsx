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
import { getEntity, updateEntity, createEntity, reset } from './enfant.reducer';
import { IEnfant } from 'app/shared/model/enfant.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEnfantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnfantUpdate = (props: IEnfantUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { enfantEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/enfant');
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
    values.dateNaissance = convertDateTimeToServer(values.dateNaissance);

    if (errors.length === 0) {
      const entity = {
        ...enfantEntity,
        ...values,
        parent: users.find(it => it.id.toString() === values.parentId.toString()),
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
          <h2 id="insApplicationApp.enfant.home.createOrEditLabel" data-cy="EnfantCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.enfant.home.createOrEditLabel">Create or edit a Enfant</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : enfantEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="enfant-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="enfant-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomLabel" for="enfant-nom">
                  <Translate contentKey="insApplicationApp.enfant.nom">Nom</Translate>
                </Label>
                <AvField
                  id="enfant-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="prenomLabel" for="enfant-prenom">
                  <Translate contentKey="insApplicationApp.enfant.prenom">Prenom</Translate>
                </Label>
                <AvField
                  id="enfant-prenom"
                  data-cy="prenom"
                  type="text"
                  name="prenom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateNaissanceLabel" for="enfant-dateNaissance">
                  <Translate contentKey="insApplicationApp.enfant.dateNaissance">Date Naissance</Translate>
                </Label>
                <AvInput
                  id="enfant-dateNaissance"
                  data-cy="dateNaissance"
                  type="datetime-local"
                  className="form-control"
                  name="dateNaissance"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.enfantEntity.dateNaissance)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="genreLabel" for="enfant-genre">
                  <Translate contentKey="insApplicationApp.enfant.genre">Genre</Translate>
                </Label>
                <AvInput
                  id="enfant-genre"
                  data-cy="genre"
                  type="select"
                  className="form-control"
                  name="genre"
                  value={(!isNew && enfantEntity.genre) || 'MASCULIN'}
                >
                  <option value="MASCULIN">{translate('insApplicationApp.TypeGenre.MASCULIN')}</option>
                  <option value="FEMININ">{translate('insApplicationApp.TypeGenre.FEMININ')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="nomParent2Label" for="enfant-nomParent2">
                  <Translate contentKey="insApplicationApp.enfant.nomParent2">Nom Parent 2</Translate>
                </Label>
                <AvField id="enfant-nomParent2" data-cy="nomParent2" type="text" name="nomParent2" />
              </AvGroup>
              <AvGroup>
                <Label id="prenomParent2Label" for="enfant-prenomParent2">
                  <Translate contentKey="insApplicationApp.enfant.prenomParent2">Prenom Parent 2</Translate>
                </Label>
                <AvField id="enfant-prenomParent2" data-cy="prenomParent2" type="text" name="prenomParent2" />
              </AvGroup>
              <AvGroup>
                <Label id="mobParent2Label" for="enfant-mobParent2">
                  <Translate contentKey="insApplicationApp.enfant.mobParent2">Mob Parent 2</Translate>
                </Label>
                <AvField
                  id="enfant-mobParent2"
                  data-cy="mobParent2"
                  type="text"
                  name="mobParent2"
                  validate={{
                    pattern: { value: '^\\d{10,10}$', errorMessage: translate('entity.validation.pattern', { pattern: '^\\d{10,10}$' }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailParent2Label" for="enfant-emailParent2">
                  <Translate contentKey="insApplicationApp.enfant.emailParent2">Email Parent 2</Translate>
                </Label>
                <AvField
                  id="enfant-emailParent2"
                  data-cy="emailParent2"
                  type="text"
                  name="emailParent2"
                  validate={{
                    pattern: { value: '^.+@.+$', errorMessage: translate('entity.validation.pattern', { pattern: '^.+@.+$' }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="infoSanteLabel" for="enfant-infoSante">
                  <Translate contentKey="insApplicationApp.enfant.infoSante">Info Sante</Translate>
                </Label>
                <AvField id="enfant-infoSante" data-cy="infoSante" type="text" name="infoSante" />
              </AvGroup>
              <AvGroup check>
                <Label id="autorisationImageLabel">
                  <AvInput
                    id="enfant-autorisationImage"
                    data-cy="autorisationImage"
                    type="checkbox"
                    className="form-check-input"
                    name="autorisationImage"
                  />
                  <Translate contentKey="insApplicationApp.enfant.autorisationImage">Autorisation Image</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="nomContactLabel" for="enfant-nomContact">
                  <Translate contentKey="insApplicationApp.enfant.nomContact">Nom Contact</Translate>
                </Label>
                <AvField id="enfant-nomContact" data-cy="nomContact" type="text" name="nomContact" />
              </AvGroup>
              <AvGroup>
                <Label id="mobContactLabel" for="enfant-mobContact">
                  <Translate contentKey="insApplicationApp.enfant.mobContact">Mob Contact</Translate>
                </Label>
                <AvField
                  id="enfant-mobContact"
                  data-cy="mobContact"
                  type="text"
                  name="mobContact"
                  validate={{
                    pattern: { value: '^\\d{10,10}$', errorMessage: translate('entity.validation.pattern', { pattern: '^\\d{10,10}$' }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="enfant-parent">
                  <Translate contentKey="insApplicationApp.enfant.parent">Parent</Translate>
                </Label>
                <AvInput id="enfant-parent" data-cy="parent" type="select" className="form-control" name="parentId" required>
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/enfant" replace color="info">
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
  enfantEntity: storeState.enfant.entity,
  loading: storeState.enfant.loading,
  updating: storeState.enfant.updating,
  updateSuccess: storeState.enfant.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(EnfantUpdate);
