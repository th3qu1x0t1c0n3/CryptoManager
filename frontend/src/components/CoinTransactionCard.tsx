import {ICoinTransaction} from "../assets/models/Transaction";

interface CoinTransactionCardProps {
    coin: ICoinTransaction;
}
function CoinTransactionCard({coin}: CoinTransactionCardProps) {
    function formapecimal(value: number): string {
        let result = Number(value.toFixed(10)).toString();
        return result;
    }

    return (
        <div className={"flex flex-auto"}>
            <p className="text-sm mx-1 px-2">{coin.name}</p>
            <p className="text-sm mx-1 px-2">{formapecimal(coin.quantity)}</p>
            <p className="text-sm mx-1 px-2">{formapecimal(coin.value)}$</p>
            <p className="text-sm mx-1 px-2">{formapecimal(coin.unitValue)}$</p>
        </div>
    );


}

export default CoinTransactionCard;