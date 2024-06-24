import {PortfolioService} from "../services/PortfolioService";
import {useEffect, useState} from "react";
import {IAllocation} from "../assets/models/Calculated";
import {IUser} from "../assets/models/Authentication";
import {toast} from "react-toastify";
import TransactionForm from "./forms/TransactionForm";
import AllocationsForm from "./forms/AllocationsForm";
import PortfolioSizeForm from "./forms/PortfolioSizeForm";

interface IAllocationProps {
    user: IUser;
}
function Allocation({user}: IAllocationProps) {
    const portfolioService = new PortfolioService();
    const [allocations, setAllocations] = useState<IAllocation[]>([])
    const [showForm, setShowForm] = useState<boolean>(false);

    useEffect(() => {
        portfolioService.getAllocations()
            .then((allocations) => {
                const sortedAllocations = allocations.sort((a: IAllocation, b: IAllocation) => b.percentage - a.percentage);
                setAllocations(sortedAllocations);
            })
            .catch((error) => {
                toast.error(error.response?.data.message)
            });
    }, []);
    function formatNumber(num: number): string {
        const formatter = new Intl.NumberFormat('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
            useGrouping: true,
        });

        return formatter.format(num).replace(/,/g, ' ');
    }

    return (
        <div className={"text-center"}>
            <div className={"my-2 text-center"}>
                <button onClick={() => setShowForm(!showForm)}
                        className={"border rounded transition ease-in duration-200 p-2 clickable hover:bg-port-five hover:border-port-one hover:text-port-one"}>
                    Add Allocation
                </button>
            </div>

            {
                showForm &&
                <div className={"grid grid-cols-3"}>
                    <PortfolioSizeForm portfolioSize={user.portfolioSize}/>
                    <AllocationsForm allocations={allocations} setAllocations={setAllocations}/>
                    <div></div>
                </div>
            }
            <h2 className={"text-3xl my-3"}>Allocations</h2>
            <h1 className={"text-2xl my-3"}>Optimal portfolio: {user.portfolioSize}$</h1>
            <table className={"table-auto border-collapse border mx-auto"}>
                <thead>
                <tr>
                    <th className={"px-4 py-2 border"}>Coin</th>
                    <th className={"px-4 py-2 border"}>Percentage</th>
                    <th className={"px-4 py-2 border"}>Allocation</th>
                    <th className={"px-4 py-2 border"}>Current Allocation</th>
                    <th className={"px-4 py-2 border"}>Rebalance</th>
                </tr>
                </thead>
                <tbody className={"text-end"}>
                {allocations.sort().map((allocation) => (
                    <tr key={allocation.id}>
                        <td className={"border px-4 py-2"}>{allocation.coin}</td>
                        <td className={"border px-4 py-2"}>{formatNumber(allocation.percentage)}%</td>
                        <td className={"border px-4 py-2"}>{formatNumber(allocation.allocation)}$</td>
                        <td className={"border px-4 py-2"}>{formatNumber(allocation.currentAllocation)}$</td>
                        <td className={"border px-4 py-2"}>{formatNumber(allocation.allocation - allocation.currentAllocation)}$</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
}

export default Allocation;