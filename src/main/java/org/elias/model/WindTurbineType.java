package org.elias.model;

import org.elias.res.constant.GeneralConstants;

/**
 * Model-Klasse fuer eine einzelne Windturbine, die Spalte "Typ (WKA)" aus der CSV-Tabelle repraesentiert.
 */
public class WindTurbineType
{
    private String model = GeneralConstants.EMPTY_STRING;

    /**
     * Erzeugt einen neuen {@link WindTurbineType}.
     *
     * @param model die Modellbezeichnung der Windturbine.
     * @precondition es wird ein {@code String} uebergeben, der eine Modellbezeichnung
     * repraesentiert. Eine inhaltliche Validierung erfolgt nicht.
     * @postcondition das {@code WindTurbineType}-Objekt ist initialisiert und speichert
     * die uebergebene Modellbezeichnung unveraendert.
     */
    public WindTurbineType (String model)
    {
        this.model = model;
    }

    // --- Getters & Setters ---

    public String getModel ()
    {
        return model;
    }

    public void setModel (String model)
    {
        this.model = model;
    }
}
