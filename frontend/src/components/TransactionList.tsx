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
        <div className={"w-1/2 mx-auto"}>
            <h1 className={"text-5xl"}>Transactions</h1>

            <Accordion className={"text-start"}>
                {transactions.map((transaction, index) => (
                    <AccordionItem key={index} className={"my-2 border rounded p-3"}>
                        <TransactionCard transaction={transaction} />
                    </AccordionItem>
                ))}
            </Accordion>

        </div>
    );
}

export default TransactionList;