import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITypeRemise } from 'app/shared/model/type-remise.model';
import { getEntities as getTypeRemises } from 'app/entities/type-remise/type-remise.reducer';
import { getEntity, updateEntity, createEntity, reset } from './remise.reducer';
import { IRemise } from 'app/shared/model/remise.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRemiseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RemiseUpdate = (props: IRemiseUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { remiseEntity, typeRemises, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/remise');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTypeRemises();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...remiseEntity,
        ...values,
        typeremise: typeRemises.find(it => it.id.toString() === values.typeremiseId.toString()),
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
          <h2 id="insApplicationApp.remise.home.createOrEditLabel" data-cy="RemiseCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.remise.home.createOrEditLabel">Create or edit a Remise</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : remiseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="remise-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="remise-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numeroLabel" for="remise-numero">
                  <Translate contentKey="insApplicationApp.remise.numero">Numero</Translate>
                </Label>
                <AvField id="remise-numero" data-cy="numero" type="string" className="form-control" name="numero" />
              </AvGroup>
              <AvGroup>
                <Label id="montantLabel" for="remise-montant">
                  <Translate contentKey="insApplicationApp.remise.montant">Montant</Translate>
                </Label>
                <AvField id="remise-montant" data-cy="montant" type="string" className="form-control" name="montant" />
              </AvGroup>
              <AvGroup>
                <Label id="descreptionLabel" for="remise-descreption">
                  <Translate contentKey="insApplicationApp.remise.descreption">Descreption</Translate>
                </Label>
                <AvField id="remise-descreption" data-cy="descreption" type="text" name="descreption" />
              </AvGroup>
              <AvGroup>
                <Label for="remise-typeremise">
                  <Translate contentKey="insApplicationApp.remise.typeremise">Typeremise</Translate>
                </Label>
                <AvInput id="remise-typeremise" data-cy="typeremise" type="select" className="form-control" name="typeremiseId">
                  <option value="" key="0" />
                  {typeRemises
                    ? typeRemises.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.numero}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/remise" replace color="info">
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
  typeRemises: storeState.typeRemise.entities,
  remiseEntity: storeState.remise.entity,
  loading: storeState.remise.loading,
  updating: storeState.remise.updating,
  updateSuccess: storeState.remise.updateSuccess,
});

const mapDispatchToProps = {
  getTypeRemises,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RemiseUpdate);
