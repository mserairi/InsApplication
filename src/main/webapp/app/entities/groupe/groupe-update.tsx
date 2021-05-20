import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICours } from 'app/shared/model/cours.model';
import { getEntities as getCours } from 'app/entities/cours/cours.reducer';
import { IEnfant } from 'app/shared/model/enfant.model';
import { getEntities as getEnfants } from 'app/entities/enfant/enfant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './groupe.reducer';
import { IGroupe } from 'app/shared/model/groupe.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGroupeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GroupeUpdate = (props: IGroupeUpdateProps) => {
  const [idsenfant, setIdsenfant] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { groupeEntity, cours, enfants, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/groupe');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCours();
    props.getEnfants();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...groupeEntity,
        ...values,
        enfants: mapIdList(values.enfants),
        cours: cours.find(it => it.id.toString() === values.coursId.toString()),
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
          <h2 id="insApplicationApp.groupe.home.createOrEditLabel" data-cy="GroupeCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.groupe.home.createOrEditLabel">Create or edit a Groupe</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : groupeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="groupe-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="groupe-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numeroLabel" for="groupe-numero">
                  <Translate contentKey="insApplicationApp.groupe.numero">Numero</Translate>
                </Label>
                <AvField id="groupe-numero" data-cy="numero" type="text" name="numero" />
              </AvGroup>
              <AvGroup>
                <Label id="libilleLabel" for="groupe-libille">
                  <Translate contentKey="insApplicationApp.groupe.libille">Libille</Translate>
                </Label>
                <AvField id="groupe-libille" data-cy="libille" type="text" name="libille" />
              </AvGroup>
              <AvGroup>
                <Label id="lasessionLabel" for="groupe-lasession">
                  <Translate contentKey="insApplicationApp.groupe.lasession">Lasession</Translate>
                </Label>
                <AvField id="groupe-lasession" data-cy="lasession" type="text" name="lasession" />
              </AvGroup>
              <AvGroup>
                <Label id="nbrApprenantLabel" for="groupe-nbrApprenant">
                  <Translate contentKey="insApplicationApp.groupe.nbrApprenant">Nbr Apprenant</Translate>
                </Label>
                <AvField id="groupe-nbrApprenant" data-cy="nbrApprenant" type="string" className="form-control" name="nbrApprenant" />
              </AvGroup>
              <AvGroup>
                <Label for="groupe-cours">
                  <Translate contentKey="insApplicationApp.groupe.cours">Cours</Translate>
                </Label>
                <AvInput id="groupe-cours" data-cy="cours" type="select" className="form-control" name="coursId">
                  <option value="" key="0" />
                  {cours
                    ? cours.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="groupe-enfant">
                  <Translate contentKey="insApplicationApp.groupe.enfant">Enfant</Translate>
                </Label>
                <AvInput
                  id="groupe-enfant"
                  data-cy="enfant"
                  type="select"
                  multiple
                  className="form-control"
                  name="enfants"
                  value={!isNew && groupeEntity.enfants && groupeEntity.enfants.map(e => e.id)}
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
              <Button tag={Link} id="cancel-save" to="/groupe" replace color="info">
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
  cours: storeState.cours.entities,
  enfants: storeState.enfant.entities,
  groupeEntity: storeState.groupe.entity,
  loading: storeState.groupe.loading,
  updating: storeState.groupe.updating,
  updateSuccess: storeState.groupe.updateSuccess,
});

const mapDispatchToProps = {
  getCours,
  getEnfants,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GroupeUpdate);
