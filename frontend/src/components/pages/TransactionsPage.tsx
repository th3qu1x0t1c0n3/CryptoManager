import TransactionList from "../TransactionList";
import {PortfolioService} from "../../services/PortfolioService";
import {useEffect, useState} from "react";
import {ITransaction} from "../../assets/models/Transaction";
import {toast} from "react-toastify";

function TransactionsPage() {
    const portfolioService = new PortfolioService();
    const [transactions, setTransactions] = useState<ITransaction[]>([]);
    const [profits, setProfits] = useState<{ [key: string]: { profit: number, profitPercentage: number } }>({});

    useEffect(() => {
        portfolioService.getTransactions()
            .then((data: ITransaction[]) => {
                data.sort((a: ITransaction, b: ITransaction) => new Date(b.transactionDate).getTime() - new Date(a.transactionDate).getTime());
                setTransactions(data)

            })
            .catch((error) => {
                toast.error(error.response?.data.message);
                console.log(error);
            });
    }, []);


    return (
        <div>
            <div className={"my-2"}></div>
            <TransactionList transactions={transactions}/>
        </div>
    );
}

export default TransactionsPage;