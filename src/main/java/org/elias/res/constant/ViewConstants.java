package org.elias.res.constant;

/**
 * Sammlung aller Konstanten aus Literalen, die von der View-Schicht verwendet wird.
 */
public interface ViewConstants
{
    String WELCOME_MESSAGE = """
            ========================================
                Welcome to the Windfarm Analyzer
            ========================================
            """;

    String TOTAL_WINDFARM_MESSAGE = "A total of %d wind farms were found.\n";
    String TOTAL_INVALID_MESSAGE = "%d wind farms could not be imported due to invalid data:";
    String FAILED_CAUSE_MESSAGE = "- %s: %s - Reason: %s";
    String ENTER_MESSAGE = "Press <enter> to continue...\n";
    String TOTAL_NORMALIZED_COORDINATE_MESSAGE = "%d coordinates have been checked and corrected\n";
    String TIME_MESSAGE = "This operation took %d milliseconds.";

    String MAIN_MENU_MESSAGE = """
            ╔══════════════════════════════════════╗
            ║          WINDFARM ANALYZER           ║
            ╠══════════════════════════════════════╣
            ║ Please choose an option:             ║
            ║                                      ║
            ║  1. Show CSV-File                    ║
            ║  2. Print repository of windFarms    ║
            ║  3. Sort and show windFarms          ║
            ║  0. Exit program                     ║
            ║                                      ║
            ╚══════════════════════════════════════╝
            Your choice:""";

    String SORT_MENU_MESSAGE = """
            ╔════════════════════════════════════════════╗
            ║         SORT WINDFARMS BY CRITERIA         ║
            ╠════════════════════════════════════════════╣
            ║ Choose sorting method:                     ║
            ║                                            ║
            ║  1. By Turbine Age                         ║
            ║     ├─ a) Oldest turbine commissioning     ║
            ║     └─ b) Latest turbine commissioning     ║
            ║                                            ║
            ║  2. By Location                            ║
            ║     └─ a) Latitude                         ║
            ║           ├─ +) Ascending                  ║
            ║           └─ -) Descending                 ║
            ║                                            ║
            ║  3. By Scale                               ║
            ║     ├─ a) Wind turbine count               ║
            ║     └─ b) Manager count                    ║
            ║                                            ║
            ║  4. By Performance                         ║
            ║     └─ a) Total performance                ║
            ║           ├─ +) Ascending                  ║
            ║           └─ -) Descending                 ║
            ║                                            ║
            ║  5. By Name                                ║
            ║     └─ a) Wind farm name                   ║
            ║           ├─ +) A-Z                        ║
            ║           └─ -) Z-A                        ║
            ║                                            ║
            ║  6. Combined Sort                          ║
            ║     ├─ a) Age + Performance                ║
            ║     ├─ b) Turbine count + Performance      ║
            ║     └─ c) Turbine count + Manager count    ║
            ║                                            ║
            ║  0. Back to main menu                      ║
            ╚════════════════════════════════════════════╝
            Your choice:""";

    String EXIT_MESSAGE = "The program is closing...";


    // Light and double line box components (U+2552 bis U+256C)
    String BORDER_TOP_LEFT = "╔";
    String BORDER_TOP_RIGHT = "╗";
    String BORDER_BOTTOM_LEFT = "╚";
    String BORDER_BOTTOM_RIGHT = "╝";
    String BORDER_INNER_LEFT = "╠";
    String BORDER_INNER_RIGHT = "╣";
    String BORDER_SIDE = "║";
    String BORDER_LINE = "═";

    int BORDER_OFFSET = 2;

    // User-Wahl fuer Hauptmenue
    int SHOW_WINDPARKS = 1;
    int PRINT_WINDREPO = 2;
    int SORT_WINDREPO = 3;
    int EXIT = 0;

    // User-Wahl fuer Sort-menue
    String TURBINE_AGE_OLD = "1a";
    String TURBINE_AGE_LATE = "1b";
    String LATITUDE_ASC = "2a+";
    String LATITUDE_DESC = "2a-";
    String TURBINE_COUNT = "3a";
    String MANAGER_COUNT = "3b";
    String PERFORMANCE_ASC = "4a+";
    String PERFORMANCE_DESC = "4a-";
    String NAME_AZ = "5a+";
    String NAME_ZA = "5a-";
    String AGE_PERFORMANCE = "6a";
    String TURBINE_PERFORMANCE = "6b";
    String TURBINE_MANAGER_COUNT = "6c";
    String EXIT_STRING = "0";


    // Konstante, die fuer Tabellenausgabe verwendet werden
    int TOTAL_TABLE_WIDTH = 100;
    int ROWS_BETWEEN_FARMS = 1;
    int COL_ID = 10;
    int COL_YEAR = 15;
    int COL_LOCATION = 22;
    int ALL_SEPARATOR_COUNTER = 5;
    int COL_TYPES = TOTAL_TABLE_WIDTH - ALL_SEPARATOR_COUNTER - COL_ID - COL_YEAR - COL_LOCATION;
    int ELLIPSIS_LENGTH = 3;

    String NOT_SPECIFIED = "not specified";
    String PERFORMANCE_COORDINATES_ROW = " Performance: %s  |  Coordinates: %s";
    String PERFORMANCE_FORMAT = "%.1f MW";
    String COORDINATES_FORMAT = "%.4f - %.4f";
    String MANAGER_ROW = "Company-Manager:";
    String ID_CELL = "ID ";
    String VERTICAL_BAR = "|";
    String WINDTURBINE_FORMAT = " %s (%dx)";
    String ELLIPSIS = "...";
}
