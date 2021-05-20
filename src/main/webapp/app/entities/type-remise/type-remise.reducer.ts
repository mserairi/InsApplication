import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITypeRemise, defaultValue } from 'app/shared/model/type-remise.model';

export const ACTION_TYPES = {
  FETCH_TYPEREMISE_LIST: 'typeRemise/FETCH_TYPEREMISE_LIST',
  FETCH_TYPEREMISE: 'typeRemise/FETCH_TYPEREMISE',
  CREATE_TYPEREMISE: 'typeRemise/CREATE_TYPEREMISE',
  UPDATE_TYPEREMISE: 'typeRemise/UPDATE_TYPEREMISE',
  PARTIAL_UPDATE_TYPEREMISE: 'typeRemise/PARTIAL_UPDATE_TYPEREMISE',
  DELETE_TYPEREMISE: 'typeRemise/DELETE_TYPEREMISE',
  RESET: 'typeRemise/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITypeRemise>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TypeRemiseState = Readonly<typeof initialState>;

// Reducer

export default (state: TypeRemiseState = initialState, action): TypeRemiseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TYPEREMISE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TYPEREMISE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TYPEREMISE):
    case REQUEST(ACTION_TYPES.UPDATE_TYPEREMISE):
    case REQUEST(ACTION_TYPES.DELETE_TYPEREMISE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TYPEREMISE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TYPEREMISE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TYPEREMISE):
    case FAILURE(ACTION_TYPES.CREATE_TYPEREMISE):
    case FAILURE(ACTION_TYPES.UPDATE_TYPEREMISE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TYPEREMISE):
    case FAILURE(ACTION_TYPES.DELETE_TYPEREMISE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPEREMISE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPEREMISE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TYPEREMISE):
    case SUCCESS(ACTION_TYPES.UPDATE_TYPEREMISE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TYPEREMISE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TYPEREMISE):
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

const apiUrl = 'api/type-remises';

// Actions

export const getEntities: ICrudGetAllAction<ITypeRemise> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TYPEREMISE_LIST,
  payload: axios.get<ITypeRemise>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITypeRemise> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TYPEREMISE,
    payload: axios.get<ITypeRemise>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITypeRemise> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TYPEREMISE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITypeRemise> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TYPEREMISE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITypeRemise> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TYPEREMISE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITypeRemise> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TYPEREMISE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
