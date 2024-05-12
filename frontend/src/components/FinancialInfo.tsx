import {PortfolioService} from "../services/PortfolioService";
import {toast} from "react-toastify";
import {useEffect, useState} from "react";
import axios from "axios";

function FinancialInfo() {
    const portfolioService = new PortfolioService();
    const cryptoValueUrl = "https://api.coinbase.com/v2/exchange-rates?currency=";
    const [currency, setCurrency] = useState<string>("CAD");
    const [holdings, setHoldings] = useState<{ [key: string]: number }>({"ETH": 1});
    const [coinValues, setCoinValues] = useState<{ [key: string]: number }>({});

    function formatDecimal(value: number | string): string {
        if (typeof value === "string") {
            value = parseFloat(value);
        }
        return Number(Math.abs(value).toFixed(10)).toString();
    }
    function formatDollars(value: number | string): string {
        if (typeof value === "string") {
            value = parseFloat(value);
        }
        return Number(Math.abs(value).toFixed(2)).toString();
    }

    useEffect(() => {
        portfolioService.getCoinBalances()
            .then((data) => {
                const sortedData = Object.entries(data).sort(([a], [b]) => {
                    const order = ["CAD", "USD", "BTC", "ETH", "BTCBULL", "ETHBULL", "SOL", "DOGE", "USDC"];
                    const aIndex = order.indexOf(a);
                    const bIndex = order.indexOf(b);

                    if (aIndex === -1 && bIndex === -1) {
                        return a.localeCompare(b);
                    }
                    if (aIndex === -1) {
                        return 1;
                    }
                    if (bIndex === -1) {
                        return -1;
                    }

                    return aIndex - bIndex;
                });

                setHoldings(Object.fromEntries(sortedData.map(([coin, value]) => [coin, Number(value)])));
                setCoinValues(Object.fromEntries(sortedData.map(([coin, value]) => [coin, Number(NaN)])))

                Object.keys(holdings).forEach(coin => getCoinValue(coin));

            })
            .catch((error) => {
                toast.error(error.response?.data.message);
                console.log(error);
            });
    }, []);

    useEffect(() => {
        Object.keys(holdings).forEach(coin => getCoinValue(coin));
    }, [holdings, currency]);

    function getCoinValue(coin: string) {
        if (coin === "CAD" || coin === "USD") {
            setCoinValues(prevValues => ({
                ...prevValues,
                [coin]: holdings[coin]
            }));
            return;
        }
        axios.get(cryptoValueUrl + coin)
            .then(response => {
                try {
                    setCoinValues(prevValues => ({
                        ...prevValues,
                        [coin]: holdings[coin] * response.data.data.rates[currency]
                    }));
                } catch (error) {
                    setCoinValues(prevValues => ({
                        ...prevValues,
                        [coin]: -1
                    }));

                    console.log(error);
                }
            });
    }

    function getTotalCoinValues() {
        let total = 0;
        for (let coin in coinValues) {
            let value = coinValues[coin];
            if (!isNaN(value) && (coin !== "CAD" && coin !== "USD")) {
                console.log(coin)
                total += value;
            }
        }
        return formatDollars(total);
    }
    return (
        <div>
            <h1 className={"text-5xl mb-2"}>User Info</h1>
            <h2 className={"text-3xl mb-2"}>Total Crypto Value: {getTotalCoinValues()}$</h2>
            <table className={"table-auto border-collapse border"}>
                <thead>
                <tr>
                    <th className={"px-4 py-2 border"}>Coin</th>
                    <th className={"px-4 py-2 border"}>Holding</th>
                    <th className={"px-4 py-2 border"}>Current Value</th>
                </tr>
                </thead>
                <tbody>
                {Object.entries(holdings).map(([coin, value]) => (
                    <tr key={coin}>
                        <td className={"border px-4 py-2"}>{coin}</td>
                        <td className={"border px-4 py-2"}>{formatDecimal(value)}{coin === "CAD" ? "$" : ""}</td>
                        <td className={"border px-4 py-2"}>{formatDollars(coinValues[coin])}$</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default FinancialInfo;