import {faCopy} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {toast} from "react-toastify";

interface IWalletProps {
    address: string;
}

function WalletAddress({address}: IWalletProps) {
    function copyToClipboard() {
        navigator.clipboard.writeText(address).then(r => toast.success("Address copied to clipboard"));
    }

    function getShortAddress(address: string) {
        return address.substring(0, 6) + "..." + address.substring(address.length - 4);
    }


    return (
        <div className={"clickable"} onClick={copyToClipboard}>
            <p className="text-sm inline">{getShortAddress(address)}</p>
            <FontAwesomeIcon icon={faCopy} className="text-port-five inline mx-1"/>
        </div>
    );
}

export default WalletAddress;