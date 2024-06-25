import {PortfolioService} from "../../services/PortfolioService";
import {useEffect, useState} from "react";
import WalletForm from "../forms/WalletForm";
import {IWallet} from "../../assets/models/BlockChain";
import Wallet from "../Wallet";

function WalletPage() {
    const portfolioService = new PortfolioService();
    const [showForm, setShowForm] = useState<boolean>(false);
    const [wallets, setWallets] = useState<IWallet[]>([]);
    const [newOne, setNewOne] = useState<IWallet | null>(null);

    useEffect(() => {
        portfolioService.getWallets()
            .then((wallets) => {
                setWallets(wallets);
            })
            .catch((error) => {
                console.log(error);
            });
    }, [newOne]);

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
                    <WalletForm setWallet={setNewOne}/>
                </div>
            }

            {
                wallets.map((wallet) => {
                    return (
                        <div key={wallet.id} className={""}>
                            <Wallet wallet={wallet}/>
                        </div>
                    )
                })
            }

        </div>
    );
}

export default WalletPage;