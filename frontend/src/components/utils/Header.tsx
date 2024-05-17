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
            <h1 onClick={() => user === null ? navigate("/") : navigate("/u/transactions")}
                className="text-2xl font-bold clickable">Go home</h1>
            {
                user !== null ?
                    <div>
                        <button onClick={() => navigate("/u/transact")}
                                className="bg-port-gray text-port-blue px-4 py-2 rounded">Create Transaction
                        </button>
                        <button onClick={() => navigate("/u/profile")}>
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