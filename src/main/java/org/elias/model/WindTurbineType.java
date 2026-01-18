package org.elias.model;

import org.elias.res.constant.GeneralConstants;

import java.util.Objects;

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

    /**
     * Vergleicht diese Windturbine mit einem anderen Objekt.
     *
     * @param o das Objekt, das mit dieser Windturbine verglichen werden soll.
     * @return boolean-Wert, ob die Objekte gleich sind.
     * @precondition das uebergebene Objekt ist nicht null.
     * @postcondition es wird true zurueckgegeben, wenn die Daten gleich sind, sonst false.
     */
    @Override
    public boolean equals (Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        WindTurbineType that = (WindTurbineType) o;
        return Objects.equals(model, that.model);
    }

    /**
     * Berechnet den Hashcode dieses Objektes.
     *
     * @return einen ganzzahligen Hashwert basierend auf den Daten.
     * @precondition die gespeicherten Daten sind nicht null.
     * @postcondition gleiche Objekte liefern den gleichen Hashwert.
     */
    @Override
    public int hashCode ()
    {
        return Objects.hashCode(model);
    }
}
