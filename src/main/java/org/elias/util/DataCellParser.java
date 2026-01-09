package org.elias.util;

import org.elias.res.Districts;
import org.elias.res.constant.ErrorMessages;
import org.elias.res.constant.GeneralConstants;
import org.elias.res.constant.ParserConstants;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasse, die fuer die Umwandlung von Daten aus einzelnen Zellen zustaendig ist.
 * <p>
 * Enthaelt statische Parsing-Methoden, um aus String-Werten die entsprechenden
 * Datentypen zu erzeugen.
 */
public class DataCellParser
{
    /**
     * Wandelt einen uebergebenen Roh-String in eine ganzzahlige Objekt-ID um.
     *
     * @param raw der Roh-String, der die Objekt-ID enthaelt.
     * @return die geparste Objekt-ID als integer.
     * @precondition der Parameter {@code raw} ist nicht null und ist eine
     * ganzzahlige Darstellung.
     * @postcondition gibt den ganzzahlige Wert zurueck.
     */
    public static int parseObjectID (String raw)
    {
        return Integer.parseInt(raw.trim());
    }


    /**
     * Wandelt einen uebergebenen Roh-String in den Namen des Windkraftparks um.
     *
     * @param raw der Roh-String, der den Namen enthaelt.
     * @return der bereinigte Name als String mit den entfernten fuehrenden und nachfolgenden Leerzeichen.
     * @precondition der Parameter {@code raw} ist nicht null.
     * @postcondition gibt den getrimmten String-Wert zurueck.
     */
    public static String parseWindFarmName (String raw)
    {
        return raw.trim();
    }


    /**
     * Wandelt einen uebergebenen Roh-String in ein ganzzahliges Datum um.
     *
     * @param raw der Roh-String, der das Baujahr bzw. einen Jahresbereich enthaelt.
     * @return das Baujahr der Windkraftanlage als ganzzahliger Wert. Falls {@code raw} null, leer oder ein Fragezeichen ist,
     * wird 0 zurueckgegeben.
     * @precondition der Parameter {@code raw} enthaelt keine Buchstaben. null, leeres String oder Fragezeichen sind erlaubt.
     * @postcondition gibt den geparsten ganzzahligen Wert zurueck. Bei Jahresbereich wird das letzte Jahr verwendet.
     */
    public static Year parseManufactureYear (String raw)
    {
        if (raw == null || raw.isEmpty() || raw.contains(GeneralConstants.QUESTION_MARK))
        {
            return Year.of(GeneralConstants.EMPTY_INT_VARIABLE);
        }

        if (raw.contains(GeneralConstants.DASH) || raw.contains(GeneralConstants.EN_DASH))
        {
            String[] parts = raw.split(ParserConstants.YEAR_SPLIT_REGEX);
            raw = parts[parts.length - ParserConstants.LAST_ELEMENT_OFFSET].trim().length() == 4
                    ? parts[parts.length - ParserConstants.LAST_ELEMENT_OFFSET]
                    : parts[GeneralConstants.INT_ZERO];
        }

        return Year.of(Integer.parseInt(raw.trim()));
    }


    /**
     * Wandelt einen uebergebenen Roh-String in eine float-Leistung um.
     *
     * @param raw der Roh-String, der die Leistung eines Windkraftparks enthaelt.
     * @return die Leistung des Windkraftparks als float-Wert. Falls {@code raw} null oder leer ist,
     * wird 0.0f zurueckgegeben.
     * @precondition der Parameter {@code raw} enthaelt keine Buchstaben oder Fragezeichen. null und leeres String sind erlaubt.
     * @postcondition gibt den geparsten float-Wert zurueck.
     */
    public static float parseTotalPerformance (String raw)
    {
        if (raw == null || raw.isEmpty())
        {
            return GeneralConstants.EMPTY_FLOAT_VARIABLE;
        }

        return Float.parseFloat(raw.trim());
    }

    /**
     * Parst einen Roh-String, der Windturbinentypen und deren Anzahl enthaelt,
     * und wandelt ihn in eine Map aus Turbinentyp → Anzahl um.
     *
     * @param raw Der Roh-String, der die Turbinentypen enthaelt. Darf null oder leer sein.
     * @return Eine Map, die jedem Turbinentyp seine Anzahl zuordnet. Bei ungueltigen oder
     * leeren Eingaben wird eine leere Map zurueckgegeben.
     * @precondition Der Parameter {@code raw} ist korrekt formatiert (z.B. "REpower MM92(3×)").
     * @postcondition Gibt eine Map ohne Duplikate zurueck. Typen ohne explizite Anzahl erhalten den Wert 1.
     */
    public static Map<String, Integer> parseWindTurbineType (String raw)
    {
        Map<String, Integer> result = new HashMap<>();

        if (raw == null || raw.trim().isEmpty())
        {
            return result;
        }

        // Mit schliessenden Klammern aufteilen
        String[] turbineSegments = raw.split(ParserConstants.CLOSED_BRACKET_REGEX);

        for (String segment : turbineSegments)
        {
            String windTurbine = GeneralConstants.EMPTY_STRING;
            String count = Integer.toString(GeneralConstants.INT_ONE);

            // Pruefen, ob Anzahl angegeben wurde
            if (segment.contains(GeneralConstants.OPEN_BRACKET))
            {
                int openParenIndex = segment.lastIndexOf(GeneralConstants.OPEN_BRACKET);

                // Typ extrahieren
                windTurbine = segment.substring(GeneralConstants.INT_ZERO, openParenIndex).trim();
                // Anzahl extrahieren (zwischen '(' und '×)')
                count = segment.substring(openParenIndex + GeneralConstants.INT_ONE, segment.length() - GeneralConstants.INT_ONE);
            } else
            {
                windTurbine = segment.trim();
            }

            result.put(windTurbine, Integer.parseInt(count));
        }

        return result;
    }


