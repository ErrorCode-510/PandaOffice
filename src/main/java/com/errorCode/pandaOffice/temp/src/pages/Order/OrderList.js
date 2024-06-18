import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callOrdersAPI} from "../../apis/OrderAPICalls";
import PagingBar from "../../components/common/PagingBar";
import {useNavigate} from "react-router-dom";
import ReviewModal from "../../components/modal/ReviewModal";
import {ToastContainer} from "react-toastify";

function OrderList () {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [currentPage, setCurrentPage] = useState(1);
    const [reviewModal, setReviewModal] = useState(false);
    const [modalData, setModalData] = useState({});
    const { orders } = useSelector(state => state.orderReducer);
    const onClickReviewHandler = (order) => {
        setModalData({
            orderCode : order.orderCode,
            productCode : order.productCode,
            reviewCode : order.reviewCode ? order.reviewCode : 0,
            reviewTitle : order.reviewTitle ? order.reviewTitle : '',
            reviewContent : order.reviewContent ? order.reviewContent : ''
        });
        setReviewModal(true);
    }

    useEffect(() => {
        dispatch(callOrdersAPI({currentPage}));
    }, [currentPage]);

    return (
        <>
            <ToastContainer hideProgressBar={true} position="top-center"/>
            {
                reviewModal && <ReviewModal setReviewModal={setReviewModal} modalData={modalData}/>
            }
            {
                orders &&
                <>
                    <div className="payment-div">
                        <h1>결제 정보</h1>
                        <table className="payment-table">
                            <colgroup>
                                <col width="20%" />
                                <col width="40%" />
                                <col width="20%" />
                                <col width="20%" />
                            </colgroup>
                            <thead>
                            <tr>
                                <th>주문일자</th>
                                <th>주문 상품 정보</th>
                                <th>상품금액(수량)</th>
                                <th>리뷰</th>
                            </tr>
                            </thead>
                            <tbody>
                            { orders.data.map(order => (
                                <tr
                                    key={order.orderCode}
                                    onClick={ () => navigate(`/product/${order.productCode}`) }
                                >
                                    <td>{order.orderDate}</td>
                                    <td>{order.productName}</td>
                                    <td>
                                        {order.productPrice}원 ({order.orderAmount}개)
                                    </td>
                                    <td>
                                        <button
                                            className="review-write-button"
                                            onClick={ e => {
                                                    e.stopPropagation();
                                                    onClickReviewHandler(order);
                                                }
                                            }
                                        >
                                            { order.reviewCode !== null ? '리뷰 보기' : '리뷰 쓰기' }
                                        </button>
                                    </td>
                                </tr>
                            ))
                            }
                            </tbody>
                        </table>
                    </div>
                    <PagingBar pageInfo={orders.pageInfo} setCurrentPage={setCurrentPage}/>
                </>
            }
        </>
    );

}
export default OrderList;