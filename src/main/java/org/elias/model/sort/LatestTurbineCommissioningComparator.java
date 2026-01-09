package org.elias.model.sort;

import org.elias.model.WindFarm;
import org.elias.model.WindTurbineGroup;
import org.elias.res.constant.GeneralConstants;

import java.time.Year;
import java.util.Comparator;

public class LatestTurbineCommissioningComparator implements Comparator<WindFarm>
{

    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {

        Year latest1 = getLatestYear(windFarm1);
        Year latest2 = getLatestYear(windFarm2);

        return latest1.compareTo(latest2);
    }

    private Year getLatestYear (WindFarm windFarm)
    {
        Year latestYear = null;

        for (WindTurbineGroup group : windFarm.getWindTurbineGroups())
        {
            Year year = group.getManufactureYear();

            if (latestYear == null || year.isAfter(latestYear))
            {
                latestYear = year;
            }
        }

        return latestYear != null ? latestYear : Year.of(GeneralConstants.INT_ZERO);
    }
}
