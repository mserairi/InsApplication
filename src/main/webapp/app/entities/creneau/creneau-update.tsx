import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISalle } from 'app/shared/model/salle.model';
import { getEntities as getSalles } from 'app/entities/salle/salle.reducer';
import { IGroupe } from 'app/shared/model/groupe.model';
import { getEntities as getGroupes } from 'app/entities/groupe/groupe.reducer';
import { getEntity, updateEntity, createEntity, reset } from './creneau.reducer';
import { ICreneau } from 'app/shared/model/creneau.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICreneauUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CreneauUpdate = (props: ICreneauUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { creneauEntity, salles, groupes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/creneau');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSalles();
    props.getGroupes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.deb = convertDateTimeToServer(values.deb);
    values.fin = convertDateTimeToServer(values.fin);

    if (errors.length === 0) {
      const entity = {
        ...creneauEntity,
        ...values,
        salle: salles.find(it => it.id.toString() === values.salleId.toString()),
        groupe: groupes.find(it => it.id.toString() === values.groupeId.toString()),
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
          <h2 id="insApplicationApp.creneau.home.createOrEditLabel" data-cy="CreneauCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.creneau.home.createOrEditLabel">Create or edit a Creneau</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : creneauEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="creneau-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="creneau-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeCreneauLabel" for="creneau-typeCreneau">
                  <Translate contentKey="insApplicationApp.creneau.typeCreneau">Type Creneau</Translate>
                </Label>
                <AvInput
                  id="creneau-typeCreneau"
                  data-cy="typeCreneau"
                  type="select"
                  className="form-control"
                  name="typeCreneau"
                  value={(!isNew && creneauEntity.typeCreneau) || 'PONCTUEL'}
                >
                  <option value="PONCTUEL">{translate('insApplicationApp.TYPECRENEAU.PONCTUEL')}</option>
                  <option value="REPETETIF">{translate('insApplicationApp.TYPECRENEAU.REPETETIF')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="jourLabel" for="creneau-jour">
                  <Translate contentKey="insApplicationApp.creneau.jour">Jour</Translate>
                </Label>
                <AvInput
                  id="creneau-jour"
                  data-cy="jour"
                  type="select"
                  className="form-control"
                  name="jour"
                  value={(!isNew && creneauEntity.jour) || 'LUNDI'}
                >
                  <option value="LUNDI">{translate('insApplicationApp.JourSemaine.LUNDI')}</option>
                  <option value="MARDI">{translate('insApplicationApp.JourSemaine.MARDI')}</option>
                  <option value="MERCREDI">{translate('insApplicationApp.JourSemaine.MERCREDI')}</option>
                  <option value="JEUDI">{translate('insApplicationApp.JourSemaine.JEUDI')}</option>
                  <option value="VENDREDI">{translate('insApplicationApp.JourSemaine.VENDREDI')}</option>
                  <option value="SAMEDI">{translate('insApplicationApp.JourSemaine.SAMEDI')}</option>
                  <option value="DIMANCHE">{translate('insApplicationApp.JourSemaine.DIMANCHE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="debLabel" for="creneau-deb">
                  <Translate contentKey="insApplicationApp.creneau.deb">Deb</Translate>
                </Label>
                <AvInput
                  id="creneau-deb"
                  data-cy="deb"
                  type="datetime-local"
                  className="form-control"
                  name="deb"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.creneauEntity.deb)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="finLabel" for="creneau-fin">
                  <Translate contentKey="insApplicationApp.creneau.fin">Fin</Translate>
                </Label>
                <AvInput
                  id="creneau-fin"
                  data-cy="fin"
                  type="datetime-local"
                  className="form-control"
                  name="fin"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.creneauEntity.fin)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="creneau-salle">
                  <Translate contentKey="insApplicationApp.creneau.salle">Salle</Translate>
                </Label>
                <AvInput id="creneau-salle" data-cy="salle" type="select" className="form-control" name="salleId">
                  <option value="" key="0" />
                  {salles
                    ? salles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.numero}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="creneau-groupe">
                  <Translate contentKey="insApplicationApp.creneau.groupe">Groupe</Translate>
                </Label>
                <AvInput id="creneau-groupe" data-cy="groupe" type="select" className="form-control" name="groupeId">
                  <option value="" key="0" />
                  {groupes
                    ? groupes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/creneau" replace color="info">
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
  salles: storeState.salle.entities,
  groupes: storeState.groupe.entities,
  creneauEntity: storeState.creneau.entity,
  loading: storeState.creneau.loading,
  updating: storeState.creneau.updating,
  updateSuccess: storeState.creneau.updateSuccess,
});

const mapDispatchToProps = {
  getSalles,
  getGroupes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CreneauUpdate);
