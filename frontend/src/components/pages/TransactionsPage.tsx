import TransactionList from "../TransactionList";
import FinancialInfo from "../FinancialInfo";

function TransactionsPage() {

    return (
        <div>
            <FinancialInfo />
            <div className={"my-2"}></div>
            <TransactionList/>
        </div>
    );
}

export default TransactionsPage;