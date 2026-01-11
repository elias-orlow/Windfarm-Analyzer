package org.elias.model.sort;

import org.elias.model.WindFarm;

import java.util.Comparator;

/**
 * Comparator, der {@link WindFarm}-Objekte anhand ihrer Gesamtleistung vergleicht.
 * <p>
 * Die Sortierung erfolgt absteigend, sodass Windparks mit hoeherer Gesamtleistung zuerst erscheinen.
 */
public class TotalPerformanceComparator implements Comparator<WindFarm>
{
    /**
     * Vergleicht zwei Windparks anhand ihrer Gesamtleistung in absteigender Reihenfolge.
     *
     * @param windFarm1 der erste zu vergleichende Windpark.
     * @param windFarm2 der zweite zu vergleichende Windpark.
     * @return -1 wenn {@code windFarm1} eine hoehere Gesamtleistung hat, 1 bei geringerer Leistung, oder 0 bei Gleichheit.
     * @precondition beide Parameter sind nicht null und besitzen gueltige Gesamtleistung.
     * @postcondition die Gesamtleistungen wurden verglichen und das Ergebnis zurueckgegeben.
     */
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        return Float.compare(windFarm2.getTotalPerformance(), windFarm1.getTotalPerformance());
    }
}
