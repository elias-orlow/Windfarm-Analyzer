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

/**
 * Utility-Klasse zur Validierung und Normalisierung der Gesamtleistung von Windparks.
 * <p>
 * Falls eine Leistung fehlt oder unrealistisch hoch ist, wird sie korrigiert und dokumentiert.
 */
public class PerformanceNormalizer
{
    private static final List<String> changedPerf = new ArrayList<>();

    public static List<String> getChangedPerf ()
    {
        return changedPerf;
    }


    /**
     * Normalisiert die Leistung eines Windparks.
     * <p>
     * Falls keine Leistung gesetzt ist, wird sie berechnet.
     * Andernfalls wird geprueft, ob die Leistung realistisch ist.
     *
     * @param windFarm      der zu pruefende Windpark.
     * @param windFarmGraph Graph mit benachbarten Windparks.
     * @precondition windFarm und windFarmGraph sind nicht null.
     * @postcondition die Leistung des Windparks ist validiert oder neu berechnet.
     */
    public static void normalizePerformance (WindFarm windFarm, Graph<WindFarm> windFarmGraph)
    {
        if (windFarm.getTotalPerformance() == GeneralConstants.INT_ZERO)
        {
            addPerformance(windFarm, windFarmGraph);
        } else
        {
            validatePerformance(windFarm);
        }
    }

    /**
     * Berechnet die Leistung eines Windparks anhand seiner Nachbarn.
     *
     * @param windFarm      Windpark ohne gesetzte Leistung.
     * @param windFarmGraph Graph mit Nachbarschaft.
     * @precondition windFarm besitzt keine gueltige Gesamtleistung.
     * @postcondition die Gesamtleistung des Windparks ist berechnet und gesetzt.
     */
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

    /**
     * Prueft, ob die angegebene Leistung eines Windparks realistisch ist.
     * <p>
     * Falls die Leistung zu hoch ist, wird sie in eine korrekte Einheit umgerechnet.
     *
     * @param windFarm der zu pruefende Windpark.
     * @precondition windFarm besitzt eine gesetzte Gesamtleistung.
     * @postcondition die Leistung ist validiert und ggf. korrigiert.
     */
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
