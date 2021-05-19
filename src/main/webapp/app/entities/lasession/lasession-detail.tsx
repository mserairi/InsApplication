import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './lasession.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILasessionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LasessionDetail = (props: ILasessionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { lasessionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lasessionDetailsHeading">
          <Translate contentKey="insApplicationApp.lasession.detail.title">Lasession</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lasessionEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="insApplicationApp.lasession.code">Code</Translate>
            </span>
          </dt>
          <dd>{lasessionEntity.code}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="insApplicationApp.lasession.description">Description</Translate>
            </span>
          </dt>
          <dd>{lasessionEntity.description}</dd>
          <dt>
            <span id="tarif">
              <Translate contentKey="insApplicationApp.lasession.tarif">Tarif</Translate>
            </span>
          </dt>
          <dd>{lasessionEntity.tarif}</dd>
          <dt>
            <span id="debut">
              <Translate contentKey="insApplicationApp.lasession.debut">Debut</Translate>
            </span>
          </dt>
          <dd>{lasessionEntity.debut ? <TextFormat value={lasessionEntity.debut} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="fin">
              <Translate contentKey="insApplicationApp.lasession.fin">Fin</Translate>
            </span>
          </dt>
          <dd>{lasessionEntity.fin ? <TextFormat value={lasessionEntity.fin} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.lasession.category">Category</Translate>
          </dt>
          <dd>{lasessionEntity.category ? lasessionEntity.category.libile : ''}</dd>
        </dl>
        <Button tag={Link} to="/lasession" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lasession/${lasessionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ lasession }: IRootState) => ({
  lasessionEntity: lasession.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LasessionDetail);
