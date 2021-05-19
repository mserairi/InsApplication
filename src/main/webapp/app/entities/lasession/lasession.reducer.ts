import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILasession, defaultValue } from 'app/shared/model/lasession.model';

export const ACTION_TYPES = {
  FETCH_LASESSION_LIST: 'lasession/FETCH_LASESSION_LIST',
  FETCH_LASESSION: 'lasession/FETCH_LASESSION',
  CREATE_LASESSION: 'lasession/CREATE_LASESSION',
  UPDATE_LASESSION: 'lasession/UPDATE_LASESSION',
  PARTIAL_UPDATE_LASESSION: 'lasession/PARTIAL_UPDATE_LASESSION',
  DELETE_LASESSION: 'lasession/DELETE_LASESSION',
  RESET: 'lasession/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILasession>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type LasessionState = Readonly<typeof initialState>;

// Reducer

export default (state: LasessionState = initialState, action): LasessionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LASESSION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LASESSION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LASESSION):
    case REQUEST(ACTION_TYPES.UPDATE_LASESSION):
    case REQUEST(ACTION_TYPES.DELETE_LASESSION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_LASESSION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_LASESSION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LASESSION):
    case FAILURE(ACTION_TYPES.CREATE_LASESSION):
    case FAILURE(ACTION_TYPES.UPDATE_LASESSION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_LASESSION):
    case FAILURE(ACTION_TYPES.DELETE_LASESSION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LASESSION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LASESSION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LASESSION):
    case SUCCESS(ACTION_TYPES.UPDATE_LASESSION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_LASESSION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LASESSION):
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

const apiUrl = 'api/lasessions';

// Actions

export const getEntities: ICrudGetAllAction<ILasession> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LASESSION_LIST,
  payload: axios.get<ILasession>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ILasession> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LASESSION,
    payload: axios.get<ILasession>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ILasession> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LASESSION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILasession> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LASESSION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ILasession> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_LASESSION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILasession> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LASESSION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
