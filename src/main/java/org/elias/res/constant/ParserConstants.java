package org.elias.res.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Konstanten fuer Parser-Operationen und regulaere Ausdruecke zur Zerlegung von CSV‑Daten.
 */
public interface ParserConstants
{
    String YEAR_SPLIT_REGEX = "[-–]";
    String COMMA_SEMICOLON_SPLIT_REGEX = "[,;]";
    String CLOSED_BRACKET_REGEX = "\\)";

    int LAST_ELEMENT_OFFSET = 1;

    HashMap<String, String> UMLAUT_REPLACEMENT = new HashMap<>(Map.of(
            "Ä", "AE",
            "Ö", "OE",
            "Ü", "UE"
    ));

    // Konstanten fuer CoordinatesNormalizer
    float NORTHEASTERNMOST_POINT = 54.9090f;
    float EASTERNMOST_POINT = 15.0419f;
    char GERMAN_LATITUDE_START_FOUR = '4';
    char GERMAN_LATITUDE_START_FIVE = '5';
    char GERMAN_LONGITUDE_START_ONE = '1';
    int POINT_INDEX_SHORT = 1;
    int POINT_INDEX_LONG = 2;
    float ROUNDING_FACTOR = 10000.0f;
    int KW_TO_MW_FACTOR = 1000;

}
