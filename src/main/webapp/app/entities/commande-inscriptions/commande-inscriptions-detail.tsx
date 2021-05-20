import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './commande-inscriptions.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICommandeInscriptionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommandeInscriptionsDetail = (props: ICommandeInscriptionsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { commandeInscriptionsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commandeInscriptionsDetailsHeading">
          <Translate contentKey="insApplicationApp.commandeInscriptions.detail.title">CommandeInscriptions</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{commandeInscriptionsEntity.id}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="insApplicationApp.commandeInscriptions.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{commandeInscriptionsEntity.numero}</dd>
          <dt>
            <span id="totalAvantRemise">
              <Translate contentKey="insApplicationApp.commandeInscriptions.totalAvantRemise">Total Avant Remise</Translate>
            </span>
          </dt>
          <dd>{commandeInscriptionsEntity.totalAvantRemise}</dd>
          <dt>
            <span id="totalRemise">
              <Translate contentKey="insApplicationApp.commandeInscriptions.totalRemise">Total Remise</Translate>
            </span>
          </dt>
          <dd>{commandeInscriptionsEntity.totalRemise}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="insApplicationApp.commandeInscriptions.status">Status</Translate>
            </span>
          </dt>
          <dd>{commandeInscriptionsEntity.status}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.commandeInscriptions.facture">Facture</Translate>
          </dt>
          <dd>{commandeInscriptionsEntity.facture ? commandeInscriptionsEntity.facture.numero : ''}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.commandeInscriptions.inscription">Inscription</Translate>
          </dt>
          <dd>{commandeInscriptionsEntity.inscription ? commandeInscriptionsEntity.inscription.id : ''}</dd>
          <dt>
            <Translate contentKey="insApplicationApp.commandeInscriptions.remise">Remise</Translate>
          </dt>
          <dd>{commandeInscriptionsEntity.remise ? commandeInscriptionsEntity.remise.numero : ''}</dd>
        </dl>
        <Button tag={Link} to="/commande-inscriptions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/commande-inscriptions/${commandeInscriptionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ commandeInscriptions }: IRootState) => ({
  commandeInscriptionsEntity: commandeInscriptions.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommandeInscriptionsDetail);
