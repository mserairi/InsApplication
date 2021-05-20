import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGroupe, defaultValue } from 'app/shared/model/groupe.model';

export const ACTION_TYPES = {
  FETCH_GROUPE_LIST: 'groupe/FETCH_GROUPE_LIST',
  FETCH_GROUPE: 'groupe/FETCH_GROUPE',
  CREATE_GROUPE: 'groupe/CREATE_GROUPE',
  UPDATE_GROUPE: 'groupe/UPDATE_GROUPE',
  PARTIAL_UPDATE_GROUPE: 'groupe/PARTIAL_UPDATE_GROUPE',
  DELETE_GROUPE: 'groupe/DELETE_GROUPE',
  RESET: 'groupe/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGroupe>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GroupeState = Readonly<typeof initialState>;

// Reducer

export default (state: GroupeState = initialState, action): GroupeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GROUPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GROUPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GROUPE):
    case REQUEST(ACTION_TYPES.UPDATE_GROUPE):
    case REQUEST(ACTION_TYPES.DELETE_GROUPE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_GROUPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GROUPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GROUPE):
    case FAILURE(ACTION_TYPES.CREATE_GROUPE):
    case FAILURE(ACTION_TYPES.UPDATE_GROUPE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_GROUPE):
    case FAILURE(ACTION_TYPES.DELETE_GROUPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GROUPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GROUPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GROUPE):
    case SUCCESS(ACTION_TYPES.UPDATE_GROUPE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_GROUPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GROUPE):
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

const apiUrl = 'api/groupes';

// Actions

export const getEntities: ICrudGetAllAction<IGroupe> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GROUPE_LIST,
  payload: axios.get<IGroupe>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGroupe> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GROUPE,
    payload: axios.get<IGroupe>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGroupe> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GROUPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGroupe> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GROUPE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IGroupe> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_GROUPE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGroupe> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GROUPE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
