package org.elias.util;

import org.elias.res.Districts;
import org.elias.res.constant.IOConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCellParser
{
    public static int parseObjectID (String raw)
    {
        return Integer.parseInt(raw.trim());
    }

    public static String parseWindFarmName (String raw)
    {
        return raw.trim();
    }

    public static int parseManufactureYear (String raw)
    {
        if (raw == null || raw.isEmpty() || raw.contains("?")) //TODO: Try Catch for other mistakes
        {
            return -1; //TODO: Wenn null dann Fehlermeldung oder
        }

        if (raw.contains("-") || raw.contains("–"))
        {
            String[] parts = raw.split("[-–]"); //3689
            raw = parts[parts.length - 1];
        }

        return Integer.parseInt(raw.trim());
    }


    public static float parseTotalPerformance (String raw)
    {
        if (raw == null || raw.isEmpty())
        {
            return -1;
        }

        return Float.parseFloat(raw.trim());
    }


    public static Map<String, Integer> parseWindTurbineType (String raw)
    {
        Map<String, Integer> result = new HashMap<>();

        if (raw == null || raw.trim().isEmpty())
        {
            return result;
        }

        String[] rawWindTurbineTypes = raw.split("\\)");

        for (String rawWindTurbine : rawWindTurbineTypes)
        {
            String windTurbine = "";
            String count = "1";

            if (rawWindTurbine.contains("\\("))
            {
                int openParenIndex = rawWindTurbine.lastIndexOf('(');

                windTurbine = rawWindTurbine.substring(0, openParenIndex).trim();
                count = rawWindTurbine.substring(openParenIndex + 1, rawWindTurbine.length() - 1);
            } else
            {
                windTurbine = rawWindTurbine;
            }

            result.put(windTurbine, Integer.parseInt(count));
        }

        return result;
    }


    public static String parseTown (String raw)
    {
        return raw.trim();
    }


    public static Districts parseDistrict (String raw)
    {
        for (String letter : IOConstants.UMLAUT_REPLACEMENT.keySet())
        {
            raw = raw.replace(letter, IOConstants.UMLAUT_REPLACEMENT.get(letter));
        }

        try
        {
            return Districts.valueOf(raw);
        } catch (IllegalArgumentException e)
        {
            return null;
        }
    }


    public static float[] parseCoordinates (String latStr, String lonStr)
    {
        if (latStr == null || lonStr == null)
        {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }

        latStr = latStr.trim();
        lonStr = lonStr.trim();

        float lat = Float.parseFloat(latStr);
        float lon = Float.parseFloat(lonStr);

        //TODO: Aufgabe 2, Koordinaten kontrollieren

        return new float[]{lat, lon};
    }


    public static List<String> parseProjectManager (String raw)
    {
        List<String> result = new ArrayList<>();

        if (raw == null || raw.trim().isEmpty())
        {
            return result;
        }

        String[] parts = raw.split("[,;]");

        for (String part : parts)
        {
            String trimmed = part.trim();
            if (!trimmed.isEmpty())
            {
                result.add(trimmed);
            }
        }

        return result;
    }


    public static String parseRemarks (String raw)
    {
        return raw.trim();
    }

}
