import {useState} from "react";
import {ICoinTransaction, ITransaction} from "../assets/models/Transaction";
import {PortfolioService} from "../services/PortfolioService";
import {toast} from "react-toastify";

function TransactionForm() {
    const portfolioService = new PortfolioService();
    const [transaction, setTransaction] = useState<ITransaction>({
        id: 0,
        toCoin: {name: "", quantity: 0, value: 0, unitValue: 0},
        fromCoin: {name: "", quantity: 0, value: 0, unitValue: 0},
        transactionDate: "",
        wallet: "",
        exchange: "",
    });

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>, coin: "toCoin" | "fromCoin" | "transaction") => {
        const {name, value} = event.target;
        if (coin === "transaction") {
            setTransaction({...transaction, [name]: value});
        } else {
            setTransaction({...transaction, [coin]: {...transaction[coin], [name]: value}});
        }
    };

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        // Handle form submission here

        portfolioService.createTransaction(transaction)
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                toast.error(error.response?.data.message)
                console.log(error);
            });
    };

    return (
        <form onSubmit={handleSubmit} className="grid grid-cols-3 gap-3 text-port-dark">
            <h2 className="text-lg font-semibold m-1 mb-0  col-span-3 text-port-white">To Coin</h2>
            <CoinForm coin={transaction.toCoin} handleInputChange={handleInputChange} coinType="toCoin"/>

            <h2 className="text-lg font-semibold m-1 mb-0 col-span-3 text-port-white">From Coin</h2>
            <CoinForm coin={transaction.fromCoin} handleInputChange={handleInputChange} coinType="fromCoin"/>

            <h2 className="text-lg font-semibold m-1 mb-0 col-span-3 text-port-white">Transaction details</h2>
            <input type="date" name="transactionDate" placeholder="Enter transaction date"
                   value={transaction.transactionDate}
                   onChange={(e) => handleInputChange(e, "transaction")}
                   className="border border-gray-300 rounded-lg p-2"/>
            <input type="text" name="wallet" placeholder="Enter wallet" value={transaction.wallet}
                   onChange={(e) => handleInputChange(e, "transaction")}
                   className="border border-gray-300 rounded-lg p-2"/>
            <input type="text" name="exchange" placeholder="Enter exchange" value={transaction.exchange}
                   onChange={(e) => handleInputChange(e, "transaction")}
                   className="border border-gray-300 rounded-lg p-2"/>
            <div className={""}></div>

            <button type="submit" className="bg-blue-500 text-white rounded-lg p-2">Submit</button>
        </form>
    );
}

interface CoinFormProps {
    coin: ICoinTransaction;
    handleInputChange: (event: React.ChangeEvent<HTMLInputElement>, coin: "toCoin" | "fromCoin") => void;
    coinType: "toCoin" | "fromCoin";
}

function CoinForm({coin, handleInputChange, coinType}: CoinFormProps) {
    return (
        <>
            <div>
                <label className={"text-port-white m-1 mb-0"}>Coin name</label>
                <input type="text" name="name" placeholder="Enter coin name" value={coin.name}
                       onChange={(e) => handleInputChange(e, coinType)}
                       className="border border-gray-300 rounded-lg p-2"/>
            </div>
            <div>
                <label className={"text-port-white m-1 mb-0"}>Quantity of the coin</label>
                <input type="number" name="quantity" placeholder="Enter coin quantity" value={coin.quantity}
                       min={0} step="any"
                       onChange={(e) => handleInputChange(e, coinType)}
                       className="border border-gray-300 rounded-lg p-2"/>
            </div>
            <div>
                <label className={"text-port-white m-1 mb-0"}>Value for quantity</label>
                <input type="number" name="value" placeholder="Enter coin value" value={coin.value}
                       min={0} step="any"
                       onChange={(e) => handleInputChange(e, coinType)}
                       className="border border-gray-300 rounded-lg p-2"/>
            </div>
        </>
    );
}

export default TransactionForm;