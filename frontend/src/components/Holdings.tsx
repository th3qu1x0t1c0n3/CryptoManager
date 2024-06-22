import {PortfolioService} from "../services/PortfolioService";
import {toast} from "react-toastify";
import {useEffect, useState} from "react";
import axios from "axios";
import {IAllocation, ICoinBalance} from "../assets/models/Transaction";

function Holdings() {
    const portfolioService = new PortfolioService();
    const cryptoValueUrl = "https://api.coinbase.com/v2/exchange-rates?currency=";
    const [balance, setBalance] = useState<ICoinBalance[]>([]);
    const [currency, setCurrency] = useState<string>("CAD");

    useEffect(() => {
        portfolioService.getAllocations()
            .then((allocations: IAllocation[]) => {
                portfolioService.getTotalCoinBalances()
                    .then((coinBalances) => {
                        // const order = ["CAD", "USD", "BTC", "ETH", "BTCBULL", "ETHBULL", "SOL", "DOGE", "USDC"];
                        const order = ["CAD", ...allocations
                            .sort((a: IAllocation, b: IAllocation) => b.percentage - a.percentage)
                            .map(allocation => allocation.coin), "USD"];
                        const sortedData = coinBalances.sort((a: ICoinBalance, b: ICoinBalance) => {
                            const aIndex = order.indexOf(a.name);
                            const bIndex = order.indexOf(b.name);

                            if (aIndex === -1 && bIndex === -1) {
                                return a.name.localeCompare(b.name);
                            }
                            if (aIndex === -1) {
                                return 1;
                            }
                            if (bIndex === -1) {
                                return -1;
                            }

                            return aIndex - bIndex;
                        });
                        setBalance(sortedData);
                        balance.forEach(coin => getCoinValue(coin));
                    })
                    .catch((error) => {
                        toast.error(error.response?.data.message);
                    });
            }).catch((error) => {
            toast.error(error.response?.data.message);
        });
    }, []);

    useEffect(() => {
        if (balance.length === 0) return;
        balance.forEach(coin => {
            if (coin.name === "CAD" || coin.name === "USD")
                return;
            if (coin.currentPrice !== 1)
                return;
            getCoinValue(coin);
        });
    }, [balance]);

    function getCoinValue(coin: ICoinBalance) {
        if (coin.name === "CAD" || coin.name === "USD") {
            setBalance(prevValues => prevValues.map(item => item.name === coin.name ? {
                ...item,
                currentPrice: coin.holdings
            } : item));
            return;
        }
        axios.get(cryptoValueUrl + coin.name)
            .then(response => {
                const newCoinValue = response.data.data.rates[currency] * 1;
                setBalance(prevValues => prevValues.map(item => item.name === coin.name ? {
                    ...item,
                    currentPrice: newCoinValue
                } : item));
            })
            .catch(() => {
                setBalance(prevValues => prevValues.map(item => item.name === coin.name ? {
                    ...item,
                    currentPrice: coin.totalValue
                } : item));
            });


        if (isNaN(coin.currentPrice)) {
            setBalance(prevValues => prevValues.map(item => item.name === coin.name ? {
                ...item,
                currentPrice: coin.totalValue
            } : item));
        }
    }

    function getTotalCoinValues() {
        let total = 0;

        balance.map((coin) => {
            if (coin.name === "CAD" || coin.name === "USD") {
                // total += coin.holdings;
            } else {
                if (isNaN(coin.currentPrice)) {
                    total += coin.holdings * coin.avgPrice;
                } else {
                    total += coin.holdings * coin.currentPrice;
                }
            }
        })

        return formatNumber(total);
    }

    function formatNumber(num: number): string {
        const formatter = new Intl.NumberFormat('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
            useGrouping: true,
        });

        return formatter.format(num).replace(/,/g, ' ');
    }

    return (
        <div className={"text-center"}>
            <h2 className={"text-3xl my-3"}>Total Crypto Value: {getTotalCoinValues()}$</h2>
            <table className={"table-auto border-collapse border mx-auto"}>
                <thead>
                <tr>
                    <th className={"px-4 py-2 border"}>Coin</th>
                    <th className={"px-4 py-2 border"}>Holding</th>
                    <th className={"px-4 py-2 border"}>Money in</th>
                    <th className={"px-4 py-2 border"}>Bought Value</th>
                    <th className={"px-4 py-2 border"}>Current Value</th>
                </tr>
                </thead>
                <tbody className={"text-end"}>
                {balance.filter((coin) => coin.holdings >= 0.00001 || coin.holdings <= 0)
                    .map((coin) => (
                        <tr key={coin.name}>
                            <td className={"border px-4 py-2"}>{coin.name}</td>
                            <td className={"border px-4 py-2"}>{formatNumber(coin.holdings)}</td>
                            <td className={"border px-4 py-2"}>{formatNumber(coin.moneyInvested)}$</td>
                            <td className={"border px-4 py-2"}>{formatNumber(coin.avgPrice)}</td>
                            {
                                coin.name === "CAD" || coin.name === "USD" || isNaN(coin.currentPrice) ? (
                                    <td className={"border px-4 py-2"}>{formatNumber(coin.holdings * coin.avgPrice)}$</td>
                                ) : (
                                    <td className={"border px-4 py-2"}>{formatNumber(coin.holdings * coin.currentPrice)}$</td>
                                )
                            }
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default Holdings;
