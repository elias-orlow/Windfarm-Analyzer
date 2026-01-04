package org.elias.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindFarmRepository
{
    private static WindFarmRepository INSTANCE = null;
    private final List<WindFarm> germanWindFarms = new ArrayList<>();

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

    public List<WindFarm> getGermanWindFarms ()
    {
        return germanWindFarms;
    }

    public Map<String[], String> getInvalidRows ()
    {
        return invalidRows;
    }

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
