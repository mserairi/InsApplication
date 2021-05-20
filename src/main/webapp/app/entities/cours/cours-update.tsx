import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISousCategory } from 'app/shared/model/sous-category.model';
import { getEntities as getSousCategories } from 'app/entities/sous-category/sous-category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cours.reducer';
import { ICours } from 'app/shared/model/cours.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICoursUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CoursUpdate = (props: ICoursUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { coursEntity, sousCategories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/cours');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSousCategories();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...coursEntity,
        ...values,
        souscategory: sousCategories.find(it => it.id.toString() === values.souscategoryId.toString()),
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
          <h2 id="insApplicationApp.cours.home.createOrEditLabel" data-cy="CoursCreateUpdateHeading">
            <Translate contentKey="insApplicationApp.cours.home.createOrEditLabel">Create or edit a Cours</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : coursEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="cours-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="cours-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numeroLabel" for="cours-numero">
                  <Translate contentKey="insApplicationApp.cours.numero">Numero</Translate>
                </Label>
                <AvField id="cours-numero" data-cy="numero" type="text" name="numero" />
              </AvGroup>
              <AvGroup>
                <Label id="libilleLabel" for="cours-libille">
                  <Translate contentKey="insApplicationApp.cours.libille">Libille</Translate>
                </Label>
                <AvField id="cours-libille" data-cy="libille" type="text" name="libille" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="cours-description">
                  <Translate contentKey="insApplicationApp.cours.description">Description</Translate>
                </Label>
                <AvField id="cours-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="seuilLabel" for="cours-seuil">
                  <Translate contentKey="insApplicationApp.cours.seuil">Seuil</Translate>
                </Label>
                <AvField id="cours-seuil" data-cy="seuil" type="string" className="form-control" name="seuil" />
              </AvGroup>
              <AvGroup>
                <Label id="dureeLabel" for="cours-duree">
                  <Translate contentKey="insApplicationApp.cours.duree">Duree</Translate>
                </Label>
                <AvField id="cours-duree" data-cy="duree" type="string" className="form-control" name="duree" />
              </AvGroup>
              <AvGroup>
                <Label id="periodeLabel" for="cours-periode">
                  <Translate contentKey="insApplicationApp.cours.periode">Periode</Translate>
                </Label>
                <AvInput
                  id="cours-periode"
                  data-cy="periode"
                  type="select"
                  className="form-control"
                  name="periode"
                  value={(!isNew && coursEntity.periode) || 'HEBDOMADAIRE'}
                >
                  <option value="HEBDOMADAIRE">{translate('insApplicationApp.PERIODICITE.HEBDOMADAIRE')}</option>
                  <option value="MENSUEL">{translate('insApplicationApp.PERIODICITE.MENSUEL')}</option>
                  <option value="TRIMESTRIEL">{translate('insApplicationApp.PERIODICITE.TRIMESTRIEL')}</option>
                  <option value="PONCTUEL">{translate('insApplicationApp.PERIODICITE.PONCTUEL')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="frequenceLabel" for="cours-frequence">
                  <Translate contentKey="insApplicationApp.cours.frequence">Frequence</Translate>
                </Label>
                <AvField id="cours-frequence" data-cy="frequence" type="string" className="form-control" name="frequence" />
              </AvGroup>
              <AvGroup>
                <Label id="agiminrecLabel" for="cours-agiminrec">
                  <Translate contentKey="insApplicationApp.cours.agiminrec">Agiminrec</Translate>
                </Label>
                <AvField id="cours-agiminrec" data-cy="agiminrec" type="string" className="form-control" name="agiminrec" />
              </AvGroup>
              <AvGroup>
                <Label id="agemaxrecLabel" for="cours-agemaxrec">
                  <Translate contentKey="insApplicationApp.cours.agemaxrec">Agemaxrec</Translate>
                </Label>
                <AvField id="cours-agemaxrec" data-cy="agemaxrec" type="string" className="form-control" name="agemaxrec" />
              </AvGroup>
              <AvGroup>
                <Label for="cours-souscategory">
                  <Translate contentKey="insApplicationApp.cours.souscategory">Souscategory</Translate>
                </Label>
                <AvInput id="cours-souscategory" data-cy="souscategory" type="select" className="form-control" name="souscategoryId">
                  <option value="" key="0" />
                  {sousCategories
                    ? sousCategories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.libille}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/cours" replace color="info">
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
  sousCategories: storeState.sousCategory.entities,
  coursEntity: storeState.cours.entity,
  loading: storeState.cours.loading,
  updating: storeState.cours.updating,
  updateSuccess: storeState.cours.updateSuccess,
});

const mapDispatchToProps = {
  getSousCategories,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CoursUpdate);
