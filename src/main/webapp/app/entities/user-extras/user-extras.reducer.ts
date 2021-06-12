import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserExtras, defaultValue } from 'app/shared/model/user-extras.model';

export const ACTION_TYPES = {
  FETCH_USEREXTRAS_LIST: 'userExtras/FETCH_USEREXTRAS_LIST',
  FETCH_USEREXTRAS: 'userExtras/FETCH_USEREXTRAS',
  CREATE_USEREXTRAS: 'userExtras/CREATE_USEREXTRAS',
  UPDATE_USEREXTRAS: 'userExtras/UPDATE_USEREXTRAS',
  PARTIAL_UPDATE_USEREXTRAS: 'userExtras/PARTIAL_UPDATE_USEREXTRAS',
  DELETE_USEREXTRAS: 'userExtras/DELETE_USEREXTRAS',
  RESET: 'userExtras/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserExtras>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type UserExtrasState = Readonly<typeof initialState>;

// Reducer

export default (state: UserExtrasState = initialState, action): UserExtrasState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USEREXTRAS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USEREXTRAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USEREXTRAS):
    case REQUEST(ACTION_TYPES.UPDATE_USEREXTRAS):
    case REQUEST(ACTION_TYPES.DELETE_USEREXTRAS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_USEREXTRAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USEREXTRAS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USEREXTRAS):
    case FAILURE(ACTION_TYPES.CREATE_USEREXTRAS):
    case FAILURE(ACTION_TYPES.UPDATE_USEREXTRAS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_USEREXTRAS):
    case FAILURE(ACTION_TYPES.DELETE_USEREXTRAS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USEREXTRAS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USEREXTRAS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USEREXTRAS):
    case SUCCESS(ACTION_TYPES.UPDATE_USEREXTRAS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_USEREXTRAS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USEREXTRAS):
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

const apiUrl = 'api/user-extras';

// Actions

export const getEntities: ICrudGetAllAction<IUserExtras> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USEREXTRAS_LIST,
  payload: axios.get<IUserExtras>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IUserExtras> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USEREXTRAS,
    payload: axios.get<IUserExtras>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserExtras> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USEREXTRAS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserExtras> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USEREXTRAS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IUserExtras> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_USEREXTRAS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserExtras> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USEREXTRAS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
