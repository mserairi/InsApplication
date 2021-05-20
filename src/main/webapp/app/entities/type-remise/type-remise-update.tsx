import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './type-remise.reducer';
import { ITypeRemise } from 'app/shared/model/type-remise.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITypeRemiseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TypeRemiseUpdate = (props: ITypeRemiseUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { typeRemiseEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/type-remise');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...typeRemiseEntity,
        ...values,
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
          <h2 id="insApplicationApp.typeRemise.home.createOrEditLabel" data-cy="TypeRemiseCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.typeRemise.home.createOrEditLabel">Create or edit a TypeRemise</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : typeRemiseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="type-remise-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="type-remise-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numeroLabel" for="type-remise-numero">
                  <Translate contentKey="insApplicationApp.typeRemise.numero">Numero</Translate>
                </Label>
                <AvField id="type-remise-numero" data-cy="numero" type="string" className="form-control" name="numero" />
              </AvGroup>
              <AvGroup>
                <Label id="libilleLabel" for="type-remise-libille">
                  <Translate contentKey="insApplicationApp.typeRemise.libille">Libille</Translate>
                </Label>
                <AvField id="type-remise-libille" data-cy="libille" type="text" name="libille" />
              </AvGroup>
              <AvGroup>
                <Label id="conditionLabel" for="type-remise-condition">
                  <Translate contentKey="insApplicationApp.typeRemise.condition">Condition</Translate>
                </Label>
                <AvField id="type-remise-condition" data-cy="condition" type="text" name="condition" />
              </AvGroup>
              <AvGroup check>
                <Label id="mantantLibreLabel">
                  <AvInput
                    id="type-remise-mantantLibre"
                    data-cy="mantantLibre"
                    type="checkbox"
                    className="form-check-input"
                    name="mantantLibre"
                  />
                  <Translate contentKey="insApplicationApp.typeRemise.mantantLibre">Mantant Libre</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="montantUnitaireLabel" for="type-remise-montantUnitaire">
                  <Translate contentKey="insApplicationApp.typeRemise.montantUnitaire">Montant Unitaire</Translate>
                </Label>
                <AvField
                  id="type-remise-montantUnitaire"
                  data-cy="montantUnitaire"
                  type="string"
                  className="form-control"
                  name="montantUnitaire"
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/type-remise" replace color="info">
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
  typeRemiseEntity: storeState.typeRemise.entity,
  loading: storeState.typeRemise.loading,
  updating: storeState.typeRemise.updating,
  updateSuccess: storeState.typeRemise.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypeRemiseUpdate);
