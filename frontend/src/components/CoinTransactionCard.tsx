import {ICoinTransaction} from "../assets/models/Transaction";

interface CoinTransactionCardProps {
    coin: ICoinTransaction;
}
function CoinTransactionCard({coin}: CoinTransactionCardProps) {
    return (
        <>
            <p className="text-sm mx-1">{coin.name}</p>
            <p className="text-sm mx-1">{coin.quantity}</p>
            <p className="text-sm mx-1">{coin.value}$</p>
            <p className="text-sm mx-1">{coin.unitValue}$</p>
        </>
    );


}

export default CoinTransactionCard;