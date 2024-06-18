import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {callAdminProductListAPI} from "../../apis/ProductAPICalls";
import ProductTable from "../../components/items/ProductTable";
import PagingBar from "../../components/common/PagingBar";
import {useNavigate} from "react-router-dom";

function ProductManagement() {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [currentPage, setCurrentPage] = useState(1);
    const { products } = useSelector(state => state.productReducer);

    useEffect(() => {
        dispatch(callAdminProductListAPI({currentPage}));
    }, [currentPage]);

    return (
        <>
            { products &&
                <div className="management-div">
                    <ProductTable data={products.data}/>
                    <PagingBar pageInfo={products.pageInfo} setCurrentPage={setCurrentPage}/>
                    <button onClick={ () => navigate('/product-regist') }>상품등록</button>
                </div>
            }
        </>
    )
}
export default ProductManagement;