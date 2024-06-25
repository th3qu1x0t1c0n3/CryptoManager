
export interface ITransaction {
    id: number;
    toCoin: ICoinTransaction;
    fromCoin: ICoinTransaction;
    transactionDate: string;
    wallet: string;
    exchange: string;
    buy: boolean;
}

export interface ICoinTransaction {
    name: string;
    quantity: number;
    value: number;
    unitValue: number;
}

export interface ICoinBalance {
    name: string;
    holdings: number;
    moneyInvested: number;
    avgPrice: number;
    totalValue: number;
    currentPrice: number;
}

export interface IAllocation {
    id: number;
    coin: string;
    percentage: number;
    allocation: number;
    currentAllocation: number;
    userId: number;
}

export interface IKellyCriterion {
    nbProfit: number;
    nbLoss: number;
    totalReturn: number;
    totalWin: number;
    totalLoss: number;
    winRate: number;
    lossRate: number;
    riskRewardRatio: number;
    kellyCriterion: number;
}