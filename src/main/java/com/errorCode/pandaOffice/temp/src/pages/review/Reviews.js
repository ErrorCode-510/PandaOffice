import {useEffect, useState} from "react";
import PagingBar from "../../components/common/PagingBar";
import {ToastContainer} from "react-toastify";
import {useNavigate, useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {callReviewsAPI} from "../../apis/ReviewAPICalls";

function Reviews() {

    const { productCode } = useParams();
    const { reviews } = useSelector(state => state.reviewReducer);
    const [currentPage, setCurrentPage] = useState(1);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        dispatch(callReviewsAPI({ productCode, currentPage }));
    }, [currentPage]);

    return (
        <>
            {reviews &&
            <div className="review-table-div">
                <button className="back-btn" onClick={ () => navigate(`/product/${productCode}`)}>상품으로</button>
                <table className="review-table">
                    <thead>
                    <tr>
                        <th>상품명</th>
                        <th>리뷰 제목</th>
                        <th>리뷰 내용</th>
                        <th>작성자</th>
                        <th>작성일</th>
                    </tr>
                    </thead>
                    <tbody>
                    { reviews.data.map(review => (
                        <tr
                            key={review.reviewCode}
                        >
                            <td>{review.productName}</td>
                            <td>{review.reviewTitle}</td>
                            <td>{review.reviewContent}</td>
                            <td>{review.memberName}</td>
                            <td>{review.createdAt}</td>
                        </tr>
                    ))
                    }
                    </tbody>
                </table>
                <PagingBar pageInfo={reviews.pageInfo} setCurrentPage={setCurrentPage} />
            </div>
            }
        </>
    );
}

export default Reviews;
