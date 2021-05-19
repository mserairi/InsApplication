import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './enfant.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnfantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnfantDetail = (props: IEnfantDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { enfantEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="enfantDetailsHeading">
          <Translate contentKey="insApplicationApp.enfant.detail.title">Enfant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{enfantEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="insApplicationApp.enfant.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{enfantEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="insApplicationApp.enfant.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{enfantEntity.prenom}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="insApplicationApp.enfant.age">Age</Translate>
            </span>
          </dt>
          <dd>{enfantEntity.age}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.enfant.parent">Parent</Translate>
          </dt>
          <dd>
            {enfantEntity.parents
              ? enfantEntity.parents.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.login}</a>
                    {enfantEntity.parents && i === enfantEntity.parents.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}{' '}
          </dd>
        </dl>
        <Button tag={Link} to="/enfant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/enfant/${enfantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ enfant }: IRootState) => ({
  enfantEntity: enfant.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnfantDetail);
