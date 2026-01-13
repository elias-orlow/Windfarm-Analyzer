package org.elias.util;

import org.elias.res.constant.GeneralConstants;

public class GeoFormula
{
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
