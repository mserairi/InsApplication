import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEnfant } from 'app/shared/model/enfant.model';
import { getEntities as getEnfants } from 'app/entities/enfant/enfant.reducer';
import { IFormation } from 'app/shared/model/formation.model';
import { getEntities as getFormations } from 'app/entities/formation/formation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './inscription.reducer';
import { IInscription } from 'app/shared/model/inscription.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInscriptionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InscriptionUpdate = (props: IInscriptionUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { inscriptionEntity, enfants, formations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/inscription');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEnfants();
    props.getFormations();
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
        inscrit: enfants.find(it => it.id.toString() === values.inscritId.toString()),
        formation: formations.find(it => it.id.toString() === values.formationId.toString()),
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
                <Label id="statusLabel" for="inscription-status">
                  <Translate contentKey="insApplicationApp.inscription.status">Status</Translate>
                </Label>
                <AvInput
                  id="inscription-status"
                  data-cy="status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && inscriptionEntity.status) || 'ENREGISTREE'}
                >
                  <option value="ENREGISTREE">{translate('insApplicationApp.EtatInscription.ENREGISTREE')}</option>
                  <option value="LISTE_ATTENTE">{translate('insApplicationApp.EtatInscription.LISTE_ATTENTE')}</option>
                  <option value="VALIDEE">{translate('insApplicationApp.EtatInscription.VALIDEE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="remarquesLabel" for="inscription-remarques">
                  <Translate contentKey="insApplicationApp.inscription.remarques">Remarques</Translate>
                </Label>
                <AvField id="inscription-remarques" data-cy="remarques" type="text" name="remarques" />
              </AvGroup>
              <AvGroup check>
                <Label id="instoLATLabel">
                  <AvInput id="inscription-instoLAT" data-cy="instoLAT" type="checkbox" className="form-check-input" name="instoLAT" />
                  <Translate contentKey="insApplicationApp.inscription.instoLAT">Insto LAT</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="inscription-inscrit">
                  <Translate contentKey="insApplicationApp.inscription.inscrit">Inscrit</Translate>
                </Label>
                <AvInput id="inscription-inscrit" data-cy="inscrit" type="select" className="form-control" name="inscritId" required>
                  <option value="" key="0" />
                  {enfants
                    ? enfants.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.prenom}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="inscription-formation">
                  <Translate contentKey="insApplicationApp.inscription.formation">Formation</Translate>
                </Label>
                <AvInput id="inscription-formation" data-cy="formation" type="select" className="form-control" name="formationId" required>
                  <option value="" key="0" />
                  {formations
                    ? formations.map(otherEntity => (
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
  enfants: storeState.enfant.entities,
  formations: storeState.formation.entities,
  inscriptionEntity: storeState.inscription.entity,
  loading: storeState.inscription.loading,
  updating: storeState.inscription.updating,
  updateSuccess: storeState.inscription.updateSuccess,
});

const mapDispatchToProps = {
  getEnfants,
  getFormations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InscriptionUpdate);
