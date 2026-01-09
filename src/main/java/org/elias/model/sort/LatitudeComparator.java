package org.elias.model.sort;

import org.elias.model.WindFarm;

import java.util.Comparator;

public class LatitudeComparator implements Comparator<WindFarm>
{
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        return Float.compare(windFarm1.getCoordinates().getLatitude(), windFarm2.getCoordinates().getLatitude());
    }
}
