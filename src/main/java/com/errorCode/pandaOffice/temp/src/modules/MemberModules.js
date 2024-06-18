import {createActions, handleActions} from "redux-actions";

/* 초기값 */
const initialState = {};

/* 액션 타입 */
const SUCCESS = 'member/SUCCESS';
const RESET = 'member/RESET';
const GET_PROFILE = 'member/GET_PROFILE';

/* 액션 함수 */
export const { member : { success, reset, getProfile }} = createActions({
    [SUCCESS] : () => ({ success : true }),
    [RESET] : () => {},
    [GET_PROFILE] : (result) => ({ profileInfo : result.data })
});

/* 리듀서 함수 */
const memberReducer = handleActions({
    [SUCCESS] : (state, { payload }) => payload,
    [RESET] : () => initialState,
    [GET_PROFILE] : (state, { payload }) => payload
}, initialState);

export default memberReducer;






