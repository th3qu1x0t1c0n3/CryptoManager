
export interface ITransaction {
    id: number;
    toCoin: ICoinTransaction;
    fromCoin: ICoinTransaction;
    transactionDate: string;
    wallet: string;
    exchange: string;
}

export interface ICoinTransaction {
    name: string;
    quantity: number;
    value: number;
    unitValue: number;
}

export interface IKellyCriterion {
    profits: number;
    losses: number;
    profitLoss: number;
    totalWin: number;
    totalLoss: number;
    winRate: number;
    lossRate: number;
    riskRewardRatio: number;
    kellyCriterion: number;
}