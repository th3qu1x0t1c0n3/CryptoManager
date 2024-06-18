import {IAllocation} from "../../assets/models/Transaction";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import { PortfolioService } from "../../services/PortfolioService";
import {useEffect, useState} from "react";
import {toast} from "react-toastify";

interface AllocationsFormProps {
    allocations: IAllocation[];
}
function AllocationsForm({allocations}: AllocationsFormProps) {
    const portfolioService = new PortfolioService();
    const MT_Allocation: IAllocation = {id: 1, coin: "", percentage: 1, allocation: 0, currentAllocation: 0, userId: 1};
    const [newAllocations, setNewAllocations] = useState<IAllocation[]>([MT_Allocation]);

    useEffect(() => {
        if (allocations.length !== 0) {
            setNewAllocations(allocations);
        } else {
            setNewAllocations([MT_Allocation]);
        }
    }, [allocations]);

    function handleAllocationChange(index: number, value: string) {
        const tmpAllocations = [...newAllocations];
        tmpAllocations[index].coin = value;
        setNewAllocations(tmpAllocations);
    }
    function handleAllocationPctChange(index: number, value: number) {
        const tmpAllocations = [...newAllocations];
        tmpAllocations[index].percentage = value;
        setNewAllocations(tmpAllocations);
    }
    function removeAllocation(index: number) {
        const tmp = [...newAllocations];
        tmp.splice(index, 1);
        setNewAllocations(tmp)
    }
    function addAllocation() {
        setNewAllocations([...newAllocations, MT_Allocation])
    }

    function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        newAllocations.filter((allocation) => !allocations.includes(allocation)).map((allocation) => {
            if (allocation.coin !== "" || allocation.percentage !== 0) {
                portfolioService.createAllocation(allocation)
                    .then(() => {
                        toast.success("Allocation added")
                    })
                    .catch((error) => {
                        toast.error(error.response?.data.message)
                    });
            }
        });
        // newAllocations.filter((allocation) => allocations.includes(allocation)).map((allocation) => {
        //     portfolioService.updateAllocation(allocation)
        //         .then(() => {
        //             toast.success("Allocation updated")
        //         })
        //         .catch((error) => {
        //             toast.error(error.response?.data.message)
        //         });
        // });
    }

    return (
        <div className={"text-center w-full"}>

            <form onSubmit={handleSubmit}>
                <div className="flex flex-col space-y-1 mx-auto">
                    <div className={""}>
                        <label className={"mx-10"}>Coin name</label>
                        <label className={"mx-10"}>Percentage allocation</label>
                    </div>

                    {newAllocations.map((allocation, index) => (
                        <div key={index} className="flex space-x-2 items-center">
                            <input type="text" value={allocation.coin}
                                   onChange={e => handleAllocationChange(index, e.target.value)}
                                   placeholder={"RNT"}
                                   className="border-2 p-2 rounded flex-grow text-port-dark"/>
                            <input type="number" value={allocation.percentage}
                                   onChange={e => handleAllocationPctChange(index, Number(e.target.value))}
                                   placeholder={allocation.percentage.toString()}
                                   min={1} max={100}
                                   className="border-2 p-2 rounded flex-grow text-port-dark"/>

                            <button type="button" onClick={() => removeAllocation(index)}
                                    className="border-2 rounded transition ease-in duration-200 p-1">
                                <span className={"hidden lg:inline-block"}>Delete</span>
                                <FontAwesomeIcon icon={faTrash} className={"mx-1"}/>
                            </button>
                        </div>
                    ))}
                    <button type="button" onClick={addAllocation}
                            className="border rounded transition ease-in duration-200 p-2">
                        Add Allocation
                    </button>
                </div>
                <button type="submit" className="mt-5 border rounded transition ease-in duration-200 p-2">
                    Submit Allocations
                </button>
            </form>
        </div>
    );
}

export default AllocationsForm;