package org.elias.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WorkDay
{
    private final int dayNumber;
    private Duration driveTime;

    private WindFarm maintainedWindFarm;
    private final List<WindTurbineType> maintainedWindTurbines = new ArrayList<>();

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

    public void addWindTurbine (WindTurbineType windTurbine)
    {
        maintainedWindTurbines.add(windTurbine);
    }


}
