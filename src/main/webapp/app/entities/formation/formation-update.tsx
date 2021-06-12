import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './formation.reducer';
import { IFormation } from 'app/shared/model/formation.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFormationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FormationUpdate = (props: IFormationUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { formationEntity, categories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/formation');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCategories();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.ouvertureInscription = convertDateTimeToServer(values.ouvertureInscription);
    values.fermetureInscription = convertDateTimeToServer(values.fermetureInscription);

    if (errors.length === 0) {
      const entity = {
        ...formationEntity,
        ...values,
        category: categories.find(it => it.id.toString() === values.categoryId.toString()),
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
          <h2 id="insApplicationApp.formation.home.createOrEditLabel" data-cy="FormationCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.formation.home.createOrEditLabel">Create or edit a Formation</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : formationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="formation-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="formation-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="libilleLabel" for="formation-libille">
                  <Translate contentKey="insApplicationApp.formation.libille">Libille</Translate>
                </Label>
                <AvField
                  id="formation-libille"
                  data-cy="libille"
                  type="text"
                  name="libille"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="formation-description">
                  <Translate contentKey="insApplicationApp.formation.description">Description</Translate>
                </Label>
                <AvField
                  id="formation-description"
                  data-cy="description"
                  type="text"
                  name="description"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="ouvertureInscriptionLabel" for="formation-ouvertureInscription">
                  <Translate contentKey="insApplicationApp.formation.ouvertureInscription">Ouverture Inscription</Translate>
                </Label>
                <AvInput
                  id="formation-ouvertureInscription"
                  data-cy="ouvertureInscription"
                  type="datetime-local"
                  className="form-control"
                  name="ouvertureInscription"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.formationEntity.ouvertureInscription)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fermetureInscriptionLabel" for="formation-fermetureInscription">
                  <Translate contentKey="insApplicationApp.formation.fermetureInscription">Fermeture Inscription</Translate>
                </Label>
                <AvInput
                  id="formation-fermetureInscription"
                  data-cy="fermetureInscription"
                  type="datetime-local"
                  className="form-control"
                  name="fermetureInscription"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.formationEntity.fermetureInscription)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="seuilInscritsLabel" for="formation-seuilInscrits">
                  <Translate contentKey="insApplicationApp.formation.seuilInscrits">Seuil Inscrits</Translate>
                </Label>
                <AvField id="formation-seuilInscrits" data-cy="seuilInscrits" type="string" className="form-control" name="seuilInscrits" />
              </AvGroup>
              <AvGroup>
                <Label id="tarifLabel" for="formation-tarif">
                  <Translate contentKey="insApplicationApp.formation.tarif">Tarif</Translate>
                </Label>
                <AvField id="formation-tarif" data-cy="tarif" type="string" className="form-control" name="tarif" />
              </AvGroup>
              <AvGroup>
                <Label for="formation-category">
                  <Translate contentKey="insApplicationApp.formation.category">Category</Translate>
                </Label>
                <AvInput id="formation-category" data-cy="category" type="select" className="form-control" name="categoryId" required>
                  <option value="" key="0" />
                  {categories
                    ? categories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.libille}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/formation" replace color="info">
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
  categories: storeState.category.entities,
  formationEntity: storeState.formation.entity,
  loading: storeState.formation.loading,
  updating: storeState.formation.updating,
  updateSuccess: storeState.formation.updateSuccess,
});

const mapDispatchToProps = {
  getCategories,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FormationUpdate);
