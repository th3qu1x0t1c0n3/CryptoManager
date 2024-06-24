import {PortfolioService} from "../../services/PortfolioService";
import {useState} from "react";
import FormInput from "../../assets/models/Form";

function WalletForm() {
    const portfolioService = new PortfolioService();

    const [walletForm, setWalletForm] = useState({
        name: '',
        address: '',
    });
    const [walletFormInfo, setWalletFromInfo] = useState([
        new FormInput('name', 'text', 'Wallet Name', ''),
        new FormInput('address', 'text', 'address or XPUB', ''),
    ])

    function handleWalletChange(e:any) {
        setWalletFromInfo(walletFormInfo.map((formInfo) => {
            if (formInfo.name === e.target.id)
                formInfo.warning = '';
            return formInfo;
        }))
        setWalletForm({...walletForm, [e.target.id]: e.target.value});
    }

    function handleSubmit(e: any) {
        e.preventDefault();

        const wallet: any = {
            name: walletForm.name.trim(),
            address: walletForm.address.trim(),
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            {
                walletFormInfo.map((formInfo) => {
                    return (
                        <div key={formInfo.name}>
                            {/*<label htmlFor={formInfo.name}>{formInfo.label}</label>*/}
                            <input className={"border border-gray-300 rounded-lg p-2 mb-2 w-1/3"}
                                type={formInfo.type}
                                id={formInfo.name}
                                placeholder={formInfo.placeholder}
                                // value={walletForm[formInfo.name]}
                                onChange={handleWalletChange}
                            />
                            <p>{formInfo.warning}</p>
                        </div>
                    )
                })
            }
            <button className={"border rounded transition ease-in duration-200 p-2 clickable hover:bg-port-five hover:border-port-one hover:text-port-one"}
                    type="submit">
                Add Wallet
            </button>
        </form>
    );
}

export default WalletForm;