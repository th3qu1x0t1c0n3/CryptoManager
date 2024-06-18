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

            <AllocationsForm allocations={allocations} />

            {/*<div className="w-full max-w-md mx-auto">*/}
            {/*    {[1, 2, 3].map((item) => (*/}
            {/*        <div key={item} className="border-b border-gray-200">*/}
            {/*            <h2 className="px-4 py-2">*/}
            {/*                <button*/}
            {/*                    className="w-full text-left"*/}
            {/*                    onClick={() => setOpenAccordion(openAccordion === item ? 0 : item)}*/}
            {/*                >*/}
            {/*                    Accordion {item}*/}
            {/*                </button>*/}
            {/*            </h2>*/}
            {/*            {openAccordion === item && (*/}
            {/*                <div className="px-4 py-2">*/}
            {/*                    Content for Accordion {item}*/}
            {/*                </div>*/}
            {/*            )}*/}
            {/*        </div>*/}
            {/*    ))}*/}
            {/*</div>*/}

        </div>
    );
}

export default Profile;