package org.elias.util;

import org.elias.model.Coordinates;
import org.elias.res.constant.GeneralConstants;
import org.elias.res.constant.ParserConstants;

public class CoordinatesNormalizer
{
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


    public static float normalizeCoordinate (float coordinate)
    {
        String coordinateAsString = Float.toString(coordinate);
        coordinateAsString = coordinateAsString.substring(GeneralConstants.INT_ZERO, coordinateAsString.indexOf(GeneralConstants.CHAR_POINT));

        int pointIndex = coordinateAsString.length() == ParserConstants.LATITUDE_SHORT_LENGTH
                ? ParserConstants.POINT_INDEX_SHORT
                : ParserConstants.POINT_INDEX_LONG;

        return parseCoordinateWithPoint(coordinateAsString, pointIndex);
    }


    public static float parseCoordinateWithPoint (String coordinateAsString, int indexOfPoint)
    {
        return Float.parseFloat(coordinateAsString.substring(GeneralConstants.INT_ZERO, indexOfPoint)
                + GeneralConstants.CHAR_POINT
                + coordinateAsString.substring(indexOfPoint));
    }


    private static float roundToFour (float value)
    {
        return Math.round(value * ParserConstants.ROUNDING_FACTOR) / ParserConstants.ROUNDING_FACTOR;
    }
}
