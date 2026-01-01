package org.elias.res.constant;

import java.util.HashMap;
import java.util.Map;

public interface IOConstants
{
    HashMap<String, String> UMLAUT_REPLACEMENT = new HashMap<>(Map.of(
            "Ä", "AE",
            "Ö", "OE",
            "Ü", "UE"
    ));
}
