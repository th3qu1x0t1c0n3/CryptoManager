import {ITransaction} from "../assets/models/Transaction";

interface ITransactionCardProps {
    transaction: ITransaction;
}

function TransactionCard({transaction}: ITransactionCardProps) {
    return (
        <div
            className="flex border border-gray-300 rounded-lg p-5 m-2 shadow-md hover:shadow-lg transition-shadow duration-200 w-1/2">
            <p className="text-sm me-1">{transaction.id} :</p>
            <p className="text-sm mx-2">{transaction.toCoin.name}</p>
            <p className="text-sm mx-1">{transaction.toCoin.quantity}</p>
            <p className="text-sm mx-1">{transaction.toCoin.value}$</p>
            <p className="text-sm mx-1">{transaction.toCoin.unitValue}$</p>

            <div className="border-r border-gray-300 mx-2"></div>

            <p className="text-sm mx-1">{transaction.fromCoin.name}</p>
            <p className="text-sm mx-1">{transaction.fromCoin.quantity}</p>
            <p className="text-sm mx-1">{transaction.fromCoin.value}$</p>
            <p className="text-sm mx-1">{transaction.fromCoin.unitValue}$</p>

            <div className="border-r border-gray-300 mx-2"></div>

            <p className="text-sm mx-2">{transaction.transactionDate}</p>
            <p className="text-sm mx-2">{transaction.wallet}</p>
            <p className="text-sm mx-2">{transaction.exchange}</p>
        </div>
    );
}

export default TransactionCard;