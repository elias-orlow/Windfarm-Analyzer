package org.elias.util;

import org.elias.res.constant.GeneralConstants;

/**
 * Hilfsklasse zur Berechnung geografischer Distanzen.
 * <p>
 * Diese Klasse stellt mathematische Formel zur Verfuegung.
 */
public class GeoFormula
{
    /**
     * Berechnet die Entfernung zwischen zwei geografischen Punkten mithilfe der Haversine-Formel.
     * <p>
     * Die Entfernung wird auf Basis von Breiten- und Laengengraden auf einer Kugel (Erde) berechnet.
     *
     * @param latitude1  Breitengrad des ersten Punktes.
     * @param latitude2  Breitengrad des zweiten Punktes.
     * @param longitude1 Laengengrad des ersten Punktes.
     * @param longitude2 Laengengrad des zweiten Punktes.
     * @return Entfernung der beiden Punkte in Kilometern.
     * @precondition alle Koordinaten sind in Grad angegeben und gueltig.
     * @postcondition der Rueckgabewert ist die berechnete Distanz in Kilometern.
     */
    public static double haversineDistance (float latitude1, float latitude2, float longitude1, float longitude2)
    {
        double deltaLatitude = Math.toRadians(latitude2 - latitude1);
        double deltaLongitude = Math.toRadians(longitude2 - longitude1);

        double havTheta = Math.sin(deltaLatitude / GeneralConstants.INT_TWO)
                * Math.sin(deltaLatitude / GeneralConstants.INT_TWO)
                + Math.cos(Math.toRadians(latitude1))
                * Math.cos(Math.toRadians(latitude2))
                * Math.sin(deltaLongitude / GeneralConstants.INT_TWO)
                * Math.sin(deltaLongitude / GeneralConstants.INT_TWO);

        double theta = GeneralConstants.INT_TWO * Math.asin(Math.sqrt(havTheta));

        return theta * GeneralConstants.EARTH_RADIUS_KM;
    }

}
