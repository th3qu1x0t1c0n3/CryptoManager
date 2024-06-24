import {PortfolioService} from "../../services/PortfolioService";
import Wallet from "../Wallet";
import {useState} from "react";
import WalletForm from "../forms/WalletForm";
import {IWallet} from "../../assets/models/BlockChain";

function WalletPage() {
    const portfolioService = new PortfolioService();
    const [showForm, setShowForm] = useState<boolean>(false);
    const [wallets, setWallets] = useState<IWallet[]>([]);

    return (
        <div className={"text-center"}>
            <h1 className={"text-5xl"}>Wallets</h1>
            <div className={"my-2 text-center"}>
                <button onClick={() => setShowForm(!showForm)}
                        className={"border rounded transition ease-in duration-200 p-2 clickable hover:bg-port-five hover:border-port-one hover:text-port-one"}>
                    Add Wallets
                </button>

            </div>

            {
                showForm &&
                <div>
                    <WalletForm/>
                </div>
            }

            {/*<Wallet/>*/}
        </div>
    );
}

export default WalletPage;