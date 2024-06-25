export interface IWallet {
    id: number;
    name: string;
    address: string;
    network: string;
}

export interface IEtherPrice {
    ethbtc: string;
    ethbtc_timestamp: string;
    ethusd: string;
    ethusd_timestamp: string;
}

export interface ITransaction {
    blockNumber: string;
    timeStamp: string;
    hash: string;
    nonce: string;
    blockHash: string;
    contractAddress: string;
    from: string;
    to: string;
    value: string;
    tokenName: string;
    tokenSymbol: string;
    tokenDecimal: string;
    transactionIndex: string;
    gas: string;
    gasPrice: string;
    gasUsed: string;
    cumulativeGasUsed: string;
    input: string;
    confirmations: string;
    gasPriceBid: string;
    isError: string;
    txreceipt_status: string;
    methodId: string;
    functionName: string;
    accessList: string;
    maxFeePerGas: string;
    maxPriorityFeePerGas: string;
    type: string;
    chainId: string;
    v: string;
    r: string;
    s: string;
    yParity: string;
}

export interface IToken {
    balance: number;
    tokenName: string;
    tokenSymbol: string;
    contractAddress: string;
    tokenDecimal: string;
}