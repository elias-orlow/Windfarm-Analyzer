package org.elias.model;

import java.util.ArrayList;
import java.util.List;

public class WindFarmRepository
{
    private static WindFarmRepository INSTANCE = null;
    private final List<WindFarm> GermanWindFarms = new ArrayList<>();

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
        return GermanWindFarms;
    }

    public void addWindFarm (WindFarm windFarm)
    {
        GermanWindFarms.add(windFarm);
    }

    public void addWindFarms (ArrayList<WindFarm> windFarms)
    {
        for (WindFarm currentWindFarm : windFarms)
        {
            addWindFarm(currentWindFarm);
        }
    }
}
