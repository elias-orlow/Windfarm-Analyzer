package org.elias.model.sort;

import org.elias.model.WindFarm;
import org.elias.model.WindTurbineGroup;
import org.elias.res.constant.GeneralConstants;

import java.util.Comparator;

/**
 * Comparator, der {@link WindFarm}-Objekte anhand der Gesamtanzahl ihrer Windturbinen vergleicht.
 * <p>
 * Die Sortierung erfolgt aufsteigend.
 */
public class WindTurbineCountComparator implements Comparator<WindFarm>
{
    /**
     * Vergleicht zwei Windparks anhand der Gesamtzahl ihrer Windturbinen.
     *
     * @param windFarm1 der erste zu vergleichende Windpark.
     * @param windFarm2 der zweite zu vergleichende Windpark.
     * @return -1 wenn {@code windFarm1} weniger Turbinen besitzt,
     * 1 bei mehr Turbinen, oder 0 bei Gleichheit.
     * @precondition beide Parameter sind nicht null und besitzen {@link WindTurbineGroup}
     * mit Listen aus {@link org.elias.model.WindTurbineType}.
     * @postcondition die Gesamtanzahl der Turbinen wurde ermittelt, verglichen und das Ergebnis zurueckgegeben.
     */
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
