import {IWallet} from "../assets/models/BlockChain";
import logos from "../assets/images/logos";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCopy} from "@fortawesome/free-solid-svg-icons";
import {toast} from "react-toastify";

interface IWalletProps {
    wallet: IWallet;
}

type Network = 'BITCOIN' | 'ETHEREUM' | 'OPTIMISM' | 'ARBITRUM' | 'SOLANA' | 'DOGECOIN';

function Wallet({wallet}: IWalletProps) {
    const shortAddress = `${wallet.address.substring(0, 6)}...${wallet.address.substring(wallet.address.length - 4)}`;

    const logo = logos[wallet.network as Network] || logos.question;

    function copyToClipboard() {
        navigator.clipboard.writeText(wallet.address).then(r => toast.success("Address copied to clipboard"));
    }

    return (
        <div className={`border border-port-four rounded-lg p-2 mb-2 w-1/3 mx-auto flex items-center`}>
            <img src={logo} alt="Avatar" className="w-12 h-12 rounded-full mr-4"/>
            <div>
                <h3 className="font-bold">{wallet.name} ({wallet.network})</h3>
                <div className={"clickable"} onClick={copyToClipboard}>
                    <p className="text-sm inline">{shortAddress}</p>
                    <FontAwesomeIcon icon={faCopy} className="text-port-five inline mx-1"/>
                </div>
            </div>
        </div>
    );
}

export default Wallet;