import {isAdmin, isLogin} from "../../utils/TokenUtils";
import {Navigate} from "react-router-dom";

function ProtectedRoute({loginCheck, authCheck, children}) {

    if(authCheck) {
        /* 권한이 있어야 접근 가능한 기능 (상품 관리) */
        return isAdmin() ? children : <Navigate to="/"/>
    }

    if(loginCheck) {
        /* 로그인 해야만 볼 수 있는 컴포넌트 (EX. 마이페이지) */
        return isLogin() ? children : <Navigate to="/member/login"/>
    } else {
        /* 로그인 하면 볼 수 없는 컴포넌트 (EX. 로그인, 회원가입) */
        return !isLogin() ? children : <Navigate to="/"/>
    }

}

export default ProtectedRoute;