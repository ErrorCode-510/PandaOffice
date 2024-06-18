import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callAdminProductAPI, callAdminProductModifyAPI, callAdminProductRemoveAPI} from "../../apis/ProductAPICalls";
import ProductForm from "../../components/form/ProductForm";

function ProductModify() {

    const { productCode } = useParams();
    const dispatch = useDispatch();
    const navigate = useNavigate();
    /* 해당 페이지에서 읽기 모드와 수정 모드를 전환하는 state */
    const [modifyMode, setModifyMode] = useState(false);
    const [form, setForm] = useState({});
    const imageInput = useRef();
    const { product, success } = useSelector(state => state.productReducer);

    /* 최초 랜더링 후 상품 상세 정보 조회 */
    useEffect(() => {
        dispatch(callAdminProductAPI({productCode}));
    }, []);

    /* 수정 성공 시 또는 삭제 성공 시 상품 목록으로 이동 */
    useEffect(() => {
        if(success === true) navigate('/product-management');
    }, [success]);

    /* 수정 모드로 변환하는 이벤트 */
    const onClickModifyModeHandler = () => {
        setModifyMode(true);
        setForm({...product});
    }

    /* 수정 내용 저장 이벤트 */
    const onClickProductUpdateHandler = () => {
        /* 서버로 전달할 FormData 형태의 객체 설정 */
        const formData = new FormData();
        formData.append('productImg', imageInput.current.files[0]);
        formData.append('productRequest', new Blob([JSON.stringify(form)], { type : 'application/json'}));
        dispatch(callAdminProductModifyAPI({ productCode, modifyRequest : formData }));
    }

    const onClickProductDeleteHandler = () => {
        dispatch(callAdminProductRemoveAPI({productCode}));
    }

    return (
        <>
            <div className="product-button-div">
                <button onClick={ () => navigate(-1) }>돌아가기</button>
                {!modifyMode && <button onClick={ onClickModifyModeHandler }>수정 모드로 변환</button>}
                {modifyMode && <button onClick={ onClickProductUpdateHandler }>수정 내용 저장</button>}
                <button onClick={ onClickProductDeleteHandler }>상품 삭제</button>
            </div>
            { !modifyMode && product && <ProductForm product={product} modifyMode={modifyMode}/> }
            { modifyMode && <ProductForm product={form} setForm={setForm} imageInput={imageInput} modifyMode={modifyMode}/> }
        </>
    );
}

export default ProductModify;