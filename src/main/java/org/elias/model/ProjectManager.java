package org.elias.model;

import org.elias.res.constant.GeneralConstants;

/**
 * Model-Klasse, die einen Projektmanager bzw. Betreiber eines Windparks repraesentiert.
 * <p>
 * Die Informationen stammen aus der CSV-Spalte "Projektierer/Betreiber" und werden einer {@link WindFarm}
 * zugeordnet.
 */
public class ProjectManager implements Comparable<ProjectManager>
{
    private String company = GeneralConstants.EMPTY_STRING;

    /**
     * Erzeugt ein neues {@link ProjectManager}-Objekt.
     * <p>
     * Der Konstruktor initialisiert den Projektmanager mit dem Namen des
     * zugehoerigen Unternehmens. Der uebergebene Wert wird unveraendert uebernommen.
     *
     * @param company der Name des Unternehmens, das den Projektmanager stellt.
     * @precondition es wird ein {@code String} uebergeben, der den Namen eines Unternehmens
     * repraesentiert.
     * @postcondition Das {@code ProjectManager}-Objekt ist initialisiert und speichert den
     * uebergebenen Unternehmensnamen unveraendert.
     */
    public ProjectManager (String company)
    {
        this.company = company;
    }

    // --- Getters & Setters ---

    public String getCompany ()
    {
        return company;
    }

    public void setCompany (String company)
    {
        this.company = company;
    }

    @Override
    public int compareTo (ProjectManager projectManager)
    {
        return this.getCompany().compareTo(projectManager.getCompany());
    }
}
