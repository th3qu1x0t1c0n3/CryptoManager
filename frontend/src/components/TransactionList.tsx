import {PortfolioService} from "../services/PortfolioService";
import {useEffect, useState} from "react";
import {ITransaction} from "../assets/models/Transaction";
import {toast} from "react-toastify";
import TransactionCard from "./TransactionCard";
import CoinTransactionCard from "./CoinTransactionCard";

function TransactionList() {
    const portfolioService = new PortfolioService();
    const [transactions, setTransactions] = useState<ITransaction[]>([]);

    useEffect(() => {
        portfolioService.getTransactions()
            .then((data) => {
                setTransactions(data)
            })
            .catch((error) => {
                toast.error(error.response?.data.message);
                console.log(error);
            });
    }, []);

    return (
        <div className={""}>
            <h2 className={"text-3xl"}>Transaction Table</h2>
            <table className={"table border-collapse border mx-auto"}>
                <thead>
                    <tr className={""}>
                        <th className={"border border-port-yellow px-2 w-1"}>ID</th>
                        <th className={"border border-port-yellow px-2 w-1/6"}>To Coin</th>
                        <th className={"border border-port-yellow px-2 w-1/6"}>From Coin</th>
                        <th className={"border border-port-yellow px-2  w-1/12"}>Date</th>
                        <th className={"border border-port-yellow px-2"}>Wallet</th>
                        <th className={"border border-port-yellow px-2"}>Exchange</th>
                    </tr>
                </thead>
                <tbody>
                {transactions.map((transaction) => (
                    <tr  className={""} key={transaction.id}>
                        <td className={"border border-port-yellow text-center"}>{transaction.id}</td>
                        <td className={"border border-port-yellow"}><CoinTransactionCard coin={transaction.toCoin}/></td>
                        <td className={"border border-port-yellow"}><CoinTransactionCard coin={transaction.fromCoin}/></td>
                        <td className={"border border-port-yellow px-2"}>{transaction.transactionDate}</td>
                        <td className={"border border-port-yellow px-2"}>{transaction.wallet}</td>
                        <td className={"border border-port-yellow px-2"}>{transaction.exchange}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            {/*<ul className={""}>*/}
            {/*    {transactions.map((transaction) => (*/}
            {/*        <li key={transaction.id}>*/}
            {/*            <TransactionCard transaction={transaction}/>*/}
            {/*        </li>*/}
            {/*    ))}*/}
            {/*</ul>*/}
        </div>
    );
}

export default TransactionList;