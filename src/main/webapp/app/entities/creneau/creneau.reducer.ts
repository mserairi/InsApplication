import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICreneau, defaultValue } from 'app/shared/model/creneau.model';

export const ACTION_TYPES = {
  FETCH_CRENEAU_LIST: 'creneau/FETCH_CRENEAU_LIST',
  FETCH_CRENEAU: 'creneau/FETCH_CRENEAU',
  CREATE_CRENEAU: 'creneau/CREATE_CRENEAU',
  UPDATE_CRENEAU: 'creneau/UPDATE_CRENEAU',
  PARTIAL_UPDATE_CRENEAU: 'creneau/PARTIAL_UPDATE_CRENEAU',
  DELETE_CRENEAU: 'creneau/DELETE_CRENEAU',
  RESET: 'creneau/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICreneau>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CreneauState = Readonly<typeof initialState>;

// Reducer

export default (state: CreneauState = initialState, action): CreneauState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CRENEAU_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CRENEAU):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CRENEAU):
    case REQUEST(ACTION_TYPES.UPDATE_CRENEAU):
    case REQUEST(ACTION_TYPES.DELETE_CRENEAU):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CRENEAU):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CRENEAU_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CRENEAU):
    case FAILURE(ACTION_TYPES.CREATE_CRENEAU):
    case FAILURE(ACTION_TYPES.UPDATE_CRENEAU):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CRENEAU):
    case FAILURE(ACTION_TYPES.DELETE_CRENEAU):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CRENEAU_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CRENEAU):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CRENEAU):
    case SUCCESS(ACTION_TYPES.UPDATE_CRENEAU):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CRENEAU):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CRENEAU):
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

const apiUrl = 'api/creneaus';

// Actions

export const getEntities: ICrudGetAllAction<ICreneau> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CRENEAU_LIST,
  payload: axios.get<ICreneau>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICreneau> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CRENEAU,
    payload: axios.get<ICreneau>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICreneau> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CRENEAU,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICreneau> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CRENEAU,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICreneau> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CRENEAU,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICreneau> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CRENEAU,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
