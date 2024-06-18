import {Outlet} from "react-router-dom";
import MyPageNavBar from "../components/common/MyPageNavBar";

function MyPageLayout () {

    return (
        <div className="mypage-layout-div">
            <MyPageNavBar/>
            <main className="mypage-main">
                <Outlet/>
            </main>

        </div>
    );
}

export default MyPageLayout;