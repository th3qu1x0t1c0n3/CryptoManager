import {ITransaction} from "../assets/models/BlockChain";
import {AccordionItemButton, AccordionItemHeading, AccordionItemPanel} from "react-accessible-accordion";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowRight, faEllipsisVertical} from "@fortawesome/free-solid-svg-icons";
import Decimal from "decimal.js";
import WalletAddress from "./utils/WalletAddress";

interface ITransactionCardProps {
    transaction: ITransaction;
}

// TODO: REDO
// `${transaction.id % 2 === 1 ? "bg-port-two" : "bg-port-one"}
function TransactionCard({transaction}: ITransactionCardProps) {


    function getDate(timeStamp: string) {
        const date = new Date(Number(timeStamp) * 1000);
        return date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    }

    function handleOptions(e: any) {
        e.preventDefault();
        e.stopPropagation();
        console.log("Options")
    }

    function divideTx(value: string) {
        const val = new Decimal(value).dividedBy(new Decimal(1e18));
        return val.toFixed(6);
    }

    return (
        <div className={"text-center"}>
            <AccordionItemHeading>
                <AccordionItemButton>
                    <div className={"grid grid-cols-3"}>
                        <div className={"flex flex-row items-center"}>
                            <p className={"text-start"}>Logo</p>
                            <div className={"text-start ms-3"}>
                                {/*<p>{transaction.functionName}</p>*/}
                                <p>{divideTx(transaction.value)} {transaction.tokenName}{transaction.tokenSymbol}</p>
                                <p>{getDate(transaction.timeStamp)}</p>
                            </div>
                        </div>

                        <div className={"flex space-x-2 my-auto justify-center"}>
                            <WalletAddress address={transaction.from} />
                            <FontAwesomeIcon icon={faArrowRight} className={"my-auto mx-auto"} />
                            <p>{transaction.contractAddress} : {transaction.tokenName}</p>
                            <WalletAddress address={transaction.to} />
                        </div>

                        <div className={"my-auto text-end"}>
                            <FontAwesomeIcon icon={faEllipsisVertical}
                                             onClick={handleOptions}
                                             className={"m-3"} />
                        </div>
                    </div>
                </AccordionItemButton>
            </AccordionItemHeading>

            <AccordionItemPanel className={"text-start"}>
                <div className={"grid grid-cols-3"}>
                    <div>
                        <p className={"font-bold"}>Transaction Hash:</p>
                        <WalletAddress address={transaction.hash}/>
                        <p className={"font-bold"}>From:</p>
                        <WalletAddress address={transaction.from}/>
                        <p className={"font-bold"}>To:</p>
                        <WalletAddress address={transaction.to}/>
                    </div>
                    <div>

                    </div>
                    <div>
                        <p className={"font-bold"}>Contract Address:</p>
                        <WalletAddress address={transaction.contractAddress}/>
                        <p className={"font-bold"}>Token Name:</p>
                        <p>{transaction.tokenName}</p>
                        <p className={"font-bold"}>Token Symbol:</p>
                        <p>{transaction.tokenSymbol}</p>
                    </div>
                </div>
            </AccordionItemPanel>

        </div>
    );
}

export default TransactionCard;