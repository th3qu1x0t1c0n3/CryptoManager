import React from 'react';
import './App.css';
import axios from "axios";
import Main from "./components/Main";
import {BrowserRouter} from "react-router-dom";

function App() {
    return (
        <div className="">
            <BrowserRouter>
                <div className="min-h-screen bg-port-dark p-0 m-0">
                    <Main/>
                </div>
            </BrowserRouter>
        </div>
    );
}

export default App;

export const PortfolioServerInstance = axios.create({
    baseURL: 'http://localhost:8080/api/v1/',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {}
});
