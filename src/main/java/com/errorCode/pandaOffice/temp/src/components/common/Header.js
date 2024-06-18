import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {isLogin} from "../../utils/TokenUtils";
import {useDispatch, useSelector} from "react-redux";
import {callLogoutAPI} from "../../apis/MemberAPICalls";

function Header() {

    const navigate = useNavigate();
    const [search, setSearch] = useState('');
    const dispatch = useDispatch();

    /* 로고 클릭 시 메인 페이지로 이동 */
    const onClickHandler = () => navigate("/");
    const onEnterKeyHandler = e => {
        if(e.key === 'Enter') navigate(`/product/search?value=${search}`);
    }

    function BeforeLogin() {
        return (
            <div>
                <button
                    className="header-btn"
                    onClick={ () => navigate(`/member/login`) }
                >
                    로그인
                </button>
                |
                <button
                    className="header-btn"
                    onClick={ () => navigate(`/member/signup`) }
                >
                    회원가입
                </button>
            </div>
        );
    }

    function AfterLogin() {

        const { success } = useSelector(state => state.memberReducer);

        useEffect(() => {
            if(success === true) {
                window.location.replace('/');
            }
        }, [success]);

        return (
            <div>
                <button
                    className="header-btn"
                    onClick={ () => navigate('/member/mypage') }
                >
                    마이페이지
                </button>
                |
                <button
                    className="header-btn"
                    onClick={ () => dispatch(callLogoutAPI()) }
                >
                    로그아웃
                </button>
            </div>
        );
    }

    return (
        <div className="header-div">
            <button
                className="logo-btn"
                onClick={ onClickHandler }
            >
                OHGIRAFFERS
            </button>
            <input
                className="input-style"
                type="text"
                placeholder="검색"
                onChange={ e => setSearch(e.target.value) }
                onKeyUp={ onEnterKeyHandler }
                value={ search }
            />
            { isLogin() ? <AfterLogin/> : <BeforeLogin/> }
        </div>
    );
}

export default Header;