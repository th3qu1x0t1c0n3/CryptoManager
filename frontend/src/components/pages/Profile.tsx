import { PortfolioService } from "../../services/PortfolioService";
import {useEffect, useState} from "react";
import AllocationsForm from "../forms/AllocationsForm";
import { IAllocation } from "../../assets/models/Transaction";

function Profile() {
    const porfolioService = new PortfolioService();
    const [openAccordion, setOpenAccordion] = useState(1);

    const [allocations, setAllocations] = useState<IAllocation[]>([]);

    useEffect(() => {
        porfolioService.getAllocations()
            .then((allocations) => {
                setAllocations(allocations);
                console.log("All:", allocations);
            })
            .catch((error) => {
                console.log(error);
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