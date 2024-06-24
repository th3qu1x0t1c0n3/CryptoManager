import {IWallet} from "../assets/models/BlockChain";
import {useState} from "react";
import FormInput from "../assets/models/Form";

interface IWalletProps {
    wallet: IWallet;
}
function Wallet({wallet}: IWalletProps) {


    // TODO: Make accordion type component
    return (
        <div>
            <h3>{wallet.name}</h3>
            <p>{wallet.address}</p>
            <p>{wallet.network}</p>
        </div>
    );
}

export default Wallet;