import {useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callProductSearchListAPI} from "../../apis/ProductAPICalls";
import ProductList from "../../components/lists/ProductList";
import PagingBar from "../../components/common/PagingBar";

function SearchMain() {

    const dispatch = useDispatch();
    const [searchParams] = useSearchParams();
    const productName = searchParams.get('value');
    const { products } = useSelector(state => state.productReducer);
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        dispatch(callProductSearchListAPI({productName, currentPage}));
    }, [productName, currentPage]);

    return (
        <>
            <h3>[{productName}] 검색 결과</h3>
            { products
                &&
                <>
                    <ProductList data={ products.data }/>
                    <PagingBar pageInfo={ products.pageInfo } setCurrentPage={setCurrentPage}/>
                </>
            }
        </>
    );

}

export default SearchMain;