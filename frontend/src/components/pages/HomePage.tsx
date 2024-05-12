import {IUser} from "../../assets/models/Authentication";
import {Route, Routes, useNavigate} from "react-router-dom";
import PageNotFound from "../utils/PageNotFound";
import React, {useEffect, useState} from "react";
import Loading from "../utils/Loading";
import TransactionsPage from "./TransactionsPage";
import {PortfolioService} from "../../services/PortfolioService";
import {toast} from "react-toastify";
import TransactionFormPage from "./TransactionFormPage";
import {PortfolioServerInstance} from "../../App";

interface IHomePageProps {
    setUser: (user: any) => void;
    user: IUser | null;
}

function HomePage({setUser, user}: IHomePageProps) {
    const portfolioService = new PortfolioService();
    const navigate = useNavigate();
    const interval = setInterval(checkToken, 5000);
    const [token, setToken] = useState<string | null>(null);

    function checkToken() {
        if (window.location.pathname === "/") {
            interval && clearInterval(interval);
        }
        if (token === null) {
            const tmp = sessionStorage.getItem('token');
            if (tmp) {
                setToken(tmp);
                PortfolioServerInstance.defaults.headers.common['Authorization'] = tmp;
            }
        }

        portfolioService.getUser()
            .then((response) => {
                if (user === null) {
                    setUser(response);
                }
            })
            .catch((error) => {
                toast.error(error.response?.data.message);
                setUser(null);
                navigate('/');
                interval && clearInterval(interval);
            });
    }

    // useEffect(() => {
    //     const tmp = sessionStorage.getItem('token');
    //
    //     if (tmp) {
    //         if (token === null){
    //             setToken(tmp);
    //             PortfolioServerInstance.defaults.headers.common['Authorization'] = token;
    //             portfolioService.getUser()
    //                 .then((response) => {
    //                     setUser(response);
    //                 })
    //                 .catch((error) => {
    //                     toast.error(error.response?.data.message);
    //                 });
    //         }
    //     } else {
    //         navigate('/');
    //     }
    // }, [token]);

    return (
        user === null ?
            <Loading/> :
            <div>
                <Routes>
                    <Route path="/transactions" element={<TransactionsPage/>}/>
                    <Route path="/transact" element={<TransactionFormPage/>}/>
                    <Route path="/*" element={<PageNotFound/>}/>
                </Routes>
            </div>
    );
}

export default HomePage;