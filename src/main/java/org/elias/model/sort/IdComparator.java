package org.elias.model.sort;

import org.elias.model.WindFarm;

import java.util.Comparator;

/**
 * Comparator zum Vergleich zwei {@link WindFarm}-Objekte anhand der ID.
 * <p>
 * Die Sortierung erfolgt aufsteigend.
 */
public class IdComparator implements Comparator<WindFarm>
{
    /**
     * Vergleicht zwei Windparks anhand der ID ihrer ersten Windturbinen-Gruppe.
     *
     * @param windFarm1 der erste zu vergleichende Windpark.
     * @param windFarm2 der zweite zu vergleichende Windpark.
     * @return -1 wenn {@code windFarm1} eine kleinere ID besitzt, 1 bei größerer ID, oder 0 bei Gleichheit.
     * @precondition beide Parameter sind nicht null und besitzen mindestens eine {@code WindTurbineGroup}.
     * @postcondition die IDs wurden verglichen und das Ergebnis zurueckgegeben.
     */
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        return Integer.compare(windFarm1.getWindTurbineGroups().getFirst().getID(), windFarm2.getWindTurbineGroups().getFirst().getID());
    }
}
