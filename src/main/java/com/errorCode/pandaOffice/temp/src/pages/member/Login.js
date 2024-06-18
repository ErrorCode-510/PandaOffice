import {useNavigate} from "react-router-dom";
import {ToastContainer} from "react-toastify";
import LoginForm from "../../components/form/LoginForm";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {reset} from "../../modules/MemberModules";

function Login () {
    const navigate = useNavigate();
    const { success } = useSelector(state => state.memberReducer);
    const dispatch = useDispatch();

    useEffect(() => {
        if(success === true) {
            navigate('/');
            dispatch(reset());
        }
    }, [success]);

    const onClickSignupHandler = () => {
        navigate('/member/signup');
    }

    return (
        <>
            <ToastContainer hideProgressBar={true} position="top-center"/>
            <div className="background-div">
                <div className="login-div">
                    <LoginForm/>
                    <button
                        onClick={ onClickSignupHandler }
                    >
                        회원가입
                    </button>
                </div>
            </div>
        </>
    );
}

export default Login;
