import {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {callOrderRegistAPI} from "../../apis/OrderAPICalls";
import {ToastContainer} from "react-toastify";

function Order() {

    const navigate = useNavigate();
    const location = useLocation();
    const dispatch = useDispatch();
    const { product, amount } = { ...location.state };
    const [form, setForm] = useState({
        productCode : product.productCode,
        orderAmount : amount
    });
    const { success } = useSelector(state => state.orderReducer);

    useEffect(() => {
        if(success === true) navigate('/member/mypage/payment');
    }, [success]);

    const onChangeHandler = e => {
        setForm({
            ...form,
            [e.target.name] : e.target.value
        });
    }

    const onClickPurchaseHandler = () => {
        dispatch(callOrderRegistAPI({ registForm : form }));
    };


    return (
        <>
            <ToastContainer hideProgressBar={true} position="top-center"/>
            <div className="purchase-div">
                <div className="purchase-info-div">
                    <h3>주문자 정보</h3>
                    <input
                        name="orderPhone"
                        placeholder="핸드폰번호"
                        autoComplete="off"
                        onChange={onChangeHandler}
                        className="purchase-input"
                    />
                    <input
                        placeholder="이메일주소"
                        name="orderEmail"
                        autoComplete="off"
                        onChange={onChangeHandler}
                        className="purchase-input"
                    />
                    <h3>배송 정보</h3>
                    <input
                        placeholder="받는사람"
                        name="orderReceiver"
                        autoComplete="off"
                        onChange={onChangeHandler}
                        className="purchase-input"
                    />
                    <input
                        placeholder="배송주소"
                        name="orderAddress"
                        autoComplete="off"
                        onChange={onChangeHandler}
                        className="purchase-input"
                    />
                </div>
                <div className="purchase-info-div">
                    <h3>결제 정보</h3>
                    <table>
                        <colgroup>
                            <col width="25%" />
                            <col width="75%" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>상품명</th>
                            <td>{ product.productName }</td>
                        </tr>
                        <tr>
                            <th>상품갯수</th>
                            <td>{ amount }</td>
                        </tr>
                        <tr>
                            <th>결제금액</th>
                            <td>{ amount * product.productPrice }</td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <button onClick={onClickPurchaseHandler}>구매하기</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );
}

export default Order;
