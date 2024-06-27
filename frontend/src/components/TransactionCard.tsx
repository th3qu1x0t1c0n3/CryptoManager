import {ITransaction} from "../assets/models/BlockChain";
import {AccordionItemButton, AccordionItemHeading, AccordionItemPanel} from "react-accessible-accordion";

interface ITransactionCardProps {
    transaction: ITransaction;
}

// TODO: REDO
// `${transaction.id % 2 === 1 ? "bg-port-two" : "bg-port-one"}
function TransactionCard({transaction}: ITransactionCardProps) {
    return (
        <div>
            <AccordionItemHeading>
                <AccordionItemButton>
                    {transaction.hash}
                </AccordionItemButton>
            </AccordionItemHeading>

            <AccordionItemPanel>
                {/* Display transaction details here */}
                <p>Transaction Hash: {transaction.hash}</p>
                <p>From: {transaction.from}</p>
                <p>To: {transaction.to}</p>
                {/* Add more details as needed */}
            </AccordionItemPanel>

        </div>
    );
}

export default TransactionCard;