package org.elias.model;

import org.elias.model.sort.LatitudeComparator;
import org.elias.model.sort.TotalPerformanceComparator;
import org.elias.model.sort.WindTurbineCountComparator;
import org.elias.res.constant.GeneralConstants;

/**
 * Bietet Analysefunktionen fuer Windparks eines {@link WindFarmRepository}.
 * <p>
 * Die Klasse ermoeglicht das Ermitteln bestimmter Analyse-Werte und das Berechnen der Gesamtleistung aller Windparks.
 */
public class WindFarmAnalyzer
{
    WindFarmRepository windFarmRepository = null;

    /**
     * Erstellt einen Analyzer fuer die Windparks eines Repositories.
     *
     * @param germanWindFarms das {@link WindFarmRepository} mit Windparks.
     * @precondition {@code germanWindFarms} ist nicht null.
     * @postcondition das Repository wurde gespeichert und steht fuer Analysen bereit.
     */
    public WindFarmAnalyzer (WindFarmRepository germanWindFarms)
    {
        this.windFarmRepository = germanWindFarms;
    }


    /**
     * Ermittelt den suedlichsten Windpark anhand des Breitengrads.
     *
     * @return der suedlichste Windpark.
     * @precondition das Repository enthaelt mindestens einen Windpark.
     * @postcondition die Liste der Windparks wurde nach Breitengrad sortiert und ein Windpark zurueckgegeben.
     */
    public WindFarm findSouthernmostWindfarm ()
    {
        this.windFarmRepository.getGermanWindFarms().sort(new LatitudeComparator());

        return this.windFarmRepository.getGermanWindFarms().getFirst();
    }


    /**
     * Ermittelt den Windpark mit der hoechsten Gesamtleistung.
     *
     * @return der leistungsstaerkste Windpark.
     * @precondition das Repository enthaelt mindestens einen Windpark.
     * @postcondition die Liste der Windparks wurde nach Gesamtleistung sortiert und ein Windpark zurueckgegeben.
     */
    public WindFarm findHighestPerformance ()
    {
        this.windFarmRepository.getGermanWindFarms().sort(new TotalPerformanceComparator());

        return this.windFarmRepository.getGermanWindFarms().getFirst();
    }


    /**
     * Ermittelt den Windpark mit der hoechsten Anzahl an Windturbinen.
     *
     * @return der Windpark mit den meisten Windturbinen.
     * @precondition das Repository enthält mindestens einen Windpark.
     * @postcondition die Liste der Windparks wurde nach Turbinenanzahl sortiert und ein Windpark zurueckgegeben.
     */
    public WindFarm findMostWindturbine ()
    {
        this.windFarmRepository.getGermanWindFarms().sort(new WindTurbineCountComparator().reversed());

        return this.windFarmRepository.getGermanWindFarms().getFirst();
    }


    /**
     * Berechnet die Gesamtleistung aller deutschen Windparks.
     *
     * @return die Summe der Gesamtleistungen aller Windparks.
     * @precondition das Repository ist nicht null.
     * @postcondition es wurden keine Aenderungen am Repository vorgenommen aber einen float-Wert zurueckgegeben.
     */
    public float calculateTotalPerformanceOfAllWindFarms ()
    {
        float totalPerformance = GeneralConstants.EMPTY_FLOAT_VARIABLE;

        for (WindFarm currentWindfarm : windFarmRepository.getGermanWindFarms())
        {
            totalPerformance += currentWindfarm.getTotalPerformance();
        }

        return totalPerformance;
    }
}
