package org.elias.model.sort;

import org.elias.model.WindFarm;

import java.util.Comparator;

public class TotalPerformanceComparator implements Comparator<WindFarm>
{
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        return Float.compare(windFarm2.getTotalPerformance(), windFarm1.getTotalPerformance());
    }
}
