import {PortfolioService} from "../../services/PortfolioService";
import {useState} from "react";
import FormInput from "../../assets/models/Form";
import {toast} from "react-toastify";

function WalletForm() {
    const portfolioService = new PortfolioService();

    const [walletForm, setWalletForm] = useState({
        id: -1,
        name: '',
        address: '',
        network: 'BITCOIN', // default network
    });
    const [walletFormInfo, setWalletFromInfo] = useState([
        new FormInput('name', 'text', 'Wallet Name', ''),
        new FormInput('address', 'text', 'address or XPUB', ''),
    ])
    const [networks, setNetworks] = useState([
        'BITCOIN',
        'ETHEREUM',
        'ARBITRUM',
        'OPTIMISM',
        'SOLANA',
        'DOGECOIN',
    ]);

    function handleWalletChange(e: any) {
        setWalletFromInfo(walletFormInfo.map((formInfo) => {
            if (formInfo.name === e.target.id)
                formInfo.warning = '';
            return formInfo;
        }))
        setWalletForm({...walletForm, [e.target.id]: e.target.value});
    }

    function handleSubmit(e: any) {
        e.preventDefault();
        console.log(walletForm)

        portfolioService.createWallet(walletForm)
            .then(response => {
                toast.success("Wallet created successfully!");
            })
            .catch(error => {
                toast.error(error.response?.data.message);
            });
    }

    function handleNetworkChange(e: any) {
        setWalletForm({...walletForm, network: e.target.value});
    }

    return (
        <form onSubmit={handleSubmit}>
            {
                walletFormInfo.map((formInfo) => {
                    return (
                        <div key={formInfo.name}>
                            <input className={"border border-gray-300 rounded-lg p-2 mb-2 w-1/3"}
                                   type={formInfo.type}
                                   id={formInfo.name}
                                   placeholder={formInfo.placeholder}
                                   onChange={handleWalletChange}
                            />
                            <p>{formInfo.warning}</p>
                        </div>
                    )
                })
            }
            <label className={"m-1 mb-0 block"}>Network</label>
            <select onChange={handleNetworkChange} className="border border-gray-300 rounded-lg p-2 mb-2 w-1/3 text-port-one">
                {networks.map((network) => (
                    <option value={network} key={network}>{network}</option>
                ))}
            </select>

            <button
                className={"block mx-auto border rounded transition ease-in duration-200 p-2 clickable hover:bg-port-five hover:border-port-one hover:text-port-one"}
                type="submit">
                Submit new wallet
            </button>
        </form>
    );
}

export default WalletForm;