import {createActions, handleActions} from "redux-actions";

/* 초기값 */
const initialState = {};

/* 액션 타입 */
const GET_REVIEWS = 'review/GET_REVIEWS';
const SUCCESS = 'review/SUCCESS';

/* 액션 함수 */
export const { review : { getReviews, success } } = createActions({
    [GET_REVIEWS] : result => ({ reviews : result.data }),
    [SUCCESS] : () => ({ success: true }),
});

/* 리듀서 */
const reviewReducer = handleActions({
    [GET_REVIEWS] : (state, { payload }) => payload,
    [SUCCESS] : (state, { payload }) => payload,
}, initialState);

export default reviewReducer;