package org.elias.res.constant;

/**
 * Sammlung aller Fehlermeldungen, die innerhalb der Anwendung verwendet werden.
 */
public interface ErrorMessages
{
    String MAIN_CONTROLLER_NOT_INITIALIZED = "MainController wurde noch nicht initialisiert!";
    String MAIN_CONTROLLER_ALREADY_INITIALIZED = "MainController wurde schon initialisiert!";
    String TABLE_CONTROLLER_NOT_INITIALIZED = "TableController wurde noch nicht initialisiert!";
    String TABLE_CONTROLLER_ALREADY_INITIALIZED = "TableController wurde schon initialisiert!";

    String ERROR_READING_CSV = "Fehler beim Lesen der CSV-Datei: ";
    String NO_WINDFARM = "Error: No Wind Farms found";

    String NULL_COORDINATES = "Coordinates cannot be null";
    String INVALID_INPUT = "Invalid input. Please enter a number";
    String INVALID_NUMBER = "Invalid input. Please select an action";

    String INVALID_USER_SORT = "Input not recognized. Your selection must follow the required format (e.g., 1a, 2b+, 3c-). \nPlease try again.";
}
