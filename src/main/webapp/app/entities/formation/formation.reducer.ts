import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFormation, defaultValue } from 'app/shared/model/formation.model';

export const ACTION_TYPES = {
  FETCH_FORMATION_LIST: 'formation/FETCH_FORMATION_LIST',
  FETCH_FORMATION: 'formation/FETCH_FORMATION',
  CREATE_FORMATION: 'formation/CREATE_FORMATION',
  UPDATE_FORMATION: 'formation/UPDATE_FORMATION',
  PARTIAL_UPDATE_FORMATION: 'formation/PARTIAL_UPDATE_FORMATION',
  DELETE_FORMATION: 'formation/DELETE_FORMATION',
  RESET: 'formation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFormation>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type FormationState = Readonly<typeof initialState>;

// Reducer

export default (state: FormationState = initialState, action): FormationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FORMATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FORMATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FORMATION):
    case REQUEST(ACTION_TYPES.UPDATE_FORMATION):
    case REQUEST(ACTION_TYPES.DELETE_FORMATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_FORMATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FORMATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FORMATION):
    case FAILURE(ACTION_TYPES.CREATE_FORMATION):
    case FAILURE(ACTION_TYPES.UPDATE_FORMATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_FORMATION):
    case FAILURE(ACTION_TYPES.DELETE_FORMATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FORMATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FORMATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FORMATION):
    case SUCCESS(ACTION_TYPES.UPDATE_FORMATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_FORMATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FORMATION):
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

const apiUrl = 'api/formations';

// Actions

export const getEntities: ICrudGetAllAction<IFormation> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FORMATION_LIST,
  payload: axios.get<IFormation>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IFormation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FORMATION,
    payload: axios.get<IFormation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFormation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FORMATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFormation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FORMATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IFormation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_FORMATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFormation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FORMATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
