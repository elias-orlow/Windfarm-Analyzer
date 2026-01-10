package org.elias.model.sort;

import org.elias.model.WindFarm;

import java.util.Comparator;

public class IdComparator implements Comparator<WindFarm>
{
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        return Integer.compare(windFarm1.getWindTurbineGroups().getFirst().getID(), windFarm2.getWindTurbineGroups().getFirst().getID());
    }
}
