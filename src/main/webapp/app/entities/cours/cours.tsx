import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './cours.reducer';
import { ICours } from 'app/shared/model/cours.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICoursProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Cours = (props: ICoursProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { coursList, match, loading } = props;
  return (
    <div>
      <h2 id="cours-heading" data-cy="CoursHeading">
        <Translate contentKey="insApplicationApp.cours.home.title">Cours</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="insApplicationApp.cours.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="insApplicationApp.cours.home.createLabel">Create new Cours</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {coursList && coursList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="insApplicationApp.cours.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.libille">Libille</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.seuil">Seuil</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.duree">Duree</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.periode">Periode</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.frequence">Frequence</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.agiminrec">Agiminrec</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.agemaxrec">Agemaxrec</Translate>
                </th>
                <th>
                  <Translate contentKey="insApplicationApp.cours.souscategory">Souscategory</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {coursList.map((cours, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${cours.id}`} color="link" size="sm">
                      {cours.id}
                    </Button>
                  </td>
                  <td>{cours.numero}</td>
                  <td>{cours.libille}</td>
                  <td>{cours.description}</td>
                  <td>{cours.seuil}</td>
                  <td>{cours.duree}</td>
                  <td>
                    <Translate contentKey={`insApplicationApp.PERIODICITE.${cours.periode}`} />
                  </td>
                  <td>{cours.frequence}</td>
                  <td>{cours.agiminrec}</td>
                  <td>{cours.agemaxrec}</td>
                  <td>
                    {cours.souscategory ? <Link to={`sous-category/${cours.souscategory.id}`}>{cours.souscategory.libille}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${cours.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${cours.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${cours.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="insApplicationApp.cours.home.notFound">No Cours found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ cours }: IRootState) => ({
  coursList: cours.entities,
  loading: cours.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Cours);
