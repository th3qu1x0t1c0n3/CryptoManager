import {PortfolioService} from "../services/PortfolioService";
import {useEffect, useState} from "react";
import {ITransaction} from "../assets/models/Transaction";
import {toast} from "react-toastify";
import TransactionCard from "./TransactionCard";

function TransactionList() {
    const portfolioService = new PortfolioService();
    const [transactions, setTransactions] = useState<ITransaction[]>([]);

    useEffect(() => {
        portfolioService.getTransactions()
            .then((data) => setTransactions(data))
            .catch((error) => {
                toast.error(error.response?.data.message);
                console.log(error);
            });
    }, []);

    return (
        <div>
            <h2 className={"text-3xl"}>Transaction List</h2>
            <ul>
                {transactions.map((transaction) => (
                    <li key={transaction.id}>
                        <TransactionCard transaction={transaction}/>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default TransactionList;