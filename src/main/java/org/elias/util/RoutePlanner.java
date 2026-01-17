package org.elias.util;

import org.elias.model.WindFarm;

import java.util.List;

public interface RoutePlanner
{
    List<WindFarm> calculateRoute (List<WindFarm> windFarms, WindFarm start);
}
