import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './sous-category.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISousCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SousCategoryDetail = (props: ISousCategoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { sousCategoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sousCategoryDetailsHeading">
          <Translate contentKey="insApplicationApp.sousCategory.detail.title">SousCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sousCategoryEntity.id}</dd>
          <dt>
            <span id="libille">
              <Translate contentKey="insApplicationApp.sousCategory.libille">Libille</Translate>
            </span>
          </dt>
          <dd>{sousCategoryEntity.libille}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="insApplicationApp.sousCategory.description">Description</Translate>
            </span>
          </dt>
          <dd>{sousCategoryEntity.description}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.sousCategory.category">Category</Translate>
          </dt>
          <dd>{sousCategoryEntity.category ? sousCategoryEntity.category.libile : ''}</dd>
        </dl>
        <Button tag={Link} to="/sous-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sous-category/${sousCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ sousCategory }: IRootState) => ({
  sousCategoryEntity: sousCategory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SousCategoryDetail);
