package org.elias.res.constant;

/**
 * Sammlung allgemeiner Konstanten, die in verschiedenen Teilen der Anwendung benoetigt werden.
 */
public interface GeneralConstants
{
    /** Pfad zur CSV-Datei mit den Windkraftanlagen. */
    String PATH_TO_CSV = "src/main/resources/Windkraftanlagen_DE.csv";

    // Konstanten für leere Deklarierungen von Variablen
    String EMPTY_STRING = "";
    int EMPTY_INT_VARIABLE = 0;
    float EMPTY_FLOAT_VARIABLE = 0.0f;

    String DASH = "-";
    String EN_DASH = "–";
    String QUESTION_MARK = "?";
    String OPEN_BRACKET = "(";

    char CHAR_DOUBLE_QUOTES = '"';
    char CHAR_COMMA = ',';
    char CHAR_SPACE = ' ';

    int INT_ZERO = 0;
    int INT_ONE = 1;

    int COLUMN_COUNT = 12;
    int COLUMN_INDEX_ID = 0;
    int COLUMN_INDEX_NAME = 1;
    int COLUMN_INDEX_MANUFACTURE_YEAR = 2;
    int COLUMN_INDEX_PERFORMANCE = 3;
    int COLUMN_INDEX_WINDTURBINE_NAME = 5;
    int COLUMN_INDEX_TOWN = 6;
    int COLUMN_INDEX_DISTRICT = 7;
    int COLUMN_INDEX_LATITUDE = 8;
    int COLUMN_INDEX_LONGITUDE = 9;
    int COLUMN_INDEX_PROJECTMANAGER = 10;
    int COLUMN_INDEX_REMARKS = 11;
}
