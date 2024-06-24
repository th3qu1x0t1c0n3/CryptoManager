import {useNavigate} from "react-router-dom";
import {IUser} from "../../assets/models/Authentication";
import {PortfolioServerInstance} from "../../App";
import {useState} from "react";

interface IHeaderProps {
    user: IUser | null;
    setUser: (user: IUser | null) => void;
}

function Header({user, setUser}: IHeaderProps) {
    const navigate = useNavigate();
    const [activePage, setActivePage] = useState<string>('');
    const pages = ['portfolio', 'wallets', 'transactions', 'allocations', 'kelly'];

    const handleNavigation = (page: string) => {
        setActivePage(page);
        navigate(`/u/${page}`);
    };

    return (
        <div className="flex justify-between items-center p-4 bg-port-two font-semibold">
            <div onClick={() => user === null ? navigate("/") : navigate("/u/transactions")}>
                <img src="./../../../../btcLogo.png" alt="BTC Coin image" className={"w-12 inline-block"}/>
                <h1 className="text-2xl font-bold clickable inline-block ms-2">CPM</h1>
            </div>
            {
                user !== null &&
                <>
                    <div className="flex justify-center">
                        {
                            pages.map((page) => (
                                <button onClick={() => handleNavigation(page)}
                                        className={`${activePage === page ? 'bg-port-three' : 'bg-port-four'} text-port-two mx-1 px-4 py-2 rounded`}>
                                    {page.charAt(0).toUpperCase() + page.slice(1)}
                                </button>
                            ))
                        }
                    </div>
                    <div>
                        <button onClick={() => {
                            navigate("/u/profile")
                            setActivePage("profile")
                        }}
                                className={`${activePage === "profile" ? 'bg-port-three' : 'bg-port-four'} mx-4 text-port-two px-4 py-2 rounded`}>
                            Profile
                        </button>
                    </div>
                </>
            }
        </div>
    );
}

export default Header;