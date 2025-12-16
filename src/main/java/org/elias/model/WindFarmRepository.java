package org.elias.model;

import java.util.ArrayList;

public class WindFarmRepository
{
    private static WindFarmRepository INSTANCE;
    private final ArrayList<WindFarm> GermanWindFarms = new ArrayList<>();

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

    public ArrayList<WindFarm> getGermanWindFarms ()
    {
        return GermanWindFarms;
    }

    public void addWindFarm (WindFarm windFarm)
    {
        GermanWindFarms.add(windFarm);
    }
}
