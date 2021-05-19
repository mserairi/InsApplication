import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISousCategory, defaultValue } from 'app/shared/model/sous-category.model';

export const ACTION_TYPES = {
  FETCH_SOUSCATEGORY_LIST: 'sousCategory/FETCH_SOUSCATEGORY_LIST',
  FETCH_SOUSCATEGORY: 'sousCategory/FETCH_SOUSCATEGORY',
  CREATE_SOUSCATEGORY: 'sousCategory/CREATE_SOUSCATEGORY',
  UPDATE_SOUSCATEGORY: 'sousCategory/UPDATE_SOUSCATEGORY',
  PARTIAL_UPDATE_SOUSCATEGORY: 'sousCategory/PARTIAL_UPDATE_SOUSCATEGORY',
  DELETE_SOUSCATEGORY: 'sousCategory/DELETE_SOUSCATEGORY',
  RESET: 'sousCategory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISousCategory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SousCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: SousCategoryState = initialState, action): SousCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SOUSCATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SOUSCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SOUSCATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_SOUSCATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_SOUSCATEGORY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SOUSCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SOUSCATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SOUSCATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_SOUSCATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_SOUSCATEGORY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SOUSCATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_SOUSCATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOUSCATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOUSCATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SOUSCATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_SOUSCATEGORY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SOUSCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SOUSCATEGORY):
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

const apiUrl = 'api/sous-categories';

// Actions

export const getEntities: ICrudGetAllAction<ISousCategory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SOUSCATEGORY_LIST,
  payload: axios.get<ISousCategory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISousCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SOUSCATEGORY,
    payload: axios.get<ISousCategory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISousCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SOUSCATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISousCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SOUSCATEGORY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISousCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SOUSCATEGORY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISousCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SOUSCATEGORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
