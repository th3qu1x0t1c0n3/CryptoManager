import {PortfolioServerInstance} from "../App";
import {IWallet} from "../assets/models/BlockChain";

export class ChainService {
//     ETHER
    async getEtherPrice() {
        return PortfolioServerInstance.get(`/ether/price`).then((response) => {
            return response.data;
        });
    }

    async getEtherBalance(wallet: IWallet) {
        return PortfolioServerInstance.get(`/ether/balance?address=${wallet.address}&network=${wallet.network}`).then((response) => {
            return response.data;
        });
    }

    async getBalances(wallet: IWallet) {
        return PortfolioServerInstance.get(`/ether/balances?address=${wallet.address}&network=${wallet.network}`).then((response) => {
            return response.data;
        });
    }

    async getTransactions(wallet: IWallet) {
        return PortfolioServerInstance.get(`/ether/transactions?address=${wallet.address}&network=${wallet.network}`).then((response) => {
            return response.data;
        });
    }

    async getContractTransactions(wallet: IWallet, contract: string) {
        return PortfolioServerInstance.get(`/ether/transactions/${contract}?address=${wallet.address}&network=${wallet.network}`).then((response) => {
            return response.data;
        });
    }

    async getTransaction(wallet: IWallet, hash: string) {
        return PortfolioServerInstance.get(`/ether/transaction/${hash}?address=${wallet.address}&network=${wallet.network}`).then((response) => {
            return response.data;
        });
    }

}