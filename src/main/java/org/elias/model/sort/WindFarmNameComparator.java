package org.elias.model.sort;

import org.elias.model.WindFarm;

import java.util.Comparator;

public class WindFarmNameComparator implements Comparator<WindFarm>
{
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        return windFarm1.getName().compareTo(windFarm2.getName());
    }
}
