import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cours.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICoursDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CoursDetail = (props: ICoursDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { coursEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="coursDetailsHeading">
          <Translate contentKey="insApplicationApp.cours.detail.title">Cours</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{coursEntity.id}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="insApplicationApp.cours.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{coursEntity.numero}</dd>
          <dt>
            <span id="libille">
              <Translate contentKey="insApplicationApp.cours.libille">Libille</Translate>
            </span>
          </dt>
          <dd>{coursEntity.libille}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="insApplicationApp.cours.description">Description</Translate>
            </span>
          </dt>
          <dd>{coursEntity.description}</dd>
          <dt>
            <span id="seuil">
              <Translate contentKey="insApplicationApp.cours.seuil">Seuil</Translate>
            </span>
          </dt>
          <dd>{coursEntity.seuil}</dd>
          <dt>
            <span id="duree">
              <Translate contentKey="insApplicationApp.cours.duree">Duree</Translate>
            </span>
          </dt>
          <dd>{coursEntity.duree}</dd>
          <dt>
            <span id="periode">
              <Translate contentKey="insApplicationApp.cours.periode">Periode</Translate>
            </span>
          </dt>
          <dd>{coursEntity.periode}</dd>
          <dt>
            <span id="frequence">
              <Translate contentKey="insApplicationApp.cours.frequence">Frequence</Translate>
            </span>
          </dt>
          <dd>{coursEntity.frequence}</dd>
          <dt>
            <span id="agiminrec">
              <Translate contentKey="insApplicationApp.cours.agiminrec">Agiminrec</Translate>
            </span>
          </dt>
          <dd>{coursEntity.agiminrec}</dd>
          <dt>
            <span id="agemaxrec">
              <Translate contentKey="insApplicationApp.cours.agemaxrec">Agemaxrec</Translate>
            </span>
          </dt>
          <dd>{coursEntity.agemaxrec}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.cours.souscategory">Souscategory</Translate>
          </dt>
          <dd>{coursEntity.souscategory ? coursEntity.souscategory.libille : ''}</dd>
        </dl>
        <Button tag={Link} to="/cours" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cours/${coursEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cours }: IRootState) => ({
  coursEntity: cours.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CoursDetail);
