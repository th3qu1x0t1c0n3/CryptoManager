import {IUser} from "../../assets/models/Authentication";
import {Route, Routes, useNavigate} from "react-router-dom";
import PageNotFound from "../utils/PageNotFound";
import React, {useEffect} from "react";
import Loading from "../utils/Loading";
import TransactionsPage from "./TransactionsPage";
import {PortfolioService} from "../../services/PortfolioService";
import {toast} from "react-toastify";

interface IHomePageProps {
    setUser: (user: any) => void;
    user: IUser | null;
}

function HomePage({setUser, user}: IHomePageProps) {
    const portfolioService = new PortfolioService();
    const navigate = useNavigate();

    const interval = setInterval(checkToken, 100000);

    function checkToken() {
        portfolioService.getUser()
            .then((response) => {
                // setUser(response);
            })
            .catch((error) => {
                toast.error(error.response?.data.message);
                setUser(null);
                navigate('/');
                interval && clearInterval(interval);
            });
    }

    return (
        user === null ?
            <Loading/> :
            <div>
                <Routes>
                    <Route path="/transactions" element={<TransactionsPage/>}/>
                    <Route path="/*" element={<PageNotFound/>}/>
                </Routes>
            </div>
    );
}

export default HomePage;