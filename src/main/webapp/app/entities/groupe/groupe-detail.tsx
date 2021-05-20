import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './groupe.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGroupeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GroupeDetail = (props: IGroupeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { groupeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="groupeDetailsHeading">
          <Translate contentKey="insApplicationApp.groupe.detail.title">Groupe</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{groupeEntity.id}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="insApplicationApp.groupe.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{groupeEntity.numero}</dd>
          <dt>
            <span id="libille">
              <Translate contentKey="insApplicationApp.groupe.libille">Libille</Translate>
            </span>
          </dt>
          <dd>{groupeEntity.libille}</dd>
          <dt>
            <span id="lasession">
              <Translate contentKey="insApplicationApp.groupe.lasession">Lasession</Translate>
            </span>
          </dt>
          <dd>{groupeEntity.lasession}</dd>
          <dt>
            <span id="nbrApprenant">
              <Translate contentKey="insApplicationApp.groupe.nbrApprenant">Nbr Apprenant</Translate>
            </span>
          </dt>
          <dd>{groupeEntity.nbrApprenant}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.groupe.cours">Cours</Translate>
          </dt>
          <dd>{groupeEntity.cours ? groupeEntity.cours.id : ''}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.groupe.enfant">Enfant</Translate>
          </dt>
          <dd>
            {groupeEntity.enfants
              ? groupeEntity.enfants.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {groupeEntity.enfants && i === groupeEntity.enfants.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/groupe" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/groupe/${groupeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ groupe }: IRootState) => ({
  groupeEntity: groupe.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GroupeDetail);
