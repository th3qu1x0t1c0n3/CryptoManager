import {useEffect, useState} from "react";
import {IKellyCriterion} from "../assets/models/Transaction";
import {PortfolioService} from "../services/PortfolioService";
import {toast} from "react-toastify";

function KellyCriterion() {
    const portfolioService = new PortfolioService();
    const [kellyCriterion, setKellyCriterion] = useState<IKellyCriterion>({
        nbProfit: 0,
        nbLoss: 0,
        totalReturn: 0,
        totalWin: 0,
        totalLoss: 0,
        winRate: 0,
        lossRate: 0,
        riskRewardRatio: 0,
        kellyCriterion: 0
    });

    useEffect(() => {
        portfolioService.getKellyCriterion()
            .then((response) => {
                setKellyCriterion(response);
            }).catch((error) => {
                toast.error(error.response?.data.message);
            }
        );

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
            <h1 className={"text-5xl my-3"}>Kelly Criterion</h1>
            <div className={"grid grid-cols-2 w-2/3 mx-auto"}>

                <div className={"col-span-2"}>
                    <div>
                        <h2 className={"text-3xl"}>Risk Reward Ratio</h2>
                        <p>{formatNumber(kellyCriterion.riskRewardRatio)}</p>
                    </div>
                    <div>
                        <h2 className={"text-3xl"}>Kelly</h2>
                        <p>{formatNumber(kellyCriterion.kellyCriterion)}</p>
                    </div>

                    <div>
                        <h2 className={"text-3xl"}>Total Return</h2>
                        <p>{formatNumber(kellyCriterion.totalReturn)}$</p>
                    </div>

                </div>

                <div className={"text-2xl"}>
                    <h1 className={"text-3xl"}>Wins</h1>
                    <div>
                        <div>
                            <h1>Total wins</h1>
                            <p>{formatNumber(kellyCriterion.totalWin)}</p>
                        </div>
                        <div>
                            <h1>Total profits</h1>
                            <p>{formatNumber(kellyCriterion.nbProfit)}$</p>
                        </div>
                        <div>
                            <h2 className={"text-2xl"}>Win Rate</h2>
                            <p>{formatNumber(kellyCriterion.winRate * 100)}%</p>
                        </div>
                    </div>

                </div>

                <div className={"text-2xl"}>
                    <h1 className={"text-3xl"}>Losses</h1>

                    <div>
                        <div>
                            <h1>Total losses</h1>
                            <p>{formatNumber(kellyCriterion.totalLoss)}</p>
                        </div>
                        <div>
                            <h1>Total losses</h1>
                            <p>{formatNumber(kellyCriterion.nbLoss)}$</p>
                        </div>

                        <div>
                            <h2 className={"text-2xl"}>Loss Rate</h2>
                            <p>{formatNumber(kellyCriterion.lossRate * 100)}%</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default KellyCriterion;