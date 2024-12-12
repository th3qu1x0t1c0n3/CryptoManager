import {IUser} from "../../assets/models/Authentication";
import {Route, Routes, useNavigate} from "react-router-dom";
import PageNotFound from "../utils/PageNotFound";
import React, {useEffect, useState} from "react";
import Loading from "../utils/Loading";
import TransactionsPage from "./TransactionsPage";
import {PortfolioService} from "../../services/PortfolioService";
import {toast} from "react-toastify";
import TransactionForm from "../forms/TransactionForm";
import {PortfolioServerInstance} from "../../App";
import Holdings from "../Holdings";
import KellyCriterion from "../KellyCriterion";
import Allocation from "../Allocation";
import Profile from "./Profile";
import WalletPage from "./WalletPage";

interface IHomePageProps {
    setUser: (user: any) => void;
    user: IUser | null;
}

function HomePage({setUser, user}: IHomePageProps) {
    const portfolioService = new PortfolioService();
    const navigate = useNavigate();
    // const [tab, setTab] = useState('portfolio');
    // const tabs = [
    //     {id: 'portfolio', label: 'portfolio'},
    //     {id: 'transactions', label: 'transactions'},
    //     {id: 'allocations', label: 'allocations'},
    //     {id: 'kelly', label: 'kelly'},
    // ];

    useEffect(() => {
        const token = sessionStorage.getItem('token');
        if (token) {
            PortfolioServerInstance.defaults.headers.common['Authorization'] = `Bearer ${token}`; // 'Bearer ' + token;

            portfolioService.getUser()
                .then((response) => {
                    setUser(response);
                })
                .catch((error) => {
                    toast.error(error.response?.data.message);
                    // navigate("/");
                });
        } else {
            navigate("/");
        }
    }, []);

    const handleDisconnect = () => {
        setUser(null);
        navigate("/")
        PortfolioServerInstance.defaults.headers.common['Authorization'] = null;
        sessionStorage.removeItem('token');
    };

    return (
        user === null ?
            <Loading/> :
            <div>
                <div className={`fixed top-20 right-5 mt-5`}>
                    <button onClick={handleDisconnect}
                            className="bg-red-500 text-port-two px-4 py-2 rounded ">Disconnect
                    </button>

                </div>

                <Routes>
                    <Route path="/landing" element={<Holdings/>}/>
                    <Route path="/portfolio" element={<Holdings/>}/>
                    <Route path="/wallets" element={<WalletPage/>}/>
                    <Route path="/transactions" element={<TransactionsPage/>}/>
                    <Route path="/allocations" element={<Allocation user={user}/>}/>
                    <Route path="/kelly" element={<KellyCriterion/>}/>
                    <Route path="/profile" element={<Profile user={user}/>}/>
                    <Route path="/*" element={<PageNotFound/>}/>
                </Routes>
            </div>
    );
}

export default HomePage;