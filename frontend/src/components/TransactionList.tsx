import {PortfolioService} from "../services/PortfolioService";
import {useEffect, useState} from "react";
import {ITransaction} from "../assets/models/Transaction";
import {toast} from "react-toastify";
import CoinTransactionCard from "./CoinTransactionCard";

function TransactionList() {
    const portfolioService = new PortfolioService();
    const [transactions, setTransactions] = useState<ITransaction[]>([]);

    useEffect(() => {
        portfolioService.getTransactions()
            .then((data) => {
                data.sort((a: ITransaction, b: ITransaction) => new Date(b.transactionDate).getTime() - new Date(a.transactionDate).getTime());
                setTransactions(data)
            })
            .catch((error) => {
                toast.error(error.response?.data.message);
                console.log(error);
            });
    }, []);

    return (
        <div className={""}>
            <h1 className={"text-5xl"}>ALL Transactions</h1>

            <table className={"table border-collapse border mx-auto mt-5"}>
                <thead>
                <tr className={""}>
                    <th className={"border border-port-yellow px-2 w-1"}>ID</th>
                    <th className={"border border-port-yellow px-2 w-1/6"}>To Coin
                        <div className={"grid grid-cols-4 text-sm font-medium"}>
                            <h1>Name</h1>
                            <h1>Quantity</h1>
                            <h1>Value</h1>
                            <h1>Unit Value</h1>
                        </div>
                    </th>
                    <th className={"border border-port-yellow px-2 w-1/6"}>From Coin
                        <div className={"grid grid-cols-4 text-sm font-medium"}>
                            <h1>Name</h1>
                            <h1>Quantity</h1>
                            <h1>Value</h1>
                            <h1>Unit Value</h1>
                        </div>
                    </th>
                    <th className={"border border-port-yellow px-2  w-1/12"}>Date</th>
                    <th className={"border border-port-yellow px-2"}>Wallet</th>
                    <th className={"border border-port-yellow px-2"}>Exchange</th>
                </tr>
                </thead>
                <tbody className={""}>
                {transactions.map((transaction) => (
                    <tr className={""} key={transaction.id}>
                        <td className={"border border-port-yellow text-center"}>{transaction.id}</td>
                        <td className={"border border-port-yellow"}><CoinTransactionCard coin={transaction.toCoin}/>
                        </td>
                        <td className={"border border-port-yellow"}><CoinTransactionCard coin={transaction.fromCoin}/>
                        </td>
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