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
import { IEnfant } from 'app/shared/model/enfant.model';
import { getEntities as getEnfants } from 'app/entities/enfant/enfant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './inscription.reducer';
import { IInscription } from 'app/shared/model/inscription.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInscriptionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InscriptionUpdate = (props: IInscriptionUpdateProps) => {
  const [idsinscrits, setIdsinscrits] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { inscriptionEntity, categories, enfants, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/inscription');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCategories();
    props.getEnfants();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.dateinscription = convertDateTimeToServer(values.dateinscription);

    if (errors.length === 0) {
      const entity = {
        ...inscriptionEntity,
        ...values,
        inscrits: mapIdList(values.inscrits),
        concerne: categories.find(it => it.id.toString() === values.concerneId.toString()),
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
          <h2 id="insApplicationApp.inscription.home.createOrEditLabel" data-cy="InscriptionCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.inscription.home.createOrEditLabel">Create or edit a Inscription</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : inscriptionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="inscription-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="inscription-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateinscriptionLabel" for="inscription-dateinscription">
                  <Translate contentKey="insApplicationApp.inscription.dateinscription">Dateinscription</Translate>
                </Label>
                <AvInput
                  id="inscription-dateinscription"
                  data-cy="dateinscription"
                  type="datetime-local"
                  className="form-control"
                  name="dateinscription"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.inscriptionEntity.dateinscription)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lasessionLabel" for="inscription-lasession">
                  <Translate contentKey="insApplicationApp.inscription.lasession">Lasession</Translate>
                </Label>
                <AvField
                  id="inscription-lasession"
                  data-cy="lasession"
                  type="text"
                  name="lasession"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="inscription-concerne">
                  <Translate contentKey="insApplicationApp.inscription.concerne">Concerne</Translate>
                </Label>
                <AvInput id="inscription-concerne" data-cy="concerne" type="select" className="form-control" name="concerneId">
                  <option value="" key="0" />
                  {categories
                    ? categories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.libile}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="inscription-inscrits">
                  <Translate contentKey="insApplicationApp.inscription.inscrits">Inscrits</Translate>
                </Label>
                <AvInput
                  id="inscription-inscrits"
                  data-cy="inscrits"
                  type="select"
                  multiple
                  className="form-control"
                  name="inscrits"
                  value={!isNew && inscriptionEntity.inscrits && inscriptionEntity.inscrits.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {enfants
                    ? enfants.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/inscription" replace color="info">
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
  enfants: storeState.enfant.entities,
  inscriptionEntity: storeState.inscription.entity,
  loading: storeState.inscription.loading,
  updating: storeState.inscription.updating,
  updateSuccess: storeState.inscription.updateSuccess,
});

const mapDispatchToProps = {
  getCategories,
  getEnfants,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InscriptionUpdate);
