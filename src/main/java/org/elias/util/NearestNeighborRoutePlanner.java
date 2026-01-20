package org.elias.util;

import org.elias.model.WindFarm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementiert einen Nearest-Neighbor-Algorithmus zur Routenplanung.
 * <p>
 * Beginnend bei einem Start-Windpark wird jeweils der naechstgelegene,
 * noch nicht besuchte Windpark zur Route hinzugefuegt.
 * Implementiert {@link RoutePlanner}.
 */
public class NearestNeighborRoutePlanner implements RoutePlanner
{
    /**
     * Berechnet eine Besuchsreihenfolge der Windparks anhand des Nearest-Neighbor-Verfahrens.
     *
     * @param windFarms Liste aller zu besuchenden Windparks.
     * @param start     Start-Windpark der Route.
     * @return Liste der Windparks in der berechneten Besuchsreihenfolge.
     * @precondition {@code start} ist Element von {@code windFarms}.
     * @postcondition jeder Windpark aus {@code windFarms} kommt genau einmal in der Rueckgabeliste vor.
     */
    @Override
    public List<WindFarm> calculateRoute (List<WindFarm> windFarms, WindFarm start)
    {
        if (windFarms == null || windFarms.isEmpty())
        {
            return List.of();
        }

        List<WindFarm> route = new ArrayList<>();
        Set<WindFarm> unvisited = new HashSet<>(windFarms);

        WindFarm current = start;

        route.add(start);
        unvisited.remove(start);

        while (!unvisited.isEmpty())
        {
            WindFarm nearest = findNearest(current, unvisited);
            route.add(nearest);
            unvisited.remove(nearest);
            current = nearest;
        }

        return route;
    }


    /**
     * Ermittelt den naechstgelegenen Windpark zu einem gegebenen Ausgangspunkt.
     *
     * @param current    aktueller Windpark.
     * @param candidates Menge moeglicher naechster Windparks.
     * @return der Windpark mit der geringsten Entfernung zu {@code current}.
     * @precondition {@code candidates} ist nicht leer.
     * @postcondition der zurueckgegebene Windpark ist Element von {@code candidates}.
     */
    private WindFarm findNearest (WindFarm current, Set<WindFarm> candidates)
    {
        WindFarm nearest = null;
        float bestDistance = Float.MAX_VALUE;

        for (WindFarm candidate : candidates)
        {
            float distance = (float) GeoFormula.haversineDistance(current.getCoordinates().getLatitude(),
                    candidate.getCoordinates().getLatitude(),
                    current.getCoordinates().getLongitude(),
                    candidate.getCoordinates().getLongitude());

            if (bestDistance > distance)
            {
                bestDistance = distance;
                nearest = candidate;
            }
        }

        return nearest;
    }
}
