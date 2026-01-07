package org.elias.model.sort;

import org.elias.model.WindFarm;

import java.util.Collections;
import java.util.Comparator;

public class ManagerCountComparator implements Comparator<WindFarm>
{
    @Override
    public int compare (WindFarm windFarm1, WindFarm windFarm2)
    {
        int result = Integer.compare(windFarm1.getProjectManagers().size(), windFarm2.getProjectManagers().size());
        Collections.sort(windFarm1.getProjectManagers());
        Collections.sort(windFarm2.getProjectManagers());

        if (result == 0 && !windFarm1.getProjectManagers().isEmpty()){
            return windFarm1.getProjectManagers().getFirst().compareTo(windFarm2.getProjectManagers().getFirst());
        }
        return result;
    }
}
