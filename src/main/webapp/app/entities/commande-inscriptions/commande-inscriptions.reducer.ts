import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICommandeInscriptions, defaultValue } from 'app/shared/model/commande-inscriptions.model';

export const ACTION_TYPES = {
  FETCH_COMMANDEINSCRIPTIONS_LIST: 'commandeInscriptions/FETCH_COMMANDEINSCRIPTIONS_LIST',
  FETCH_COMMANDEINSCRIPTIONS: 'commandeInscriptions/FETCH_COMMANDEINSCRIPTIONS',
  CREATE_COMMANDEINSCRIPTIONS: 'commandeInscriptions/CREATE_COMMANDEINSCRIPTIONS',
  UPDATE_COMMANDEINSCRIPTIONS: 'commandeInscriptions/UPDATE_COMMANDEINSCRIPTIONS',
  PARTIAL_UPDATE_COMMANDEINSCRIPTIONS: 'commandeInscriptions/PARTIAL_UPDATE_COMMANDEINSCRIPTIONS',
  DELETE_COMMANDEINSCRIPTIONS: 'commandeInscriptions/DELETE_COMMANDEINSCRIPTIONS',
  RESET: 'commandeInscriptions/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICommandeInscriptions>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CommandeInscriptionsState = Readonly<typeof initialState>;

// Reducer

export default (state: CommandeInscriptionsState = initialState, action): CommandeInscriptionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMMANDEINSCRIPTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMMANDEINSCRIPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COMMANDEINSCRIPTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_COMMANDEINSCRIPTIONS):
    case REQUEST(ACTION_TYPES.DELETE_COMMANDEINSCRIPTIONS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COMMANDEINSCRIPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COMMANDEINSCRIPTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMMANDEINSCRIPTIONS):
    case FAILURE(ACTION_TYPES.CREATE_COMMANDEINSCRIPTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_COMMANDEINSCRIPTIONS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COMMANDEINSCRIPTIONS):
    case FAILURE(ACTION_TYPES.DELETE_COMMANDEINSCRIPTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMANDEINSCRIPTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMMANDEINSCRIPTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMMANDEINSCRIPTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_COMMANDEINSCRIPTIONS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COMMANDEINSCRIPTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMMANDEINSCRIPTIONS):
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

const apiUrl = 'api/commande-inscriptions';

// Actions

export const getEntities: ICrudGetAllAction<ICommandeInscriptions> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COMMANDEINSCRIPTIONS_LIST,
  payload: axios.get<ICommandeInscriptions>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICommandeInscriptions> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMMANDEINSCRIPTIONS,
    payload: axios.get<ICommandeInscriptions>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICommandeInscriptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMMANDEINSCRIPTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICommandeInscriptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMMANDEINSCRIPTIONS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICommandeInscriptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COMMANDEINSCRIPTIONS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICommandeInscriptions> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMMANDEINSCRIPTIONS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
