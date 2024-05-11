import {IUser} from "../assets/models/Authentication";
import {Route, Routes} from "react-router-dom";
import PageNotFound from "./utils/PageNotFound";
import React from "react";
import Loading from "./utils/Loading";

interface IHomePageProps {
    setUser: (user: any) => void;
    user: IUser | null;
}

function HomePage({setUser, user}: IHomePageProps) {


    return (
        user === null ?
            <>
                <Loading/>
            </> :
            <div>
                <h1>Home Page</h1>
                <p>Welcome to the home page</p>
                <Routes>
                    <Route path="/*" element={<PageNotFound/>}/>
                </Routes>
            </div>
    );
}

export default HomePage;