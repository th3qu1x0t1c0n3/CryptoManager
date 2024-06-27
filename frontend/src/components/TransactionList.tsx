import {ITransaction} from "../assets/models/BlockChain";
import {
    Accordion,
    AccordionItem,
} from "react-accessible-accordion";
import TransactionCard from "./TransactionCard";

interface ITransactionListProps {
    transactions: ITransaction[];
}
function TransactionList({transactions}: ITransactionListProps) {

    // TODO: REDO
    return (
        <div className={""}>
            <h1 className={"text-5xl"}>Transactions</h1>

            <Accordion className={"mx-auto text-center"}>
                {transactions.map((transaction, index) => (
                    <AccordionItem key={index}>
                        <h1>{transaction.timeStamp}</h1>
                        <TransactionCard transaction={transaction} />
                    </AccordionItem>
                ))}
            </Accordion>

        </div>
    );
}

export default TransactionList;