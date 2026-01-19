package org.elias.model.sort;

import org.elias.model.WindFarm;
import org.elias.res.constant.GeneralConstants;

import java.util.Collections;
import java.util.Comparator;

/**
 * Comparator, der {@link WindFarm}-Objekte anhand der Anzahl ihrer Projektleiter vergleicht.
 * <p>
 * Die Sortierung erfolgt aufsteigend. Bei gleicher Anzahl wird zusaetzlich alphabetisch verglichen.
 */
public class ManagerCountComparator implements Comparator<WindFarm>
{
    /**
     * Vergleicht zwei Windparks anhand der Anzahl ihrer Projektleiter.
     * <p>
     * Bei gleicher Anzahl wird der alphabetisch erste Projektleiter verglichen.
     *
     * @param windFarm1 der erste zu vergleichende Windpark.
     * @param windFarm2 der zweite zu vergleichende Windpark.
     * @return -1 wenn {@code windFarm1} weniger Manager hat, 1 bei mehr Managern, oder 0 bei Gleichheit.
     * @precondition beide Parameter sind nicht null und besitzen eine Liste von Projektleitern (darf auch leer sein).
     * @postcondition die Managerlisten wurden sortiert, verglichen und die relevanten Ergebnisse zurueckgegeben.
     */
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        int result = Integer.compare(windFarm1.getProjectManagers().size(), windFarm2.getProjectManagers().size());
        Collections.sort(windFarm1.getProjectManagers());
        Collections.sort(windFarm2.getProjectManagers());

        if (result == GeneralConstants.INT_ZERO && !windFarm1.getProjectManagers().isEmpty())
        {
            return windFarm1.getProjectManagers().getFirst().compareTo(windFarm2.getProjectManagers().getFirst());
        }
        return result;
    }
}
