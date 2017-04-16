package quantour.po;

import quantour.data.DataClass;

import java.util.List;

/**
 * Created by dell on 2017/4/16.
 */
public class FormativeNHoldingPO extends DataClass{
    private List<Double> overProfit;
    private List<Double> winChance;

    public FormativeNHoldingPO(List<Double> overProfit, List<Double> winChance) {
        this.overProfit = overProfit;
        this.winChance = winChance;
    }

    public List<Double> getOverProfit() {
        return overProfit;
    }

    public List<Double> getWinChance() {
        return winChance;
    }
}
