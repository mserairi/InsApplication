import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './type-remise.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITypeRemiseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TypeRemiseDetail = (props: ITypeRemiseDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { typeRemiseEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="typeRemiseDetailsHeading">
          <Translate contentKey="insApplicationApp.typeRemise.detail.title">TypeRemise</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{typeRemiseEntity.id}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="insApplicationApp.typeRemise.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{typeRemiseEntity.numero}</dd>
          <dt>
            <span id="libille">
              <Translate contentKey="insApplicationApp.typeRemise.libille">Libille</Translate>
            </span>
          </dt>
          <dd>{typeRemiseEntity.libille}</dd>
          <dt>
            <span id="condition">
              <Translate contentKey="insApplicationApp.typeRemise.condition">Condition</Translate>
            </span>
          </dt>
          <dd>{typeRemiseEntity.condition}</dd>
          <dt>
            <span id="mantantLibre">
              <Translate contentKey="insApplicationApp.typeRemise.mantantLibre">Mantant Libre</Translate>
            </span>
          </dt>
          <dd>{typeRemiseEntity.mantantLibre ? 'true' : 'false'}</dd>
          <dt>
            <span id="montantUnitaire">
              <Translate contentKey="insApplicationApp.typeRemise.montantUnitaire">Montant Unitaire</Translate>
            </span>
          </dt>
          <dd>{typeRemiseEntity.montantUnitaire}</dd>
        </dl>
        <Button tag={Link} to="/type-remise" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/type-remise/${typeRemiseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ typeRemise }: IRootState) => ({
  typeRemiseEntity: typeRemise.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypeRemiseDetail);
