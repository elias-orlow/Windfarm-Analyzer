package org.elias.util;

import org.elias.model.WindFarm;
import org.elias.model.WindTurbineGroup;
import org.elias.model.graph.Graph;
import org.elias.model.graph.Vertex;
import org.elias.res.constant.GeneralConstants;
import org.elias.res.constant.ParserConstants;
import org.elias.res.constant.ViewConstants;

import java.util.ArrayList;
import java.util.List;

public class PerformanceNormalizer
{
    private static final List<String> changedPerf = new ArrayList<>();

    public static List<String> getChangedPerf ()
    {
        return changedPerf;
    }


    public static void normalizePerformance (WindFarm windFarm, Graph<WindFarm> windFarmGraph)
    {
        if (windFarm.getTotalPerformance() == 0)
        {
            addPerformance(windFarm, windFarmGraph);
        } else
        {
            validatePerformance(windFarm);
        }
    }

    private static void addPerformance (WindFarm windFarm, Graph<WindFarm> windFarmGraph)
    {
        List<Vertex<WindFarm>> neighborsVertexes = windFarmGraph.getNeighbors(new Vertex<>(windFarm));
        int neighborsWindTurbineTotalCount = GeneralConstants.EMPTY_INT_VARIABLE;
        float neighborsTotalPerformance = GeneralConstants.EMPTY_FLOAT_VARIABLE;

        for (Vertex<WindFarm> windFarmVertex : neighborsVertexes)
        {
            for (WindTurbineGroup windTurbineGroup : windFarmVertex.getData().getWindTurbineGroups())
            {
                neighborsWindTurbineTotalCount += windTurbineGroup.getWindTurbines().size();
            }
            neighborsTotalPerformance += windFarmVertex.getData().getTotalPerformance();
        }

        float averagePerformance = neighborsTotalPerformance / neighborsWindTurbineTotalCount;
        int windFarmCounter = GeneralConstants.EMPTY_INT_VARIABLE;

        for (WindTurbineGroup windTurbineGroup : windFarm.getWindTurbineGroups())
        {
            windFarmCounter += windTurbineGroup.getWindTurbines().size();
        }

        windFarm.setTotalPerformance(averagePerformance * windFarmCounter);

        changedPerf.add(String.format(ViewConstants.CALCULATED_PERFORMANCE_NAME,
                windFarm.getName(),
                averagePerformance * windFarmCounter));
    }


    private static void validatePerformance (WindFarm windFarm)
    {
        int windTurbineCounter = GeneralConstants.EMPTY_INT_VARIABLE;

        for (WindTurbineGroup windTurbineGroup : windFarm.getWindTurbineGroups())
        {
            windTurbineCounter += windTurbineGroup.getWindTurbines().size();
        }

        if (windFarm.getTotalPerformance() > windTurbineCounter * GeneralConstants.TOP_TURBINE_PERFORMANCE)
        {
            float convertedPerformance = windFarm.getTotalPerformance() / ParserConstants.KW_TO_MW_FACTOR;

            changedPerf.add(String.format(ViewConstants.CONVERTED_PERFORMANCE_NAME, windFarm.getName(),
                    windFarm.getTotalPerformance(),
                    convertedPerformance));
            windFarm.setTotalPerformance(convertedPerformance);

        }
    }
}
