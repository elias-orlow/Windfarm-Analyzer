package org.elias.model;

import org.elias.res.constant.GeneralConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model-Klasse fuer einen Windpark, der aus mehreren Windkraftanlagen besteht und mehrere Einträge
 * aus der CSV-Tabelle zusammenfasst.
 */
public class WindFarm
{
    private String name = GeneralConstants.EMPTY_STRING;
    private float totalPerformance = GeneralConstants.EMPTY_FLOAT_VARIABLE;
    private Coordinates coordinates = null;

    private final List<ProjectManager> projectManagers = new ArrayList<>();
    private final List<WindTurbineGroup> windTurbineGroups = new ArrayList<>();


    /**
     * Erzeugt ein neues {@link WindFarm}-Objekt.
     * <p>
     * Der Konstruktor initialisiert einen Windpark mit einem Namen, der
     * Gesamtleistung sowie den zugehoerigen Koordinaten.
     *
     * @param name             der Name des Windparks.
     * @param totalPerformance die gesamte installierte Leistung des Windparks in Megawatt.
     * @param coordinates      die Koordinaten des Windparks.
     * @precondition Es wird ein Name als {@code String}, eine Leistung sowie
     * ein {@link Coordinates}-Objekt uebergeben. Eine inhaltliche Validierung
     * der Werte oder eine Pruefung auf {@code null} erfolgt nicht.
     * @postcondition Das {@code WindFarm}-Objekt ist initialisiert und speichert den
     * uebergebenen Namen, die uebergebene Gesamtleistung sowie die
     * uebergebenen Koordinaten unveraendert.
     */
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

    /**
     * Fuegt einen Projektmanager zur Liste der Projektmanager hinzu.
     *
     * @param projectManager das {@link ProjectManager}-Objekt, das hinzugefuegt werden soll.
     * @precondition projectManager darf nicht null sein.
     * @postcondition projectManager befindet sich in der Liste projectManagers.
     */
    public void addProjectManager (ProjectManager projectManager)
    {
        this.projectManagers.add(projectManager);
    }

    /**
     * Fuegt mehrere Projektmanager zur Liste der Projektmanager hinzu.
     *
     * @param projectManagers die Liste von {@link ProjectManager}-Objekten.
     * @precondition projectManagers darf nicht null sein; die Liste kann leer sein.
     * @postcondition alle uebergebenen Projektmanager befinden sich in der Liste projectManagers.
     */
    public void addProjectManagers (List<ProjectManager> projectManagers)
    {
        for (ProjectManager projectManager : projectManagers)
        {
            this.addProjectManager(projectManager);
        }
    }

    /**
     * Fuegt eine Windturbinen-Gruppe zur Liste der Gruppen hinzu.
     *
     * @param windTurbineGroup das {@link WindTurbineGroup}-Objekt, das hinzugefuegt werden soll.
     * @precondition windTurbineGroup darf nicht null sein.
     * @postcondition windTurbineGroup befindet sich in der Liste windTurbineGroups.
     */
    public void addWindTurbineGroup (WindTurbineGroup windTurbineGroup)
    {
        this.windTurbineGroups.add(windTurbineGroup);
    }

    /**
     * Fuegt mehrere Windturbinen-Gruppen zur Liste der Gruppen hinzu.
     *
     * @param windTurbineGroups die Liste von {@link WindTurbineGroup}-Objekten
     * @precondition windTurbineGroups darf nicht null sein; die Liste kann leer sein
     * @postcondition alle uebergebenen Gruppen befinden sich in der Liste windTurbineGroups
     */
    public void addWindTurbineGroups (List<WindTurbineGroup> windTurbineGroups)
    {
        for (WindTurbineGroup windTurbineGroup : windTurbineGroups)
        {
            this.addWindTurbineGroup(windTurbineGroup);
        }
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        WindFarm windFarm = (WindFarm) o;
        return Float.compare(totalPerformance, windFarm.totalPerformance) == 0
                && Objects.equals(name, windFarm.name)
                && Objects.equals(coordinates, windFarm.coordinates)
                && Objects.equals(projectManagers, windFarm.projectManagers)
                && Objects.equals(windTurbineGroups, windFarm.windTurbineGroups);
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash(name, totalPerformance, coordinates, projectManagers, windTurbineGroups);
    }
}
