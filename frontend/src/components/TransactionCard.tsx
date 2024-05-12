import {ITransaction} from "../assets/models/Transaction";
import CoinTransactionCard from "./CoinTransactionCard";

interface ITransactionCardProps {
    transaction: ITransaction;
}

function TransactionCard({transaction}: ITransactionCardProps) {
    return (
        <div
            className="flex border border-gray-300 rounded-lg p-5 m-2 shadow-md hover:shadow-lg transition-shadow duration-200">
            <p className="text-sm me-1">{transaction.id} :</p>

            <CoinTransactionCard coin={transaction.toCoin}/>
            <div className="border-r border-gray-300 mx-2"></div>
            <CoinTransactionCard coin={transaction.fromCoin}/>

            <div className="border-r border-gray-300 mx-2"></div>

            <p className="text-sm mx-2">{transaction.transactionDate}</p>
            <p className="text-sm mx-2">{transaction.wallet}</p>
            <p className="text-sm mx-2">{transaction.exchange}</p>
        </div>
    );
}

export default TransactionCard;