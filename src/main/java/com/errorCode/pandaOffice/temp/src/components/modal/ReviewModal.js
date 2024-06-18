import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {callReviewRegistAPI} from "../../apis/ReviewAPICalls";

function ReviewModal({ modalData, setReviewModal }) {

    const [form, setForm] = useState({...modalData});
    const dispatch = useDispatch();
    const { success } = useSelector(state => state.reviewReducer);
    const navigate = useNavigate();
    const readOnly = modalData.reviewCode > 0;

    useEffect(() => {
        if(success === true) {
            navigate(`/product/${modalData.productCode}/review`);
        }
    }, [success]);

    const onChangeHandler = e => {
        setForm({
            ...form,
            [e.target.name] : e.target.value
        })
    }

    const onClickProductReviewHandler = () => {
        dispatch(callReviewRegistAPI({ registRequest : form }));
    };

    return (
        <div className="modal">
            <div className="modal-container">
                <div className="product-review-modal-div">
                    <h1>리뷰</h1>
                    <input
                        type="text"
                        name="reviewTitle"
                        placeholder="제목을 입력하세요"
                        value={ form.reviewTitle }
                        readOnly={ readOnly }
                        onChange={onChangeHandler}
                    />
                    <textarea
                        placeholder="내용을 입력하세요"
                        value={ form.reviewContent }
                        name="reviewContent"
                        readOnly={ readOnly }
                        onChange={onChangeHandler}
                    ></textarea>
                    {!readOnly && <button onClick={onClickProductReviewHandler}>리뷰 작성하기</button>}
                    <button
                        style={{
                            border: "none",
                            margin: 0,
                            fontSize: "10px",
                            height: "10px",
                        }}
                        onClick={() => setReviewModal(false)}
                    >
                        돌아가기
                    </button>
                </div>
            </div>
        </div>
    );
}

export default ReviewModal;
