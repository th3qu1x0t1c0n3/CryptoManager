import {ITransaction} from "../assets/models/BlockChain";

interface ITransactionCardProps {
    transaction: ITransaction;
}

// TODO: REDO
function TransactionCard({transaction}: ITransactionCardProps) {
    return (
        <div></div>
        // <tr className={`${transaction.id % 2 === 1 ? "bg-port-two" : "bg-port-one"} flex border border-gray-300 rounded-lg p-5 m-2 shadow-md hover:shadow-lg transition-shadow duration-200`}>
        //     <td className="text-sm me-1">{transaction.id}</td>
        //
        //     <td className="text-sm me-1"><CoinTransactionCard coin={transaction.toCoin}/></td>
        //     <td className="text-sm me-1"><CoinTransactionCard coin={transaction.fromCoin}/></td>
        //
        //     <td className="text-sm mx-2">{transaction.transactionDate}</td>
        //     <td className="text-sm mx-2">{transaction.wallet}</td>
        //     <td className="text-sm mx-2">{transaction.exchange}</td>
        // </tr>
    );
}

export default TransactionCard;