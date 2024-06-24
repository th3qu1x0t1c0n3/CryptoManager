import {IWallet} from "../assets/models/BlockChain";
import logos from "../assets/images/logos";

interface IWalletProps {
    wallet: IWallet;
}

type Network = 'BITCOIN' | 'ETHEREUM' | 'OPTIMISM' | 'ARBITRUM' | 'SOLANA' | 'DOGECOIN';

function Wallet({wallet}: IWalletProps) {
    const shortAddress = `${wallet.address.substring(0, 6)}...${wallet.address.substring(wallet.address.length - 4)}`;

    const logo = logos[wallet.network as Network] || logos.question;

    return (
        <div className={`border border-port-four rounded-lg p-2 mb-2 w-1/3 mx-auto flex items-center`}>
            <img src={logo} alt="Avatar" className="w-12 h-12 rounded-full mr-4"/>
            <div>
                <h3 className="font-bold">{wallet.name} ({wallet.network})</h3>
                <p className="text-sm">{shortAddress}</p>
            </div>
        </div>
    );
}

export default Wallet;