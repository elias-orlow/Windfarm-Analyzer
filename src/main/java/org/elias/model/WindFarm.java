package org.elias.model;

import org.elias.res.constant.GeneralConstants;

import java.util.ArrayList;
import java.util.List;

public class WindFarm
{
    private String name = GeneralConstants.EMPTY_STRING;
    private float totalPerformance = GeneralConstants.EMPTY_FLOAT_VARIABLE;
    private Coordinates coordinates = null;

    private final List<ProjectManager> projectManagers = new ArrayList<>();
    private final List<WindTurbineGroup> windTurbineGroups = new ArrayList<>();

    public WindFarm (String name, float totalPerformance, Coordinates coordinates)
    {
        this.name = name;
        this.totalPerformance = totalPerformance;
        this.coordinates = coordinates;
    }

    // --- Getters & Setters ---

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public float getTotalPerformance ()
    {
        return totalPerformance;
    }

    public void setTotalPerformance (float totalPerformance)
    {
        this.totalPerformance = totalPerformance;
    }

    public Coordinates getCoordinates ()
    {
        return coordinates;
    }

    public void setCoordinates (Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    public List<ProjectManager> getProjectManagers ()
    {
        return projectManagers;
    }

    public List<WindTurbineGroup> getWindTurbineGroups ()
    {
        return windTurbineGroups;
    }

    public void addProjectManager (ProjectManager projectManager)
    {
        this.projectManagers.add(projectManager);
    }

    public void addProjectManagers (List<ProjectManager> projectManagers)
    {
        for (ProjectManager projectManager : projectManagers)
        {
            this.addProjectManager(projectManager);
        }
    }

    public void addWindTurbineGroup (WindTurbineGroup windTurbineGroup)
    {
        this.windTurbineGroups.add(windTurbineGroup);
    }

    public void addWindTurbineGroups (List<WindTurbineGroup> windTurbineGroups)
    {
        for (WindTurbineGroup windTurbineGroup : windTurbineGroups)
        {
            this.addWindTurbineGroup(windTurbineGroup);
        }
    }

}
