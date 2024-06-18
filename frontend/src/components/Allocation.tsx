import {PortfolioService} from "../services/PortfolioService";
import {useEffect, useState} from "react";
import {IAllocation} from "../assets/models/Transaction";
import {IUser} from "../assets/models/Authentication";
import {toast} from "react-toastify";

interface IAllocationProps {
    user: IUser;
}
function Allocation({user}: IAllocationProps) {
    const portfolioService = new PortfolioService();
    const [allocations, setAllocations] = useState<IAllocation[]>([])

    useEffect(() => {
        portfolioService.getAllocations()
            .then((allocations) => {
                setAllocations(allocations)
            })
            .catch((error) => {
                toast.error(error.response?.data.message)
            });
    }, []);

    return (
        <div className={"text-center"}>
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
                {allocations.map((allocation) => (
                    <tr key={allocation.id}>
                        <td className={"border px-4 py-2"}>{allocation.coin}</td>
                        <td className={"border px-4 py-2"}>{allocation.percentage}%</td>
                        <td className={"border px-4 py-2"}>{allocation.allocation}$</td>
                        <td className={"border px-4 py-2"}>{allocation.currentAllocation}$</td>
                        <td className={"border px-4 py-2"}>{allocation.allocation - allocation.currentAllocation}$</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
}

export default Allocation;