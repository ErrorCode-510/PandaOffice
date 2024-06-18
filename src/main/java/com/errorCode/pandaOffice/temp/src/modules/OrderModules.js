import {createActions, handleActions} from "redux-actions";

const initialState = {};

const SUCCESS = 'order/SUCCESS';
const GET_ORDERS = 'order/GET_ORDERS';

export const { order : { success, getOrders } } = createActions({
    [SUCCESS] : () => ({ success : true }),
    [GET_ORDERS] : (result) => ({ orders : result.data })
});

const orderReducer = handleActions({
    [SUCCESS] : (state, { payload }) => payload,
    [GET_ORDERS] : (state, { payload }) => payload,
}, initialState);

export default orderReducer;