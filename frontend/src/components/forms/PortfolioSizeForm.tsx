import {useState} from "react";
import { PortfolioService } from "../../services/PortfolioService";
import {toast} from "react-toastify";

interface PortfolioSizeFormProps {
    portfolioSize: number;
}

function PortfolioSizeForm({portfolioSize}: PortfolioSizeFormProps) {
    const portfolioService = new PortfolioService();
    const [newPortfolioSize, setNewPortfolioSize] = useState<number>(portfolioSize);

    function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        portfolioService.updatePortfolioSize(newPortfolioSize)
            .then(() => {
                toast.success("Portfolio size updated")
                window.location.reload();
            })
            .catch((error) => {
                toast.error(error.response?.data.message)
            });
    }

    return (
        <form onSubmit={handleSubmit}>
            <label className={"block"}>Portfolio Size ($)</label>
            <input type="number" value={newPortfolioSize}
                   onChange={e => setNewPortfolioSize(Number(e.target.value))}
                   placeholder={portfolioSize.toString()}
                   min={1}
                   className="border-2 p-2 rounded text-port-one"/>
            <button type="submit" className="mt-2 border rounded transition ease-in duration-200 p-2 block mx-auto">
                Submit Portfolio size
            </button>
        </form>
    );
}

export default PortfolioSizeForm;