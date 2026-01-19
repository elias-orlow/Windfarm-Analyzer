package org.elias.model.service;

import org.elias.model.WindFarm;
import org.elias.model.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Speichert alle aktuelle Windparke waehrend der Programmausfuehrung.
 * <p>
 * Wird von {@link WindFarmImporter} befuegt.
 * Folgt dem Singleton-Pattern.
 */
public class WindFarmRepository
{
    private static WindFarmRepository INSTANCE = null;

    /**
     * Erfolgreich erzeugte Windparke aus der CSV-Datei
     */
    private final List<WindFarm> germanWindFarms = new ArrayList<>();
    /**
     * Fehlerhafte Eintraege in der CSV-Datei und Fehlermeldung
     */
    Map<String[], String> invalidRows = new HashMap<>();

    Graph<WindFarm> windFarmGraph = null;

    /**
     * Erzeugt eine neue Instanz des {@link WindFarmRepository}.
     * <p>
     * Der Konstruktor ist privat, da die Klasse als Singleton verwendet wird.
     *
     * @precondition es existiert noch keine Instanz des {@code WindFarmRepository}.
     * @postcondition eine neue Repository-Instanz ist erzeugt und enthaelt leere
     * Sammlungen fuer Windparks und fehlerhafte CSV-Eintraege.
     */
    private WindFarmRepository ()
    {
    }

    /**
     * Liefert die Singleton-Instanz des {@code WindFarmRepository}.
     * <p>
     * Falls noch keine Instanz existiert, wird diese erzeugt.
     *
     * @return die Singleton-Instanz des Repositories.
     * @postcondition eine Instanz des {@code WindFarmRepository} existiert und wird
     * zurueckgegeben.
     */
    public static WindFarmRepository getInstance ()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new WindFarmRepository();
        }
        return INSTANCE;
    }

    // --- Getters ---

    public List<WindFarm> getGermanWindFarms ()
    {
        return germanWindFarms;
    }

    public Map<String[], String> getInvalidRows ()
    {
        return invalidRows;
    }

    public Graph<WindFarm> getWindFarmGraph ()
    {
        return windFarmGraph;
    }

    public void setWindFarmGraph (Graph<WindFarm> windFarmGraph)
    {
        this.windFarmGraph = windFarmGraph;
    }

    // --- Add-Methode ---

    /**
     * Fuegt einen Windpark zum Repository hinzu.
     *
     * @param windFarm der hinzuzufuegende {@link WindFarm}.
     * @precondition windFarm darf nicht null sein.
     * @postcondition der Windpark befindet sich in der Liste germanWindFarms.
     */
    public void addWindFarm (WindFarm windFarm)
    {
        germanWindFarms.add(windFarm);
    }


    /**
     * Fuegt mehrere Windparks zum Repository hinzu.
     *
     * @param windFarms die Liste der hinzuzufuegenden {@link WindFarm}-Objekte.
     * @precondition windFarms darf nicht null sein; die Liste kann leer sein.
     * @postcondition Alle uebergebenen Windparks befinden sich in der Liste germanWindFarms.
     */
    public void addWindFarms (List<WindFarm> windFarms)
    {
        for (WindFarm currentWindFarm : windFarms)
        {
            addWindFarm(currentWindFarm);
        }
    }

    /**
     * Speichert einen fehlerhaften CSV-Datensatz mit zugehoeriger Fehlermeldung.
     *
     * @param row          der fehlerhafte CSV-Datensatz.
     * @param errorMessage die zugehoerige Fehlermeldung.
     * @precondition row und errorMessage duerfen nicht null sein.
     * @postcondition der Datensatz ist zusammen mit der Fehlermeldung in invalidRows gespeichert.
     */
    public void addInvalidRow (String[] row, String errorMessage)
    {
        invalidRows.put(row, errorMessage);
    }
}
