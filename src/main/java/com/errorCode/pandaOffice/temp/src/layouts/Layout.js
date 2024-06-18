import Header from "../components/common/Header";
import Navbar from "../components/common/Navbar";
import Footer from "../components/common/Footer";
import {Outlet} from "react-router-dom";

function Layout() {


    return (
        <>
            <Header/>
            <Navbar/>
            <main className="main">
                <Outlet/>
            </main>
            <Footer/>
        </>
    );
}
export default Layout;