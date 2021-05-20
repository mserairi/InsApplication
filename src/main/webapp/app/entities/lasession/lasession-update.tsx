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
import { getEntity, updateEntity, createEntity, reset } from './lasession.reducer';
import { ILasession } from 'app/shared/model/lasession.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILasessionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LasessionUpdate = (props: ILasessionUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { lasessionEntity, categories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/lasession');
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
    values.debut = convertDateTimeToServer(values.debut);
    values.fin = convertDateTimeToServer(values.fin);

    if (errors.length === 0) {
      const entity = {
        ...lasessionEntity,
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
          <h2 id="insApplicationApp.lasession.home.createOrEditLabel" data-cy="LasessionCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.lasession.home.createOrEditLabel">Create or edit a Lasession</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : lasessionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="lasession-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="lasession-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="lasession-code">
                  <Translate contentKey="insApplicationApp.lasession.code">Code</Translate>
                </Label>
                <AvField
                  id="lasession-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="lasession-description">
                  <Translate contentKey="insApplicationApp.lasession.description">Description</Translate>
                </Label>
                <AvField
                  id="lasession-description"
                  data-cy="description"
                  type="text"
                  name="description"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tarifLabel" for="lasession-tarif">
                  <Translate contentKey="insApplicationApp.lasession.tarif">Tarif</Translate>
                </Label>
                <AvField id="lasession-tarif" data-cy="tarif" type="string" className="form-control" name="tarif" />
              </AvGroup>
              <AvGroup>
                <Label id="debutLabel" for="lasession-debut">
                  <Translate contentKey="insApplicationApp.lasession.debut">Debut</Translate>
                </Label>
                <AvInput
                  id="lasession-debut"
                  data-cy="debut"
                  type="datetime-local"
                  className="form-control"
                  name="debut"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.lasessionEntity.debut)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="finLabel" for="lasession-fin">
                  <Translate contentKey="insApplicationApp.lasession.fin">Fin</Translate>
                </Label>
                <AvInput
                  id="lasession-fin"
                  data-cy="fin"
                  type="datetime-local"
                  className="form-control"
                  name="fin"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.lasessionEntity.fin)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="lasession-category">
                  <Translate contentKey="insApplicationApp.lasession.category">Category</Translate>
                </Label>
                <AvInput id="lasession-category" data-cy="category" type="select" className="form-control" name="categoryId">
                  <option value="" key="0" />
                  {categories
                    ? categories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.libille}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/lasession" replace color="info">
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
  lasessionEntity: storeState.lasession.entity,
  loading: storeState.lasession.loading,
  updating: storeState.lasession.updating,
  updateSuccess: storeState.lasession.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(LasessionUpdate);
