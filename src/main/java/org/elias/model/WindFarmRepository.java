package org.elias.model;

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

    /** Erfolgreich erzeugte Windparke aus der CSV-Datei */
    private final List<WindFarm> germanWindFarms = new ArrayList<>();
    /** Fehlerhafte Eintraege in der CSV-Datei und Fehlermeldung */
    Map<String[], String> invalidRows = new HashMap<>();

    private WindFarmRepository ()
    {
    }

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

    // --- Add-Methode ---

    public void addWindFarm (WindFarm windFarm)
    {
        germanWindFarms.add(windFarm);
    }

    public void addWindFarms (List<WindFarm> windFarms)
    {
        for (WindFarm currentWindFarm : windFarms)
        {
            addWindFarm(currentWindFarm);
        }
    }

    public void addInvalidRow (String[] row, String errorMessage)
    {
        invalidRows.put(row, errorMessage);
    }
}
