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
import FinancialInfo from "../FinancialInfo";
import Holdings from "../Holdings";

interface IHomePageProps {
    setUser: (user: any) => void;
    user: IUser | null;
}

function HomePage({setUser, user}: IHomePageProps) {
    const portfolioService = new PortfolioService();
    const navigate = useNavigate();
    const [tab, setTab] = useState('portfolio');
    const tabs = [
        { id: 'portfolio', label: 'portfolio' },
        { id: 'transactions', label: 'transactions' },
        { id: 'allocations', label: 'allocations' },
        { id: 'kelly', label: 'kelly' },
    ];

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
                <div className="items-center my-2 mx-auto text-center w-full">
                    {tabs.map(tabItem => (
                        <button
                            key={tabItem.id}
                            className={`py-2 px-4 rounded-lg border border-port-gray ${tab === tabItem.id ? 'bg-port-dark' : 'bg-port-blue hover:bg-blue-600'}`}
                            onClick={() => {
                                setTab(tabItem.id)
                            }}
                            style={{position: 'relative'}}
                        >
                            {tabItem.label}
                        </button>
                    ))}
                </div>
                <Routes>
                    {/*<Route path="/transactions" element={<TransactionsPage/>}/>*/}
                    <Route path="/transact" element={<TransactionFormPage/>}/>
                    <Route path="/profile" element={<PageNotFound/>}/>
                </Routes>

                {/*{tab === 'portfolio' && <div>Portfolio</div> }*/}
                {tab === 'portfolio' && <Holdings /> }
                {tab === 'transactions' && <TransactionsPage/>}
                {tab === 'allocations' && <div>Allocations</div>}
                {tab === 'kelly' && <div>Kelly</div>}

            </div>
    );
}

export default HomePage;