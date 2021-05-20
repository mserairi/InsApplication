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
import { getEntity, updateEntity, createEntity, reset } from './paiement.reducer';
import { IPaiement } from 'app/shared/model/paiement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPaiementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaiementUpdate = (props: IPaiementUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { paiementEntity, factures, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/paiement');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFactures();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...paiementEntity,
        ...values,
        facture: factures.find(it => it.id.toString() === values.factureId.toString()),
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
          <h2 id="insApplicationApp.paiement.home.createOrEditLabel" data-cy="PaiementCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.paiement.home.createOrEditLabel">Create or edit a Paiement</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : paiementEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="paiement-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="paiement-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numeroLabel" for="paiement-numero">
                  <Translate contentKey="insApplicationApp.paiement.numero">Numero</Translate>
                </Label>
                <AvField id="paiement-numero" data-cy="numero" type="string" className="form-control" name="numero" />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="paiement-date">
                  <Translate contentKey="insApplicationApp.paiement.date">Date</Translate>
                </Label>
                <AvInput
                  id="paiement-date"
                  data-cy="date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.paiementEntity.date)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="montantLabel" for="paiement-montant">
                  <Translate contentKey="insApplicationApp.paiement.montant">Montant</Translate>
                </Label>
                <AvField id="paiement-montant" data-cy="montant" type="string" className="form-control" name="montant" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="paiement-type">
                  <Translate contentKey="insApplicationApp.paiement.type">Type</Translate>
                </Label>
                <AvInput
                  id="paiement-type"
                  data-cy="type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && paiementEntity.type) || 'ENLIGNE'}
                >
                  <option value="ENLIGNE">{translate('insApplicationApp.TypePaiement.ENLIGNE')}</option>
                  <option value="SPCARTE">{translate('insApplicationApp.TypePaiement.SPCARTE')}</option>
                  <option value="SPLIQUIDE">{translate('insApplicationApp.TypePaiement.SPLIQUIDE')}</option>
                  <option value="SPESPECE">{translate('insApplicationApp.TypePaiement.SPESPECE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="paiement-facture">
                  <Translate contentKey="insApplicationApp.paiement.facture">Facture</Translate>
                </Label>
                <AvInput id="paiement-facture" data-cy="facture" type="select" className="form-control" name="factureId">
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
              <Button tag={Link} id="cancel-save" to="/paiement" replace color="info">
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
  paiementEntity: storeState.paiement.entity,
  loading: storeState.paiement.loading,
  updating: storeState.paiement.updating,
  updateSuccess: storeState.paiement.updateSuccess,
});

const mapDispatchToProps = {
  getFactures,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaiementUpdate);
