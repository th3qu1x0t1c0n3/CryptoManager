import {useNavigate} from "react-router-dom";
import {IUser} from "../../assets/models/Authentication";
import {PortfolioServerInstance} from "../../App";

interface IHeaderProps {
    user: IUser | null;
    setUser: (user: IUser | null) => void;
}

function Header({user, setUser}: IHeaderProps) {
    const navigate = useNavigate();
    const handleDisconnect = () => {
        setUser(null);
        navigate("/")
        PortfolioServerInstance.defaults.headers.common['Authorization'] = null;
        sessionStorage.removeItem('token');
    };

    return (
        <div className="flex justify-between items-center p-4 bg-port-blue font-semibold">
            <div onClick={() => user === null ? navigate("/") : navigate("/u/transactions")}>
                <img src="./../../../../btcLogo.png" alt="BTC Coin image" className={"w-12 inline-block"}/>
                <h1 className="text-2xl font-bold clickable inline-block ms-2">Go home</h1>
            </div>
            {
                user !== null ?
                    <div>
                        <button onClick={() => navigate("/u/transact")}
                                className="bg-port-gray text-port-blue px-4 py-2 rounded">Create Transaction
                        </button>
                        <button className="mx-4 bg-port-gray text-port-blue px-4 py-2 rounded" onClick={() => navigate("/u/profile")}>
                            Modify investing profile
                        </button>
                        <button onClick={handleDisconnect}
                                className="mx-4 bg-red-500 text-port-blue px-4 py-2 rounded">Disconnect
                        </button>
                    </div> :
                    <div>

                    </div>
            }
        </div>
    );
}

export default Header;