package org.elias.model.sort;

import org.elias.model.WindFarm;

import java.util.Comparator;

/**
 * Comparator, der {@link WindFarm}-Objekte anhand ihres Namens alphabetisch vergleicht.
 * <p>
 * Die Sortierung erfolgt aufsteigend.
 */
public class WindFarmNameComparator implements Comparator<WindFarm>
{
    /**
     * Vergleicht zwei Windparks alphabetisch anhand ihres Namens.
     *
     * @param windFarm1 der erste zu vergleichende Windpark.
     * @param windFarm2 der zweite zu vergleichende Windpark.
     * @return -1 wenn {@code windFarm1} alphabetisch vor {@code windFarm2} liegt,
     * 1 bei spaeterer Position, oder 0 bei Gleichheit.
     * @precondition beide Parameter sind nicht null.
     * @postcondition die beiden Namen wurden verglichen und das Ergebnis zurueckgegeben.
     */
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        return windFarm1.getName().compareTo(windFarm2.getName());
    }
}
