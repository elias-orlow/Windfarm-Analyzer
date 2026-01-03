package org.elias.res.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Konstanten für Parser-Operationen und regulaere Ausdruecke zur Zerlegung von CSV‑Daten.
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
}
