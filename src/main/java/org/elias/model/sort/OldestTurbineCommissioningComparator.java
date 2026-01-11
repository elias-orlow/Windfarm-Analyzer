package org.elias.model.sort;

import org.elias.model.WindFarm;
import org.elias.model.WindTurbineGroup;
import org.elias.res.constant.GeneralConstants;

import java.time.Year;
import java.util.Comparator;

/**
 * Comparator, der {@link WindFarm}-Objekte anhand des
 * aeltesten Inbetriebnahmejahres ihrer Windturbinen-Gruppen vergleicht.
 * <p>
 * Die Sortierung erfolgt aufsteigend nach dem aeltesten Jahr.
 */
public class OldestTurbineCommissioningComparator implements Comparator<WindFarm>
{
    /**
     * Vergleicht zwei Windparks anhand des aeltesten Inbetriebnahmejahres ihrer Windturbinen-Gruppen.
     *
     * @param windFarm1 der erste zu vergleichende Windpark.
     * @param windFarm2 der zweite zu vergleichende Windpark.
     * @return -1 wenn {@code windFarm1} ein frueheres aeltestes Jahr hat, 1 bei spaeterem Jahr, oder 0 bei Gleichheit.
     * @precondition beide Parameter sind nicht null und besitzen mindestens eine {@code WindTurbineGroup}.
     * @postcondition die beiden aeltesten Jahreswerte wurden verglichen und das Ergebnis zurueckgegeben.
     */
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {

        Year oldest1 = getOldestYear(windFarm1);
        Year oldest2 = getOldestYear(windFarm2);

        return oldest1.compareTo(oldest2);
    }


    /**
     * Ermittelt das aelteste Inbetriebnahmejahr aller Windturbinen-Gruppen eines Windparks.
     *
     * @param windFarm der Windpark, dessen aeltesten Jahr bestimmt wird.
     * @return das aelteste Jahr oder {@code Year.of(0)}, falls keine Gruppe vorhanden ist.
     * @precondition {@code windFarm} ist nicht null.
     * @postcondition es wurde das Minimum der vorhandenen Jahreswerte bestimmt und zurueckgegeben.
     */
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
