import TransactionList from "../TransactionList";
import {PortfolioService} from "../../services/PortfolioService";
import {useEffect, useState} from "react";
import TransactionForm from "../forms/TransactionForm";
import {ITransaction} from "../../assets/models/BlockChain";

function TransactionsPage() {
    const portfolioService = new PortfolioService();
    const [transactions, setTransactions] = useState<ITransaction[]>([]);
    const [showForm, setShowForm] = useState<boolean>(false);

    useEffect(() => {
        // portfolioService.getTransactions()
        //     .then((data: ITransaction[]) => {
        //         data.sort((a: ITransaction, b: ITransaction) => new Date(b.timeStamp).getTime() - new Date(a.timeStamp).getTime());
        //         setTransactions(data)
        //     })
        //     .catch((error) => {
        //         toast.error(error.response?.data.message);
        //     });
    }, []);

    return (
        <div>
            <div className={"my-2 text-center"}>
                <button onClick={() => setShowForm(!showForm)}
                        className={"border rounded transition ease-in duration-200 p-2 clickable hover:bg-port-five hover:border-port-one hover:text-port-one"}>
                    Add transaction
                </button>

            </div>

            {
                showForm &&
                <div>
                    <TransactionForm/>
                </div>
            }
            <TransactionList transactions={transactions}/>
        </div>
    );
}

export default TransactionsPage;