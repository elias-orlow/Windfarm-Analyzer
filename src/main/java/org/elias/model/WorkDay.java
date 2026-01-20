package org.elias.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Repraesentiert einen einzelnen Arbeitstag innerhalb eines Wartungsplans.
 * <p>
 * Ein Arbeitstag kann Wartungsarbeiten an Windkraftanlagen enthalten
 * und optional Fahrzeiten zwischen Windparks.
 */
public class WorkDay
{
    private final int dayNumber;
    private Duration driveTime;

    private WindFarm maintainedWindFarm;
    private final List<WindTurbineType> maintainedWindTurbines = new ArrayList<>();

    /**
     * Erstellt einen neuen Arbeitstag mit einer festen Tagesnummer.
     *
     * @param dayNumber die Nummer des Arbeitstages.
     * @precondition {@code dayNumber} ist groesser als 0.
     * @postcondition der Arbeitstag besitzt eine feste Tagesnummer,
     * aber noch keine Wartungen oder Fahrzeiten.
     */
    public WorkDay (int dayNumber)
    {
        this.dayNumber = dayNumber;
    }

    // --- Getters & Setters ---

    public int getDayNumber ()
    {
        return dayNumber;
    }

    public Duration getDriveTime ()
    {
        return driveTime;
    }

    public WindFarm getMaintainedWindFarm ()
    {
        return maintainedWindFarm;
    }

    public List<WindTurbineType> getMaintainedWindTurbines ()
    {
        return maintainedWindTurbines;
    }

    public void setDriveTime (Duration driveTime)
    {
        this.driveTime = driveTime;
    }

    public void setMaintainedWindFarm (WindFarm maintainedWindFarm)
    {
        this.maintainedWindFarm = maintainedWindFarm;
    }


    /**
     * Fuegt eine Windturbine hinzu, die an diesem Tag gewartet wird.
     *
     * @param windTurbine zu wartende Windturbine
     * @precondition {@code windTurbine} ist nicht null.
     * @postcondition die Windturbine ist in der Liste der gewarteten Turbinen enthalten.
     */
    public void addWindTurbine (WindTurbineType windTurbine)
    {
        maintainedWindTurbines.add(windTurbine);
    }

}
