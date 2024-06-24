import {ICoinTransaction} from "../assets/models/Calculated";

interface CoinTransactionCardProps {
    coin: ICoinTransaction;
}
function CoinTransactionCard({coin}: CoinTransactionCardProps) {
    function formatDecimal(value: number): string {
        let result = Number(value.toFixed(10)).toString();
        return result;
    }

    return (
        <div className={"grid grid-cols-4 w-full"}>
        {/*<div className={"flex flex-auto"}>*/}
            <p className="text-sm mx-1 px-2">{coin.name}</p>
            <p className="text-sm mx-1 px-2">{formatDecimal(coin.quantity)}</p>
            <p className="text-sm mx-1 px-2">{formatDecimal(coin.value)}$</p>
            <p className="text-sm mx-1 px-2">{formatDecimal(coin.unitValue)}$</p>
        </div>
    );


}

export default CoinTransactionCard;