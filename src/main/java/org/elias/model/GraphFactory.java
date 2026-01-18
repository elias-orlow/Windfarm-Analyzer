package org.elias.model;

import org.elias.model.graph.Graph;
import org.elias.model.graph.Vertex;
import org.elias.res.constant.GeneralConstants;
import org.elias.util.GeoFormula;

import java.util.List;

public class GraphFactory
{
    public static Graph<WindFarm> createGraph (List<WindFarm> windFarms)
    {
        Graph<WindFarm> windFarmGraph = new Graph<>();

        for (int i = 0; i < windFarms.size(); i++)
        {
            WindFarm currentWindFarm = windFarms.get(i);
            Vertex<WindFarm> currentVertex = new Vertex<>(currentWindFarm);
            Coordinates currentWindfarmCord = currentWindFarm.getCoordinates();

            windFarmGraph.addVertex(currentVertex);

            for (int j = i + GeneralConstants.NEXT_INDEX_OFFSET; j < windFarms.size(); j++)
            {
                WindFarm compareWindFarm = windFarms.get(j);
                Vertex<WindFarm> compareVertex = new Vertex<>(compareWindFarm);
                Coordinates compareWindfarmCord = compareWindFarm.getCoordinates();

                float distance = (float) GeoFormula.haversineDistance(currentWindfarmCord.getLatitude(),
                        compareWindfarmCord.getLatitude(),
                        currentWindfarmCord.getLongitude(),
                        compareWindfarmCord.getLongitude());


                if (distance < GeneralConstants.MAX_WIND_FARM_DISTANCE_KM)
                {
                    windFarmGraph.addEdge(currentVertex, compareVertex, distance);
                }
            }

        }

        return windFarmGraph;
    }
}
