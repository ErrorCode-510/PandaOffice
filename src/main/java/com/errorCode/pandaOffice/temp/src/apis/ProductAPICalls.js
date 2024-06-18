import {authRequest, request} from "./api";
import {getProduct, getProducts, success} from "../modules/ProductModules";

export const callProductListAPI = ({ currentPage = 1 }) => {
    return async (dispatch, getState) => {
        try {
            const result = await request('GET', `/api/v1/products?page=${currentPage}`);
            console.log('callProductListAPI result : ', result);

            if (result && result.status === 200) {
                dispatch(getProducts(result));
            } else {
                console.error('API call failed with status:', result ? result.status : 'undefined result');
            }
        } catch (error) {
            console.error('Error occurred during API call:', error);
        }
    };
};
export const callProductCategoryListAPI = ({ categoryCode, currentPage = 1 }) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/api/v1/products?categoryCode=${categoryCode}&page=${currentPage}`);
        console.log('callProductCategoryListAPI result : ', result);

        if(result.status === 200) {
            dispatch(getProducts(result));
        }

    }
};

export const callProductSearchListAPI = ({ productName, currentPage = 1 }) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/api/v1/products?productName=${productName}&page=${currentPage}`);
        console.log('callProductSearchListAPI result : ', result);

        if(result.status === 200) {
            dispatch(getProducts(result));
        }

    }
};

export const callProductDetailAPI = ({ productCode }) => {

    return async (dispatch, getState) => {

        const result = await request('GET', `/api/v1/products/${productCode}`);
        console.log('callProductDetailAPI result : ', result);

        if(result.status === 200) {
            dispatch(getProduct(result));
        }

    }
};

export const callAdminProductListAPI = ({ currentPage = 1}) => {

    return async (dispatch, getState) => {
        const result = await authRequest.get(`/api/v1/products-management?page=${currentPage}`);
        console.log('callAdminProductListAPI result : ',result);

        if(result.status === 200) {
            dispatch(getProducts(result));
        }

    }
};

export const callAdminProductRegistAPI = ({ registRequest }) => {

    return async (dispatch, getState) => {
        const result = await authRequest.post(`/api/v1/products`, registRequest);
        console.log('callAdminProductRegistAPI result : ',result);

        if(result.status === 201) {
            dispatch(success());
        }
    }
};

export const callAdminProductAPI = ({ productCode }) => {

    return async (dispatch, getState) => {
        const result = await authRequest.get(`/api/v1/products-management/${productCode}`);
        console.log('callAdminProductAPI result : ',result);

        if(result.status === 200) {
            dispatch(getProduct(result));
        }
    }
};

export const callAdminProductModifyAPI = ({ productCode, modifyRequest }) => {

    return async (dispatch, getState) => {
        const result = await authRequest.put(`/api/v1/products/${productCode}`, modifyRequest);
        console.log('callAdminProductModifyAPI result : ',result);

        if(result.status === 201) {
            dispatch(success());
        }
    }
};

export const callAdminProductRemoveAPI = ({ productCode }) => {

    return async (dispatch, getState) => {
        const result = await authRequest.delete(`/api/v1/products/${productCode}`);
        console.log('callAdminProductRemoveAPI result : ',result);

        if(result.status === 204) {
            dispatch(success());
        }
    }
};