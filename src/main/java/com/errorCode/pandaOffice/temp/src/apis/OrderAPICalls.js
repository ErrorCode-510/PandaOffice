import {authRequest} from "./api";
import {toast} from "react-toastify";
import {getOrders, success} from "../modules/OrderModules";
import {getMemberId} from "../utils/TokenUtils";

export const callOrderRegistAPI = ({registForm}) => {
    return async (dispatch, getState) => {
        const result = await authRequest.post(
            `/api/v1/orders`,
            JSON.stringify(registForm),
            { headers : { 'Content-Type' : 'application/json' }}
        ).catch(e => {
           if(e.response.status === 409) {
               toast.error('재고 부족으로 상품 구매가 불가합니다.');
           }
        });

        console.log('callOrderRegistAPI result : ', result);

        if(result?.status === 201) {
            dispatch(success());
        }

    }
}

export const callOrdersAPI = ({currentPage}) => {

    return async (dispatch, getState) => {
        const result = await authRequest.get(
            `/api/v1/members/${getMemberId()}/orders?page=${currentPage}`
        );

        console.log('callOrdersAPI result : ', result);

        if(result?.status === 200) {
            dispatch(getOrders(result));
        }

    }
}


















