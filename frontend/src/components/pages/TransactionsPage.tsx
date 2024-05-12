import {useEffect, useState} from "react";
import {ITransaction} from "../../assets/models/Transaction";
import {PortfolioService} from "../../services/PortfolioService";
import {toast} from "react-toastify";
import TransactionList from "../TransactionList";
import TransactionForm from "../TransactionForm";

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
            <div className={"grid grid-cols-2"}>
                <TransactionList/>
                <TransactionForm />
            </div>
        </div>
    );
}

export default TransactionsPage;