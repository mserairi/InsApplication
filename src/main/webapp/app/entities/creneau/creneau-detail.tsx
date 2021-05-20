import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './creneau.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICreneauDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CreneauDetail = (props: ICreneauDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { creneauEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="creneauDetailsHeading">
          <Translate contentKey="insApplicationApp.creneau.detail.title">Creneau</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.id}</dd>
          <dt>
            <span id="typeCreneau">
              <Translate contentKey="insApplicationApp.creneau.typeCreneau">Type Creneau</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.typeCreneau}</dd>
          <dt>
            <span id="jour">
              <Translate contentKey="insApplicationApp.creneau.jour">Jour</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.jour}</dd>
          <dt>
            <span id="deb">
              <Translate contentKey="insApplicationApp.creneau.deb">Deb</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.deb ? <TextFormat value={creneauEntity.deb} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="fin">
              <Translate contentKey="insApplicationApp.creneau.fin">Fin</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.fin ? <TextFormat value={creneauEntity.fin} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.creneau.salle">Salle</Translate>
          </dt>
          <dd>{creneauEntity.salle ? creneauEntity.salle.code : ''}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.creneau.groupe">Groupe</Translate>
          </dt>
          <dd>{creneauEntity.groupe ? creneauEntity.groupe.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/creneau" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/creneau/${creneauEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ creneau }: IRootState) => ({
  creneauEntity: creneau.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CreneauDetail);
