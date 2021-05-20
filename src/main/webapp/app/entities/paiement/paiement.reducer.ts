import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPaiement, defaultValue } from 'app/shared/model/paiement.model';

export const ACTION_TYPES = {
  FETCH_PAIEMENT_LIST: 'paiement/FETCH_PAIEMENT_LIST',
  FETCH_PAIEMENT: 'paiement/FETCH_PAIEMENT',
  CREATE_PAIEMENT: 'paiement/CREATE_PAIEMENT',
  UPDATE_PAIEMENT: 'paiement/UPDATE_PAIEMENT',
  PARTIAL_UPDATE_PAIEMENT: 'paiement/PARTIAL_UPDATE_PAIEMENT',
  DELETE_PAIEMENT: 'paiement/DELETE_PAIEMENT',
  RESET: 'paiement/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPaiement>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PaiementState = Readonly<typeof initialState>;

// Reducer

export default (state: PaiementState = initialState, action): PaiementState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAIEMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAIEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PAIEMENT):
    case REQUEST(ACTION_TYPES.UPDATE_PAIEMENT):
    case REQUEST(ACTION_TYPES.DELETE_PAIEMENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PAIEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PAIEMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAIEMENT):
    case FAILURE(ACTION_TYPES.CREATE_PAIEMENT):
    case FAILURE(ACTION_TYPES.UPDATE_PAIEMENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PAIEMENT):
    case FAILURE(ACTION_TYPES.DELETE_PAIEMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAIEMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAIEMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAIEMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_PAIEMENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PAIEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAIEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/paiements';

// Actions

export const getEntities: ICrudGetAllAction<IPaiement> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PAIEMENT_LIST,
  payload: axios.get<IPaiement>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPaiement> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAIEMENT,
    payload: axios.get<IPaiement>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPaiement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAIEMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPaiement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAIEMENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPaiement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PAIEMENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPaiement> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAIEMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
