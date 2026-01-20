package org.elias.util;

import org.elias.model.WindFarm;

import java.util.List;

/**
 * Definiert eine Strategie zur Berechnung einer Besuchsreihenfolge von Windparks.
 * <p>
 * Implementierungen dieser Schnittstelle bestimmen, in welcher Reihenfolge Windparks besucht werden sollen.
 */
public interface RoutePlanner
{
    /**
     * Berechnet eine Route durch alle uebergebenen Windparks, beginnend bei einem Start-Windpark.
     *
     * @param windFarms Liste der zu besuchenden Windparks.
     * @param start     Start-Windpark der Route.
     * @return Liste der Windparks in der berechneten Besuchsreihenfolge;
     * eine leere Liste, falls {@code windFarms} leer oder null ist.
     * @precondition {@code start} ist Element von {@code windFarms}.
     * @postcondition jeder Windpark aus {@code windFarms} kommt genau einmal in der Rueckgabeliste vor.
     */
    List<WindFarm> calculateRoute (List<WindFarm> windFarms, WindFarm start);
}
