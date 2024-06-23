import CoinTransactionCard from "./CoinTransactionCard";
import {ITransaction} from "../assets/models/Transaction";

interface ITransactionListProps {
    transactions: ITransaction[];
}
function TransactionList({transactions}: ITransactionListProps) {

    return (
        <div className={""}>
            <h1 className={"text-5xl"}>ALL Transactions</h1>

            <table className={"table border-collapse border mx-auto mt-5"}>
                <thead>
                <tr className={""}>
                    <th className={"border border-port-three px-2 w-1"}>ID</th>
                    <th className={"border border-port-three px-2 w-1/6"}>To Coin
                        <div className={"grid grid-cols-4 text-sm font-medium"}>
                            <h1>Name</h1>
                            <h1>Quantity</h1>
                            <h1>Value</h1>
                            <h1>Unit Value</h1>
                        </div>
                    </th>
                    <th className={"border border-port-three px-2 w-1/6"}>From Coin
                        <div className={"grid grid-cols-4 text-sm font-medium"}>
                            <h1>Name</h1>
                            <h1>Quantity</h1>
                            <h1>Value</h1>
                            <h1>Unit Value</h1>
                        </div>
                    </th>
                    <th className={"border border-port-three px-2  w-1/12"}>Date</th>
                    <th className={"border border-port-three px-2"}>Wallet</th>
                    <th className={"border border-port-three px-2"}>Exchange</th>
                </tr>
                </thead>
                <tbody className={""}>
                {transactions.map((transaction) => (
                    <tr className={""} key={transaction.id}>
                        <td className={"border border-port-three text-center"}>{transaction.id}</td>
                        <td className={"border border-port-three w-1/3"}><CoinTransactionCard coin={transaction.toCoin}/></td>
                        <td className={"border border-port-three w-1/3"}><CoinTransactionCard coin={transaction.fromCoin}/></td>
                        <td className={"border border-port-three px-2"}>{transaction.transactionDate}</td>
                        <td className={"border border-port-three px-2"}>{transaction.wallet}</td>
                        <td className={"border border-port-three px-2"}>{transaction.exchange}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default TransactionList;