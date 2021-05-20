import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICours, defaultValue } from 'app/shared/model/cours.model';

export const ACTION_TYPES = {
  FETCH_COURS_LIST: 'cours/FETCH_COURS_LIST',
  FETCH_COURS: 'cours/FETCH_COURS',
  CREATE_COURS: 'cours/CREATE_COURS',
  UPDATE_COURS: 'cours/UPDATE_COURS',
  PARTIAL_UPDATE_COURS: 'cours/PARTIAL_UPDATE_COURS',
  DELETE_COURS: 'cours/DELETE_COURS',
  RESET: 'cours/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICours>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CoursState = Readonly<typeof initialState>;

// Reducer

export default (state: CoursState = initialState, action): CoursState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COURS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COURS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COURS):
    case REQUEST(ACTION_TYPES.UPDATE_COURS):
    case REQUEST(ACTION_TYPES.DELETE_COURS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COURS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COURS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COURS):
    case FAILURE(ACTION_TYPES.CREATE_COURS):
    case FAILURE(ACTION_TYPES.UPDATE_COURS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COURS):
    case FAILURE(ACTION_TYPES.DELETE_COURS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COURS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COURS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COURS):
    case SUCCESS(ACTION_TYPES.UPDATE_COURS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COURS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COURS):
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

const apiUrl = 'api/cours';

// Actions

export const getEntities: ICrudGetAllAction<ICours> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COURS_LIST,
  payload: axios.get<ICours>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICours> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COURS,
    payload: axios.get<ICours>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICours> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COURS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICours> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COURS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICours> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COURS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICours> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COURS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
