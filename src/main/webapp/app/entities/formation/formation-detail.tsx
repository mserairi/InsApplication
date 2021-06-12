import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './formation.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFormationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FormationDetail = (props: IFormationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { formationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formationDetailsHeading">
          <Translate contentKey="insApplicationApp.formation.detail.title">Formation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{formationEntity.id}</dd>
          <dt>
            <span id="libille">
              <Translate contentKey="insApplicationApp.formation.libille">Libille</Translate>
            </span>
          </dt>
          <dd>{formationEntity.libille}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="insApplicationApp.formation.description">Description</Translate>
            </span>
          </dt>
          <dd>{formationEntity.description}</dd>
          <dt>
            <span id="ouvertureInscription">
              <Translate contentKey="insApplicationApp.formation.ouvertureInscription">Ouverture Inscription</Translate>
            </span>
          </dt>
          <dd>
            {formationEntity.ouvertureInscription ? (
              <TextFormat value={formationEntity.ouvertureInscription} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fermetureInscription">
              <Translate contentKey="insApplicationApp.formation.fermetureInscription">Fermeture Inscription</Translate>
            </span>
          </dt>
          <dd>
            {formationEntity.fermetureInscription ? (
              <TextFormat value={formationEntity.fermetureInscription} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="seuilInscrits">
              <Translate contentKey="insApplicationApp.formation.seuilInscrits">Seuil Inscrits</Translate>
            </span>
          </dt>
          <dd>{formationEntity.seuilInscrits}</dd>
          <dt>
            <span id="tarif">
              <Translate contentKey="insApplicationApp.formation.tarif">Tarif</Translate>
            </span>
          </dt>
          <dd>{formationEntity.tarif}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.formation.category">Category</Translate>
          </dt>
          <dd>{formationEntity.category ? formationEntity.category.libille : ''}</dd>
        </dl>
        <Button tag={Link} to="/formation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/formation/${formationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ formation }: IRootState) => ({
  formationEntity: formation.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FormationDetail);
