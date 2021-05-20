import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './salle.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISalleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SalleDetail = (props: ISalleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { salleEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="salleDetailsHeading">
          <Translate contentKey="insApplicationApp.salle.detail.title">Salle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{salleEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="insApplicationApp.salle.code">Code</Translate>
            </span>
          </dt>
          <dd>{salleEntity.code}</dd>
          <dt>
            <span id="batiment">
              <Translate contentKey="insApplicationApp.salle.batiment">Batiment</Translate>
            </span>
          </dt>
          <dd>{salleEntity.batiment}</dd>
          <dt>
            <span id="etage">
              <Translate contentKey="insApplicationApp.salle.etage">Etage</Translate>
            </span>
          </dt>
          <dd>{salleEntity.etage}</dd>
        </dl>
        <Button tag={Link} to="/salle" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/salle/${salleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ salle }: IRootState) => ({
  salleEntity: salle.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SalleDetail);
