import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFacture } from 'app/shared/model/facture.model';
import { getEntities as getFactures } from 'app/entities/facture/facture.reducer';
import { IInscription } from 'app/shared/model/inscription.model';
import { getEntities as getInscriptions } from 'app/entities/inscription/inscription.reducer';
import { IRemise } from 'app/shared/model/remise.model';
import { getEntities as getRemises } from 'app/entities/remise/remise.reducer';
import { getEntity, updateEntity, createEntity, reset } from './commande-inscriptions.reducer';
import { ICommandeInscriptions } from 'app/shared/model/commande-inscriptions.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICommandeInscriptionsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommandeInscriptionsUpdate = (props: ICommandeInscriptionsUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { commandeInscriptionsEntity, factures, inscriptions, remises, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/commande-inscriptions');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFactures();
    props.getInscriptions();
    props.getRemises();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...commandeInscriptionsEntity,
        ...values,
        facture: factures.find(it => it.id.toString() === values.factureId.toString()),
        inscription: inscriptions.find(it => it.id.toString() === values.inscriptionId.toString()),
        remise: remises.find(it => it.id.toString() === values.remiseId.toString()),
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
          <h2 id="insApplicationApp.commandeInscriptions.home.createOrEditLabel" data-cy="CommandeInscriptionsCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.commandeInscriptions.home.createOrEditLabel">
              Create or edit a CommandeInscriptions
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : commandeInscriptionsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="commande-inscriptions-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="commande-inscriptions-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numeroLabel" for="commande-inscriptions-numero">
                  <Translate contentKey="insApplicationApp.commandeInscriptions.numero">Numero</Translate>
                </Label>
                <AvField id="commande-inscriptions-numero" data-cy="numero" type="string" className="form-control" name="numero" />
              </AvGroup>
              <AvGroup>
                <Label id="totalAvantRemiseLabel" for="commande-inscriptions-totalAvantRemise">
                  <Translate contentKey="insApplicationApp.commandeInscriptions.totalAvantRemise">Total Avant Remise</Translate>
                </Label>
                <AvField
                  id="commande-inscriptions-totalAvantRemise"
                  data-cy="totalAvantRemise"
                  type="string"
                  className="form-control"
                  name="totalAvantRemise"
                />
              </AvGroup>
              <AvGroup>
                <Label id="totalRemiseLabel" for="commande-inscriptions-totalRemise">
                  <Translate contentKey="insApplicationApp.commandeInscriptions.totalRemise">Total Remise</Translate>
                </Label>
                <AvField
                  id="commande-inscriptions-totalRemise"
                  data-cy="totalRemise"
                  type="string"
                  className="form-control"
                  name="totalRemise"
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="commande-inscriptions-status">
                  <Translate contentKey="insApplicationApp.commandeInscriptions.status">Status</Translate>
                </Label>
                <AvInput
                  id="commande-inscriptions-status"
                  data-cy="status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && commandeInscriptionsEntity.status) || 'ENCOURS'}
                >
                  <option value="ENCOURS">{translate('insApplicationApp.Etatcommande.ENCOURS')}</option>
                  <option value="TRAITEE">{translate('insApplicationApp.Etatcommande.TRAITEE')}</option>
                  <option value="VALIDATIONPARTIELLE">{translate('insApplicationApp.Etatcommande.VALIDATIONPARTIELLE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="commande-inscriptions-facture">
                  <Translate contentKey="insApplicationApp.commandeInscriptions.facture">Facture</Translate>
                </Label>
                <AvInput id="commande-inscriptions-facture" data-cy="facture" type="select" className="form-control" name="factureId">
                  <option value="" key="0" />
                  {factures
                    ? factures.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.numero}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="commande-inscriptions-inscription">
                  <Translate contentKey="insApplicationApp.commandeInscriptions.inscription">Inscription</Translate>
                </Label>
                <AvInput
                  id="commande-inscriptions-inscription"
                  data-cy="inscription"
                  type="select"
                  className="form-control"
                  name="inscriptionId"
                >
                  <option value="" key="0" />
                  {inscriptions
                    ? inscriptions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="commande-inscriptions-remise">
                  <Translate contentKey="insApplicationApp.commandeInscriptions.remise">Remise</Translate>
                </Label>
                <AvInput id="commande-inscriptions-remise" data-cy="remise" type="select" className="form-control" name="remiseId">
                  <option value="" key="0" />
                  {remises
                    ? remises.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.numero}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/commande-inscriptions" replace color="info">
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
  factures: storeState.facture.entities,
  inscriptions: storeState.inscription.entities,
  remises: storeState.remise.entities,
  commandeInscriptionsEntity: storeState.commandeInscriptions.entity,
  loading: storeState.commandeInscriptions.loading,
  updating: storeState.commandeInscriptions.updating,
  updateSuccess: storeState.commandeInscriptions.updateSuccess,
});

const mapDispatchToProps = {
  getFactures,
  getInscriptions,
  getRemises,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommandeInscriptionsUpdate);
