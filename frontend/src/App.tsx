import React from 'react';
import './App.css';
import axios from "axios";
import Main from "./components/pages/Main";
import {BrowserRouter} from "react-router-dom";
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
    return (
        <div className="">
            <ToastContainer
                position="top-left"
                autoClose={3000}
                hideProgressBar={false}
                newestOnTop={true}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                pauseOnHover
                theme="dark"
            />

            <BrowserRouter>
                <div className="min-h-screen bg-port-one p-0 m-0">
                    <Main/>
                </div>
            </BrowserRouter>
        </div>
    );
}

export default App;

export const PortfolioServerInstance = axios.create({
    // baseURL: 'https://crypto.quixotic.date/api/v1/',
    baseURL: 'http://localhost:8085/api/v1/',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {}
});

// Blockchains API Instances
export const BtcApiInstance = axios.create({
    baseURL: 'https://blockchain.info/q',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {}
});
export const EthereumApiInstance = axios.create({
    baseURL: 'https://api.etherscan.io/api',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {
        apikey: 'S4R8CY3J4X4UT4CV2SD1AT5CVUNDMHEYEW'
    }
});
export const ArbitrumApiInstance = axios.create({
    baseURL: 'https://api.arbiscan.io/api',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {
        apikey: 'VGT8MHGKBMVG4XX6M5NGR5QDZNIXF2RFN3'
    }
});
export const OptimismApiInstance = axios.create({
    baseURL: 'https://api-optimistic.etherscan.io/api',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {
        apikey: 'AJQRMWNMB7CKZJ66741M4XTI38XKMTX42W'
    }
});
// Better to use `npm install @solana/web3.js` to access the Solana API
export const SolanaApiInstance = axios.create({
    baseURL: 'https://api.mainnet-beta.solana.com',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {}
});
export const DogeApiInstance = axios.create({
    baseURL: 'https://api.blockcypher.com/v1/doge/main',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {
        token: '62c6da236d834c7583a6d3dc4db627b9'
    }
});
