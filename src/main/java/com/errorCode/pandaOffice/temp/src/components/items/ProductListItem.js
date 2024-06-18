import {useNavigate} from "react-router-dom";

function ProductListItem({product : { productCode, productName, productImageUrl, productPrice }}) {

    const navigate = useNavigate();

    return (
        <div
            className="product-div"
            onClick={ () => navigate(`/product/${productCode}`)}
        >
            <img src={productImageUrl} alt={productName}/>
            <h5>{productName}</h5>
            <h5>{productPrice}</h5>
        </div>
    );

}

export default ProductListItem;