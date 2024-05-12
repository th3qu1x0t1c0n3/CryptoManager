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

    useEffect(() => {
        const token = sessionStorage.getItem('token');
        if (token) {
            PortfolioServerInstance.defaults.headers.common['Authorization'] = token;

            portfolioService.getUser()
                .then((response) => {
                    setUser(response);
                })
                .catch((error) => {
                    toast.error(error.response?.data.message);
                    navigate("/");
                });
        } else {
            navigate("/");
        }
    }, []);

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