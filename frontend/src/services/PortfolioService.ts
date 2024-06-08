import {PortfolioServerInstance} from "../App";
import {IsignIn, IsignUp, IUser} from "../assets/models/Authentication";

export class PortfolioService {

    // Authentication
    async signIn(user: IsignIn) {
        return PortfolioServerInstance.post<IUser>(`/port/auth/signin`, user)
            .then((response) => {
                return response.data;
            })
    }

    async signUp(user: IsignUp) {
        return PortfolioServerInstance.post<IUser>(`/port/auth/signup`, user)
            .then((response) => {
                return response.data;
            });
    }

    async getUser() {
        if (!PortfolioServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return PortfolioServerInstance.get<IUser>(`/port/auth/me`).then((response) => {
            return response.data;
        });
    }

//     Transactions
    async getTransactions() {
        return PortfolioServerInstance.get(`/port/transactions`).then((response) => {
            return response.data;
        });
    }

    async createTransaction(transactionDTO: any) {
        return PortfolioServerInstance.post(`/port/transaction`, transactionDTO).then((response) => {
            return response.data;
        });
    }

    async getTotalCoinBalances() {
        return PortfolioServerInstance.get(`/port/coinBalances`).then((response) => {
            return response.data;
        });
    }

    async getCoinBalances() {
        return PortfolioServerInstance.get(`/port/balance`).then((response) => {
            return response.data;
        });
    }

    async getCoinBalance(coin: string) {
        return PortfolioServerInstance.get(`/port/balance/${coin}`).then((response) => {
            return response.data;
        });
    }
    async getAveragePrice(coin: string) {
        return PortfolioServerInstance.get(`/port/averagePrice/${coin}`).then((response) => {
            return response.data;
        });
    }

    async getKellyCriterion() {
        return PortfolioServerInstance.get(`/port/kellyCriterion`).then((response) => {
            return response.data;
        });
    }

}
