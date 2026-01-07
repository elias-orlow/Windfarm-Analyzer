package org.elias.model.sort;

import org.elias.model.WindFarm;
import org.elias.model.WindTurbineGroup;
import org.elias.res.constant.GeneralConstants;

import java.util.Comparator;

public class WindTurbineCountComparator implements Comparator<WindFarm>
{
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        int firstWindfarmCount = GeneralConstants.EMPTY_INT_VARIABLE;
        int secondWindfarmCount = GeneralConstants.EMPTY_INT_VARIABLE;

        for (WindTurbineGroup windTurbineGroup : windFarm1.getWindTurbineGroups())
        {
            firstWindfarmCount += windTurbineGroup.getWindTurbines().size();
        }

        for (WindTurbineGroup windTurbineGroup : windFarm2.getWindTurbineGroups())
        {
            secondWindfarmCount += windTurbineGroup.getWindTurbines().size();
        }

        return Integer.compare(firstWindfarmCount, secondWindfarmCount);
    }
}
