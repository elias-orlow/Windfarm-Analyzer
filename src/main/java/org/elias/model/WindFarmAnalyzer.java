package org.elias.model;

import org.elias.model.sort.LatitudeComparator;
import org.elias.model.sort.TotalPerformanceComparator;
import org.elias.model.sort.WindTurbineCountComparator;
import org.elias.res.constant.GeneralConstants;

public class WindFarmAnalyzer
{
    WindFarmRepository windFarmRepository = null;

    public WindFarmAnalyzer (WindFarmRepository germanWindFarms)
    {
        this.windFarmRepository = germanWindFarms;
    }

    public WindFarm findSouthernmostWindfarm ()
    {
        this.windFarmRepository.getGermanWindFarms().sort(new LatitudeComparator());

        return this.windFarmRepository.getGermanWindFarms().getFirst();
    }

    public WindFarm findHighestPerformance ()
    {
        this.windFarmRepository.getGermanWindFarms().sort(new TotalPerformanceComparator());

        return this.windFarmRepository.getGermanWindFarms().getFirst();
    }

    public WindFarm findMostWindturbine ()
    {
        this.windFarmRepository.getGermanWindFarms().sort(new WindTurbineCountComparator().reversed());

        return this.windFarmRepository.getGermanWindFarms().getFirst();
    }

    public float calculateTotalPerformanceOfAllWindFarms ()
    {
        float totalPerformance = GeneralConstants.EMPTY_FLOAT_VARIABLE;

        for (WindFarm currentWindfarm : windFarmRepository.getGermanWindFarms())
        {
            totalPerformance += currentWindfarm.getTotalPerformance();
        }

        return totalPerformance;
    }
}
