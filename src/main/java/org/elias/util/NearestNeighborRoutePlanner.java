package org.elias.util;

import org.elias.model.WindFarm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NearestNeighborRoutePlanner implements RoutePlanner
{
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
