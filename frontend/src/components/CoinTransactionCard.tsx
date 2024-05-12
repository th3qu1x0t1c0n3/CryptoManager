import {ICoinTransaction} from "../assets/models/Transaction";

interface CoinTransactionCardProps {
    coin: ICoinTransaction;
}
function CoinTransactionCard({coin}: CoinTransactionCardProps) {
    function formatDecimal(value: number): string {
        let result = Number(value.toFixed(10)).toString();
        return result;
    }

    return (
        <tr className={""}>
            <td className="text-sm mx-1 px-2">{coin.name}</td>
            <td className="text-sm mx-1 px-2">{formatDecimal(coin.quantity)}</td>
            <td className="text-sm mx-1 px-2">{formatDecimal(coin.value)}$</td>
            <td className="text-sm mx-1 px-2">{formatDecimal(coin.unitValue)}$</td>
        </tr>
    );


}

export default CoinTransactionCard;