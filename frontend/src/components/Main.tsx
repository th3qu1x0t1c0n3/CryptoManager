import {IUser} from "../assets/models/Authentication";
import React, {useState} from "react";
import {Route, Routes} from "react-router-dom";
import PageNotFound from "./utils/PageNotFound";
import AuthPage from "./AuthPage";
import HomePage from "./HomePage";

function Main() {
    const [user, setUser] = useState<IUser | null>(null);

    return (
        <div className={"text-port-white"}>
            <h1 className="text-4xl text-center ">Crypto Portfolio Manager</h1>
            <main className="min-h-screen font-semibold">
                <div className="flex">
                    <div className="w-11/12 mx-auto">
                        <Routes>
                            <Route path="/" element={<AuthPage setUser={setUser}/>}/>
                            <Route path="/u/*" element={<HomePage setUser={setUser} user={user}/>}/>
                            <Route path="*" element={<PageNotFound/>}/>
                        </Routes>
                    </div>
                </div>
            </main>
        </div>
    );
}

export default Main;