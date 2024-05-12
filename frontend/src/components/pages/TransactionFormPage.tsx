import {useState} from "react";
import {ICoinTransaction, ITransaction} from "../../assets/models/Transaction";
import {PortfolioService} from "../../services/PortfolioService";
import {toast} from "react-toastify";

function TransactionFormPage() {
    const portfolioService = new PortfolioService();
    const [transaction, setTransaction] = useState<ITransaction>({
        id: 0,
        toCoin: {name: "", quantity: 0, value: 0, unitValue: 0},
        fromCoin: {name: "CAD", quantity: 0, value: 0, unitValue: 0},
        transactionDate: "",
        wallet: "Newton wallet",
        exchange: "Newton",
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

        portfolioService.createTransaction(transaction)
            .then(response => {
                toast.success("Transaction created successfully!");
                console.log(response);
            })
            .catch(error => {
                toast.error(error.response?.data.message)
                console.log(error);
            });
    };

    return (
        <form onSubmit={handleSubmit} className="text-port-dark text-center">
            <div className="grid grid-cols-4 gap-3 mx-auto w-11/12 mb-5">
                <div>
                    <h2 className="text-3xl m-1 mb-0 text-port-white">To Coin</h2>
                    <CoinForm coin={transaction.toCoin} handleInputChange={handleInputChange} coinType="toCoin"/>
                </div>
                <div>
                    <h2 className="text-3xl m-1 mb-0 text-port-white">From Coin</h2>
                    <CoinForm coin={transaction.fromCoin} handleInputChange={handleInputChange}
                              coinType="fromCoin"/>
                </div>

                <div className={"col-span-2 text-start ms-10"}>
                    <h2 className="text-3xl m-1 mb-0 text-port-white">Transaction details</h2>
                    <div className={""}>
                        <label className={"text-port-white m-1 mb-0 block"}>Transaction date</label>
                        <input type="date" name="transactionDate" placeholder="Enter transaction date"
                               value={transaction.transactionDate}
                               onChange={(e) => handleInputChange(e, "transaction")}
                               className="border border-gray-300 rounded-lg p-2 mb-0"/>
                    </div>
                    <div className={""}>
                        <label className={"text-port-white m-1 mb-0 block"}>Wallet used</label>
                        <input type="text" name="wallet" placeholder="Enter wallet" value={transaction.wallet}
                               onChange={(e) => handleInputChange(e, "transaction")}
                               className="border border-gray-300 rounded-lg p-2 w-11/12"/>
                    </div>
                    <div className={""}>
                        <label className={"text-port-white m-1 mb-0 block"}>Exchange used</label>
                        <input type="text" name="exchange" placeholder="Enter exchange" value={transaction.exchange}
                               onChange={(e) => handleInputChange(e, "transaction")}
                               className="border border-gray-300 rounded-lg p-2 w-11/12"/>
                    </div>
                </div>
            </div>

            <button type="reset" onClick={() => setTransaction({
                id: 0,
                toCoin: {name: "", quantity: 0, value: 0, unitValue: 0},
                fromCoin: {name: "", quantity: 0, value: 0, unitValue: 0},
                transactionDate: "",
                wallet: "",
                exchange: ""
            })} className="bg-red-500 text-white rounded-lg p-2 px-4 mx-2">Reset
            </button>
            <button type="submit" className="bg-blue-500 text-white rounded-lg p-2 px-4 mx-2">Submit</button>
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
                <label className={"text-port-white m-1 mb-0 block"}>Coin name</label>
                <input type="text" name="name" placeholder="Enter coin name" value={coin.name}
                       onChange={(e) => handleInputChange(e, coinType)}
                       className="border border-gray-300 rounded-lg p-2"/>
            </div>
            <div>
                <label className={"text-port-white m-1 mb-0 block"}>Quantity of the coin</label>
                <input type="number" name="quantity" placeholder="Enter coin quantity" value={coin.quantity}
                       min={0} step="any"
                       onChange={(e) => handleInputChange(e, coinType)}
                       className="border border-gray-300 rounded-lg p-2"/>
            </div>
            <div>
                <label className={"text-port-white m-1 mb-0 block"}>Value for quantity</label>
                <input type="number" name="value" placeholder="Enter coin value" value={coin.value}
                       min={0} step="any"
                       onChange={(e) => handleInputChange(e, coinType)}
                       className="border border-gray-300 rounded-lg p-2"/>
            </div>
        </>
    );
}

export default TransactionFormPage;