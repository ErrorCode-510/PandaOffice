import {useState} from "react";

function ProductForm({product, setForm, imageInput, modifyMode}) {


    const [imageUrl, setImageUrl] = useState(product.productImageUrl);

    /* 입력 양식 값 변경 시 state 수정 */
    const onChangeHandler = e => {
        setForm && setForm({
            ...product,
            [e.target.name] : e.target.value
        })
    }

    /* 이미지 업로드 버튼 클릭 시 input type file이 클릭 되도록 하는 이벤트 */
    const onClickImageUpload = () => {
        imageInput.current.click();
    }

    /* 이미지 파일 첨부 시 동작하는 이벤트 */
    const onChangeImageUpload = () => {
        const fileReader = new FileReader();
        fileReader.onload = e => {
            const { result } = e.target;
            if(result) setImageUrl(result);
        }
        if(imageInput.current.files[0])
            fileReader.readAsDataURL(imageInput.current.files[0]);
    }

    return (
        <div className="product-section">
            <div className="product-info-div">
                <div className="product-img-div">
                    { imageUrl &&
                        <img
                            className="product-img"
                            alt="preview"
                            src={ imageUrl }
                        />
                    }
                    <input
                        style={ { display: 'none' }}
                        type="file"
                        name='productImage'
                        accept='image/jpg,image/png,image/jpeg,image/gif'
                        ref={ imageInput }
                        onChange={ onChangeImageUpload }
                    />
                    <button
                        className="product-image-button"
                        onClick={ onClickImageUpload }
                        disabled={ !modifyMode }
                    >
                        이미지 업로드
                    </button>
                </div>
            </div>
            <div className="product-info-div">
                <table>
                    <tbody>
                    <tr>
                        <td><label>상품이름</label></td>
                        <td>
                            <input
                                name='productName'
                                placeholder='상품 이름'
                                className="product-info-input"
                                onChange={onChangeHandler}
                                value={product.productName}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td><label>상품가격</label></td>
                        <td>
                            <input
                                name='productPrice'
                                placeholder='상품 가격'
                                type='number'
                                className="product-info-input"
                                onChange={onChangeHandler}
                                value={product.productPrice}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td><label>판매 여부</label></td>
                        <td>
                            <label>
                                <input
                                    type="radio"
                                    name="status"
                                    onChange={onChangeHandler}
                                    value="usable"
                                    checked={product.status === 'usable'}
                                />
                                판매
                            </label> &nbsp;
                            <label>
                                <input
                                    type="radio"
                                    name="status"
                                    onChange={onChangeHandler}
                                    value="disable"
                                    checked={ product.status === 'disable'}
                                />판매중단</label>
                        </td>
                    </tr>
                    <tr>
                        <td><label>상품 종류</label></td>
                        <td>
                            <label><input type="radio" onChange={onChangeHandler} name="categoryCode" value="1"
                                          checked={product.categoryCode == 1}/> 식사</label> &nbsp;
                            <label><input type="radio" onChange={onChangeHandler} name="categoryCode" value="2"
                                          checked={product.categoryCode == 2}/> 디저트</label> &nbsp;
                            <label><input type="radio" onChange={onChangeHandler} name="categoryCode" value="3"
                                          checked={product.categoryCode == 3}/> 음료</label>
                        </td>
                    </tr>
                    <tr>
                        <td><label>상품 재고</label></td>
                        <td>
                            <input
                                placeholder='상품 재고'
                                type='number'
                                name='productStock'
                                className="product-info-input"
                                onChange={onChangeHandler}
                                value={product.productStock}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td><label>상품 설명</label></td>
                        <td>
                                    <textarea
                                        className="textarea-style"
                                        name='productDescription'
                                        onChange={onChangeHandler}
                                        value={product.productDescription}
                                    ></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default ProductForm;