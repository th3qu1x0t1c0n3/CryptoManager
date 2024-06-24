import { PortfolioService } from "../../services/PortfolioService";
import {useEffect, useState} from "react";
import AllocationsForm from "../forms/AllocationsForm";
import { IAllocation } from "../../assets/models/Calculated";
import {toast} from "react-toastify";
import PortfolioSizeForm from "../forms/PortfolioSizeForm";
import {IUser} from "../../assets/models/Authentication";

interface IProfileProps {
    user: IUser;
}
function Profile({user}: IProfileProps) {
    const porfolioService = new PortfolioService();

    return (
        <div className={"text-center"}>
            <h1 className={"text-3xl"}>Profile</h1>

        </div>
    );
}

export default Profile;