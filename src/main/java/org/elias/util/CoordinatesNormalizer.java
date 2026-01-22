package org.elias.util;

import org.elias.model.Coordinates;
import org.elias.res.constant.GeneralConstants;
import org.elias.res.constant.ParserConstants;

/**
 * Util-Klasse fuer Ueberpruefung von Richtigkeit und Normalisierung der Koordinaten.
 */
public class CoordinatesNormalizer
{
    /**
     * Normalisiert Breitengrad und Laengengrad, falls diese Koordinate
     * außerhalb der maximal moeglichen Punkten fuer Deutschland liegen.
     * Ein solcher Fall weist darauf hin, dass die Koordinaten falsche Eingabe in CSV-Tabelle haben.
     *
     * @param latitude  urspruenglicher Breitengrad als float.
     * @param longitude urspruenglicher Laengengrad als float.
     * @return {@link Coordinates} mit normalisierten Werten.
     * @precondition {@code latitude} und {@code longitude} sind die Werte fuer Koordinaten aus der CSV-Tabelle.
     * @postcondition Koordinaten liegen im gueltigen Bereich und haben vier Nachkommastellen.
     */
    public static Coordinates normalizeCoordinates (float latitude, float longitude)
    {
        if (latitude > ParserConstants.NORTHEASTERNMOST_POINT)
        {
            latitude = normalizeCoordinate(latitude);
        }

        if (longitude > ParserConstants.EASTERNMOST_POINT)
        {
            longitude = normalizeCoordinate(longitude);
        }

        return new Coordinates(roundToFour(latitude), roundToFour(longitude));
    }


    /**
     * Normalisiert eine einzelne Koordinate, indem der Dezimalpunkt an die
     * korrekte Position gesetzt wird. Die Methode geht davon aus, dass die
     * Eingabe eine Ganzzahl ohne Dezimalpunkt ist (z. B. 51999).
     *
     * @param coordinate Koordinate ohne gueltigen Dezimalpunkt.
     * @return korrekt normalisierte Koordinate als float.
     * @precondition Koordinate stammt aus der Tabelle.
     * @postcondition einzelne Koordinate hat vier Nachkommastellen.
     */
    private static float normalizeCoordinate (float coordinate)
    {
        String coordinateAsString = Float.toString(coordinate);
        coordinateAsString = coordinateAsString.substring(
                GeneralConstants.INT_ZERO, coordinateAsString.indexOf(GeneralConstants.CHAR_POINT));

        int pointIndex = isIntegerPartShort(coordinateAsString)
                ? ParserConstants.POINT_INDEX_SHORT
                : ParserConstants.POINT_INDEX_LONG;

        return parseCoordinateWithPoint(coordinateAsString, pointIndex);
    }


    /**
     * Prueft, ob der Integer-Teil der Koordinate nur eine Stelle lang ist.
     * <p>
     * Die Entscheidung basiert auf der ersten Ziffer:
     * <ul>
     *  <li> zweistellige Breitengrade in Deutschland beginnen mit 4 oder 5</li>
     *  <li> zweistellige Laengengrade beginnen mit 1</li>
     * </ul>
     * Beginnt die Zahl nicht mit diesen Werten, wird angenommen, dass der Integer-Teil nur einstellig ist.
     *
     * @param coordinateString Koordinate als String.
     * @return true -> wenn Koordinate einstellig ist, sonst false.
     * @precondition {@code coordinateString} ist nicht leer.
     * @postcondition boolescher Wert wird zurueckgegeben.
     */
    private static boolean isIntegerPartShort (String coordinateString)
    {
        char startWith = coordinateString.charAt(GeneralConstants.INT_ZERO);
        return startWith != ParserConstants.GERMAN_LATITUDE_START_FIVE
                && startWith != ParserConstants.GERMAN_LATITUDE_START_FOUR
                && startWith != ParserConstants.GERMAN_LONGITUDE_START_ONE;
    }


    /**
     * Fuegt an der angegebenen Stelle einen Dezimalpunkt in die Koordinate ein.
     *
     * @param coordinateAsString Koordinate als reine Ziffernfolge ohne Dezimalpunkt.
     * @param indexOfPoint Position, an der der Dezimalpunkt eingefuegt werden soll.
     * @return Koordinate als float mit korrektem Dezimalpunkt.
     * @precondition {@code coordinateAsString} besteht aus Ziffern und {@code indexOfPoint} ist gueltiger Index.
     * @postcondition gueltige float-Darstellung der Koordinate mit genau einem Punkt wird zurueckgegeben.
     */
    private static float parseCoordinateWithPoint (String coordinateAsString, int indexOfPoint)
    {
        return Float.parseFloat(coordinateAsString.substring(GeneralConstants.INT_ZERO, indexOfPoint)
                + GeneralConstants.CHAR_POINT
                + coordinateAsString.substring(indexOfPoint));
    }

    /**
     * Rundet einen float-Wert auf vier Nachkommastellen.
     *
     * @param value zu rundender Wert.
     * @return Wert mit vier Nachkommastellen.
     * @precondition {@code value} ist ein float-Wert.
     * @postcondition Der Rueckgabewert entspricht {@code value}, gerundet auf vier Nachkommastellen.
     */
    private static float roundToFour (float value)
    {
        return Math.round(value * ParserConstants.ROUNDING_FACTOR) / ParserConstants.ROUNDING_FACTOR;
    }
}
