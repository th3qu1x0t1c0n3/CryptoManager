import {useEffect, useState} from "react";
import {ITransaction} from "../../assets/models/Transaction";
import {PortfolioService} from "../../services/PortfolioService";
import {toast} from "react-toastify";
import TransactionList from "../TransactionList";

function TransactionsPage() {
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
            <h1 className={"text-5xl "}>Transactions Page</h1>
            <TransactionList/>
        </div>
    );
}

export default TransactionsPage;