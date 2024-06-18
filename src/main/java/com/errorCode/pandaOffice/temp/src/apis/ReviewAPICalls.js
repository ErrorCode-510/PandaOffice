import {authRequest, request} from "./api";
import {getReviews, success} from "../modules/ReviewModule";
import {toast} from "react-toastify";

export const callReviewsAPI = ({ productCode, currentPage }) => {

    return async (dispatch, getState) => {

        const result
            = await request('GET', `/api/v1/products/${productCode}/reviews?page=${currentPage}`,
            {
                    'Content-Type' : 'application/json'
            });

        console.log('callReviewsAPI result : ', result);

        if(result?.status === 200) {
            dispatch(getReviews(result));
        }
    }
}

export const callReviewRegistAPI = ({ registRequest }) => {

    return async (dispatch, getState) => {

        const result = await authRequest.post(`/api/v1/products/${registRequest.productCode}/reviews`,
            JSON.stringify(registRequest),
            {
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).catch(e => {
            if(e.response.status === 404) {
                toast.error("리뷰 작성이 불가한 상품입니다. ");
            } else if(e.response.status === 409) {
                toast.error("리뷰가 이미 작성 되어 작성 불가합니다.");
            }
        });

        console.log('callReviewRegistAPI result : ', result);

        if(result?.status === 201) {
            dispatch(success());
        }
    }
}