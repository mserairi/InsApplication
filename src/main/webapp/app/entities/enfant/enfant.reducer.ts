import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEnfant, defaultValue } from 'app/shared/model/enfant.model';

export const ACTION_TYPES = {
  FETCH_ENFANT_LIST: 'enfant/FETCH_ENFANT_LIST',
  FETCH_ENFANT: 'enfant/FETCH_ENFANT',
  CREATE_ENFANT: 'enfant/CREATE_ENFANT',
  UPDATE_ENFANT: 'enfant/UPDATE_ENFANT',
  PARTIAL_UPDATE_ENFANT: 'enfant/PARTIAL_UPDATE_ENFANT',
  DELETE_ENFANT: 'enfant/DELETE_ENFANT',
  RESET: 'enfant/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEnfant>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EnfantState = Readonly<typeof initialState>;

// Reducer

export default (state: EnfantState = initialState, action): EnfantState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ENFANT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ENFANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ENFANT):
    case REQUEST(ACTION_TYPES.UPDATE_ENFANT):
    case REQUEST(ACTION_TYPES.DELETE_ENFANT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ENFANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ENFANT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ENFANT):
    case FAILURE(ACTION_TYPES.CREATE_ENFANT):
    case FAILURE(ACTION_TYPES.UPDATE_ENFANT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ENFANT):
    case FAILURE(ACTION_TYPES.DELETE_ENFANT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENFANT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENFANT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ENFANT):
    case SUCCESS(ACTION_TYPES.UPDATE_ENFANT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ENFANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ENFANT):
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

const apiUrl = 'api/enfants';

// Actions

export const getEntities: ICrudGetAllAction<IEnfant> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ENFANT_LIST,
  payload: axios.get<IEnfant>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEnfant> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ENFANT,
    payload: axios.get<IEnfant>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEnfant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ENFANT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEnfant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ENFANT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEnfant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ENFANT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEnfant> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ENFANT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
