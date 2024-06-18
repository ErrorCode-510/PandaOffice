import axios from "axios";
import {getAccessTokenHeader, getRefreshTokenHeader, saveToken} from "../utils/TokenUtils";

const SERVER_IP = `${process.env.REACT_APP_RESTAPI_SERVER_IP}`;
const SERVER_PORT = `${process.env.REACT_APP_RESTAPI_SERVER_PORT}`;
const DEFAULT_URL = `http://${SERVER_IP}:${SERVER_PORT}`;

/* 인증이 필요 없는 (토큰을 전달하지 않아도 되는) 기능 호출 시 사용하는 함수 */
export const request = async (method, url, headers, data) => {
    return await axios({
        method,
        url : `${DEFAULT_URL}${url}`,
        headers,
        data
    }).catch(error => console.log(error));
}

/* 인증이 필요한 (토큰을 전달해야 하는) 기능 호출 시 사용하는 함수 */
export const authRequest = axios.create({
    baseURL : DEFAULT_URL
});

authRequest.interceptors.request.use((config) => {
    config.headers['Access-Token'] = getAccessTokenHeader();
    return config;
});

authRequest.interceptors.response.use(
    /* 첫 번째 인자로 사용 되는 콜백 함수는 정상 수행 시 동작이므로 별도의 동작 없이 진행하도록 한다. */
    (response) => {
        return response;
    },
    /* 두 번째 인자로 사용 되는 콜백 함수는 오류 발생 시 동작이므로 로직을 작성한다. */
    async (error) => {

        console.log("error :", error);

        const {
            config,
            response : { status }
        } = error;

        if(status === 401) {
            const originRequest = config;
            // refresh token 전달하여 토큰 재발급 요청
            const response = await postRefreshToken();

            console.log("response : ", response);

            if(response.status === 200) {
                // 토큰 재발급에 성공했을 때
                saveToken(response.headers);
                // 실패했던 요청을 다시 요청
                originRequest.headers['Access-Token'] = getAccessTokenHeader();
                return axios(originRequest);
            }
        }

        return Promise.reject(error);
    });

/* refresh token 전달하여 토큰 재발급 요청하는 api */
export async function postRefreshToken() {

    return await request(
        'POST',
        '/api/v1/refresh-token',
        { 'Refresh-Token' : getRefreshTokenHeader() }
    );
}