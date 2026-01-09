package org.elias.model.sort;

import org.elias.model.WindFarm;
import org.elias.model.WindTurbineGroup;
import org.elias.res.constant.GeneralConstants;

import java.time.Year;
import java.util.Comparator;

public class OldestTurbineCommissioningComparator implements Comparator<WindFarm>
{

    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {

        Year oldest1 = getOldestYear(windFarm1);
        Year oldest2 = getOldestYear(windFarm2);

        return oldest1.compareTo(oldest2);
    }

    private Year getOldestYear (WindFarm windFarm)
    {
        Year oldestYear = null;

        for (WindTurbineGroup group : windFarm.getWindTurbineGroups())
        {
            Year year = group.getManufactureYear();

            if ((oldestYear == null || year.isBefore(oldestYear)) && year.getValue() != GeneralConstants.INT_ZERO)
            {
                oldestYear = year;
            }
        }

        return oldestYear != null ? oldestYear : Year.of(GeneralConstants.INT_ZERO);
    }
}
