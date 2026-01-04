package org.elias.model;

import org.elias.res.constant.GeneralConstants;

/**
 * Model-Klasse, die einen Projektmanager bzw. Betreiber eines Windparks repraesentiert.
 * <p>
 * Die Informationen stammen aus der CSV-Spalte "Projektierer/Betreiber" und werden einer {@link WindFarm}
 * zugeordnet.
 */
public class ProjectManager
{
    private String company = GeneralConstants.EMPTY_STRING;

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
}