    /**
     * Wandelt einen uebergebenen Roh-String in den Namen der Stadt um.
     *
     * @param raw der Roh-String, der den Namen der Stadt enthaelt.
     * @return der bereinigte Name als String mit den entfernten fuehrenden und nachfolgenden Leerzeichen.
     * @precondition der Parameter {@code raw} ist nicht null.
     * @postcondition gibt den getrimmten String-Wert zurueck.
     */
    public static String parseTown (String raw)
    {
        return raw.trim();
    }


    /**
     * Wandelt einen Roh-String in einen {@link Districts}-Enumwert um.
     * <p>
     * Dabei werden die Umlaute mit {@code ParserConstants.UMLAUT_REPLACEMENT} ersetzt.
     *
     * @param raw der Roh-String, der den Landkreisnamen enthaelt.
     * @return entsprechende {@link Districts}-Enumwert oder null.
     * @precondition der Parameter {@code raw} enthaelt einen gueltigen Landkreisnamen oder ist null, leer.
     * @postcondition gibt entweder passenden Enumwert oder null zurueck.
     */
    public static Districts parseDistrict (String raw)
    {
        if (raw == null || raw.trim().isEmpty())
        {
            return null;
        }

        for (String letter : ParserConstants.UMLAUT_REPLACEMENT.keySet())
        {
            raw = raw.replace(letter, ParserConstants.UMLAUT_REPLACEMENT.get(letter));
        }

        return Districts.valueOf(raw.trim());
    }


    /**
     * Wandelt zwei Roh-String für Breiten- und Laengengrad und wandelt sie in ein float-Array um.
     *
     * @param latStr Der Roh-String für den Breitengrad. Darf nicht null sein.
     * @param lonStr Der Roh-String für den Laengengrad. Darf nicht null sein.
     * @return Ein float-Array {@code {latitude, longitude}}.
     * @throws IllegalArgumentException wenn einer der Parameter null ist.
     * @precondition Beide Parameter sind nicht null und enthalten gueltige Float-Werte.
     * @postcondition Gibt ein Array der Laenge 2 zurueck, bestehend aus Breitengrad und Laengengrad.
     */
    public static float[] parseCoordinates (String latStr, String lonStr)
    {
        if (latStr == null || lonStr == null)
        {
            throw new IllegalArgumentException(ErrorMessages.NULL_COORDINATES);
        }

        latStr = latStr.trim();
        lonStr = lonStr.trim();

        float lat = Float.parseFloat(latStr);
        float lon = Float.parseFloat(lonStr);

        return new float[]{lat, lon};
    }


    /**
     * Parst einen Roh-String, der eine Liste von Projektleitern enthaelt, und wandelt
     * ihn in eine Liste einzelner Namen um.
     * <p>
     * Die Namen koennen durch Kommas oder Semikolons getrennt sein.
     *
     * @param raw Der Roh-String, der die Namen der Projektmanager enthaelt. Darf null oder leer sein.
     * @return Liste der Namen. Bei null oder leerem Roh-String wird eine leere Liste zurueckgegeben.
     * @precondition der Parameter {@code raw} enthaelt Namen, die durch Komma oder Semikolon getrennt sind.
     * @postcondition gibt eine Liste ohne leere Eintraege zurueck.
     */
    public static List<String> parseProjectManager (String raw)
    {
        List<String> result = new ArrayList<>();

        if (raw == null || raw.trim().isEmpty())
        {
            return result;
        }

        // Mit Komma oder Semikolon aufteilen
        String[] parts = raw.split(ParserConstants.COMMA_SEMICOLON_SPLIT_REGEX);

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


    /**
     * Wandelt einen uebergebenen Roh-String in die Bemerkungen um.
     *
     * @param raw der Roh-String, der die Bemerkungen zum Windkraftpark enthaelt.
     * @return der bereinigte Name als String mit den entfernten fuehrenden und nachfolgenden Leerzeichen.
     * @precondition der Parameter {@code raw} ist nicht null.
     * @postcondition gibt den getrimmten String-Wert zurueck.
     */
    public static String parseRemarks (String raw)
    {
        return raw.trim();
    }

}
