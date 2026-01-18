package org.elias.model;

import org.elias.model.graph.Graph;
import org.elias.model.graph.Vertex;
import org.elias.util.GeoFormula;

import java.util.List;

public class GraphFactory
{
    public static Graph<WindFarm> createGraph (List<WindFarm> windFarms)
    {
        Graph<WindFarm> windFarmGraph = new Graph<>();

        for (int i = 0; i < windFarms.size(); i++)
        {
            Vertex<WindFarm> currentVertex = new Vertex<>(windFarms.get(i));
            Coordinates currentWindfarmCord = windFarms.get(i).getCoordinates();

            windFarmGraph.addVertex(currentVertex);

            for (int j = i + 1; j < windFarms.size(); j++)
            {
                Vertex<WindFarm> compareVertex = new Vertex<>(windFarms.get(j));
                Coordinates compareWindfarmCord = windFarms.get(j).getCoordinates();

                float distance = (float) GeoFormula.haversineDistance(currentWindfarmCord.getLatitude(),
                        compareWindfarmCord.getLatitude(),
                        currentWindfarmCord.getLongitude(),
                        compareWindfarmCord.getLongitude());


                if (distance < 20.0f)
                {
                    windFarmGraph.addEdge(currentVertex, compareVertex, distance);
                }
            }

        }

        return windFarmGraph;
    }
}
