import {useNavigate} from "react-router-dom";
import ProductForm from "../../components/form/ProductForm";
import {useEffect, useRef, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callAdminProductRegistAPI} from "../../apis/ProductAPICalls";

function ProductRegist () {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const[ form, setForm ] = useState({
        productName : '',
        productPrice : '',
        status : '',
        categoryCode: '',
        productStock : '',
        productDescription : ''
    });
    const imageInput = useRef();
    const { success } = useSelector(state => state.productReducer);

    useEffect(() => {
        if(success === true) navigate('/product-management');
    }, [success]);

    const onClickProductRegistHandler = () => {
        /* 서버로 전달할 FormData 형태의 객체 설정 */
        const formData = new FormData();
        formData.append('productImg', imageInput.current.files[0]);
        formData.append('productRequest', new Blob([JSON.stringify(form)], { type : 'application/json'}));
        dispatch(callAdminProductRegistAPI({ registRequest : formData }));
    }

    return (
        <>
            <div className="product-button-div">
                <button onClick={ () => navigate(-1) }>돌아가기</button>
                <button onClick={ onClickProductRegistHandler }>상품등록</button>
            </div>
            <ProductForm product={form} setForm={setForm} imageInput={imageInput} modifyMode={true}/>
        </>
    );
}

export default ProductRegist;