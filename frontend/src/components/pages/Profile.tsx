import { PortfolioService } from "../../services/PortfolioService";
import {useEffect, useState} from "react";
import AllocationsForm from "../forms/AllocationsForm";
import { IAllocation } from "../../assets/models/Transaction";
import {toast} from "react-toastify";

function Profile() {
    const porfolioService = new PortfolioService();
    const [openAccordion, setOpenAccordion] = useState(1);

    const [allocations, setAllocations] = useState<IAllocation[]>([]);

    useEffect(() => {
        porfolioService.getAllocations()
            .then((allocations) => {
                setAllocations(allocations);
            })
            .catch((error) => {
                toast.error(error.response?.data.message)
            });
    }, []);

    return (
        <div>
            <h1>Profile</h1>

            <div className={"flex flex-auto"}>
                <div></div>
                <AllocationsForm allocations={allocations}/>
                <div></div>
            </div>


        </div>
    );
}

export default Profile;