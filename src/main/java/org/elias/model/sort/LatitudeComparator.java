package org.elias.model.sort;

import org.elias.model.WindFarm;

import java.util.Comparator;

/**
 * Comparator, der {@link WindFarm}-Objekte anhand ihres Breitengrads vergleicht.
 * <p>
 * Die Sortierung erfolgt aufsteigend.
 */
public class LatitudeComparator implements Comparator<WindFarm>
{
    /**
     * Vergleicht zwei Windparks anhand ihrer geografischen Breite.
     *
     * @param windFarm1 der erste zu vergleichende Windpark.
     * @param windFarm2 der zweite zu vergleichende Windpark.
     * @return -1 wenn {@code windFarm1} einen kleineren Breitengrad hat, 1 bei groesserem Breitengrad, oder 0 bei Gleichheit.
     * @precondition beide Parameter sind nicht null und besitzen gueltige Koordinaten.
     * @postcondition die beiden Breitengrade wurden verglichen und das Ergebnis zurueckgegeben.
     */
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        return Float.compare(windFarm1.getCoordinates().getLatitude(), windFarm2.getCoordinates().getLatitude());
    }
}
