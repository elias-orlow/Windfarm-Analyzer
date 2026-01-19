package org.elias.model.service;

import org.elias.model.Coordinates;
import org.elias.model.WindFarm;
import org.elias.model.graph.Graph;
import org.elias.model.graph.Vertex;
import org.elias.res.constant.GeneralConstants;
import org.elias.util.GeoFormula;

import java.util.List;

/**
 * Fabrikklasse zur Erstellung eines Graphen aus Windparks.
 * <p>
 * Jeder Windpark wird als {@link Vertex} im Graphen dargestellt.
 * Zwei Windparks werden durch eine Kante verbunden, wenn ihr Abstand kleiner
 * als der maximal erlaubte Abstand (20 km) ist.
 */
public class GraphFactory
{
    /**
     * Erstellt einen Graphen aus einer Liste von Windparks.
     * <p>
     * Jeder Windpark wird als Knoten eingefuegt. Fuer jedes Windpark-Paar
     * wird der Abstand berechnet.
     *
     * @param windFarms Liste aller Windparks.
     * @return ein Graph, der alle Windparks und deren Nachbarn enthaelt.
     * @precondition {@code windFarms} ist nicht null und enthaelt mindestens einen Windpark.
     * @postcondition der zurueckgegebene Graph enthaelt alle Windparks als Knoten und
     * Kanten zwischen in der Nahe liegenden Windparks.
     */
    public static Graph<WindFarm> createGraph (List<WindFarm> windFarms)
    {
        Graph<WindFarm> windFarmGraph = new Graph<>();

        for (int i = 0; i < windFarms.size(); i++)
        {
            WindFarm currentWindFarm = windFarms.get(i);
            Vertex<WindFarm> currentVertex = new Vertex<>(currentWindFarm);
            Coordinates currentWindfarmCord = currentWindFarm.getCoordinates();

            windFarmGraph.addVertex(currentVertex);

            // Vergleich mit allen folgenden Windparks
            for (int j = i + GeneralConstants.NEXT_INDEX_OFFSET; j < windFarms.size(); j++)
            {
                WindFarm compareWindFarm = windFarms.get(j);
                Vertex<WindFarm> compareVertex = new Vertex<>(compareWindFarm);
                Coordinates compareWindfarmCord = compareWindFarm.getCoordinates();

                float distance = (float) GeoFormula.haversineDistance(currentWindfarmCord.getLatitude(),
                        compareWindfarmCord.getLatitude(),
                        currentWindfarmCord.getLongitude(),
                        compareWindfarmCord.getLongitude());

                // Falls der Abstand klein ist - Kante legen
                if (distance < GeneralConstants.MAX_WIND_FARM_DISTANCE_KM)
                {
                    windFarmGraph.addEdge(currentVertex, compareVertex, distance);
                }
            }

        }

        return windFarmGraph;
    }
}
