package org.elias.model.sort;

import org.elias.model.WindFarm;
import org.elias.model.WindTurbineGroup;
import org.elias.res.constant.GeneralConstants;

import java.time.Year;
import java.util.Comparator;

/**
 * Comparator, der {@link WindFarm}-Objekte anhand des
 * juengsten Inbetriebnahmejahres ihrer Windturbinen-Gruppen vergleicht.
 * <p>
 * Die Sortierung erfolgt aufsteigend nach dem neuesten Jahr.
 */
public class LatestTurbineCommissioningComparator implements Comparator<WindFarm>
{
    /**
     * Vergleicht zwei Windparks anhand des juengsten Inbetriebnahmejahres ihrer Windturbinen-Gruppen.
     *
     * @param windFarm1 der erste zu vergleichende Windpark.
     * @param windFarm2 der zweite zu vergleichende Windpark.
     * @return -1 wenn {@code windFarm1} ein frueheres juengstes Jahr hat, 1 bei spaeterem Jahr, oder 0 bei Gleichheit.
     * @precondition beide Parameter sind nicht null und besitzen mindestens eine {@code WindTurbineGroup}.
     * @postcondition die beiden juengsten Jahreswerte wurden verglichen und das Ergebnis zurueckgegeben.
     */
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {

        Year latest1 = getLatestYear(windFarm1);
        Year latest2 = getLatestYear(windFarm2);

        return latest1.compareTo(latest2);
    }


    /**
     * Ermittelt das juengste Inbetriebnahmejahr aller Windturbinen-Gruppen eines Windparks.
     *
     * @param windFarm der Windpark, dessen juengstes Jahr bestimmt wird.
     * @return das juengste Jahr oder {@code Year.of(0)}, falls keine Gruppe vorhanden ist.
     * @precondition {@code windFarm} ist nicht null.
     * @postcondition es wurde das Maximum der vorhandenen Jahreswerte bestimmt und zurueckgegeben.
     */
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
