function PagingBar({pageInfo, setCurrentPage}) {

    const pageNumber = [];
    for(let i = pageInfo.startPage; i <= pageInfo.endPage; i++) {
        pageNumber.push(i);
    }

    return (
        <ul className="paging-ul">
            <li>
                <button
                    className="paging-btn"
                    disabled={ pageInfo.currentPage <= 1 }
                    onClick={ () => setCurrentPage(pageInfo.currentPage - 1) }
                >
                    &lt;
                </button>
            </li>
                {
                    pageNumber.map(
                        num =>
                            <li key={num}>
                                <button
                                    className="paging-btn"
                                    style={ pageInfo.currentPage === num ? { backgroundColor : 'orange'} : null }
                                    disabled={ pageInfo.currentPage === num }
                                    onClick={ () => setCurrentPage(num) }
                                >
                                    {num}
                                </button>
                            </li>
                    )
                }
            <li>
                <button
                    className="paging-btn"
                    disabled={ pageInfo.currentPage >= pageInfo.maxPage }
                    onClick={ () => setCurrentPage(pageInfo.currentPage + 1) }
                >
                    &gt;
                </button>
            </li>
        </ul>
    );
}

export default PagingBar;