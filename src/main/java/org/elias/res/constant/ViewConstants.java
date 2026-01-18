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
    String SOUTHERNMOST_MESSAGE = "The southernmost wind-farm is: ";
    String HIGHEST_PERFORMANCE_MESSAGE = "The wind-farm with highest performance is: ";
    String MOST_TURBINES_MESSAGE = "The wind-farm with most turbines is: ";
    String TOTAL_PERFORMANCE_MESSAGE = "The total performance of all wind-farms is %.2f MW";
    String UPDATED_PERFORMANCE_MESSAGE = "Updated the performance data of %d wind farms.";
    String CONVERTED_PERFORMANCE_NAME = "%s: converted %.2f kW to %.4f MW";
    String CALCULATED_PERFORMANCE_NAME = "%s: calculated from the adjacent wind farm %.4f MW";
    String CHOICE_PROJECT_MANAGER_MESSAGE = "Select a wind‑park manager to develop the work plan:";
    String CHOICE_START_POINT_MESSAGE = "Choose the plan’s starting point:";
    String INDEX_DATA_MESSAGE = "{ %d }  -->  %s";


    String MAIN_MENU_MESSAGE = """
            ╔══════════════════════════════════════╗
            ║          WINDFARM ANALYZER           ║
            ╠══════════════════════════════════════╣
            ║ Please choose an option:             ║
            ║                                      ║
            ║  1. Show CSV-File                    ║
            ║  2. Print repository of wind-farms   ║
            ║  3. Sort and show wind-farms         ║
            ║  4. Analysis of wind-farms           ║
            ║  5. Make working plan                ║
            ║  0. Exit program                     ║
            ║                                      ║
            ╚══════════════════════════════════════╝
            Your choice:""";

    String SORT_MENU_MESSAGE = """
            ╔════════════════════════════════════════════╗
            ║        SORT WIND-FARMS BY CRITERIA         ║
            ╠════════════════════════════════════════════╣
            ║ Choose sorting method:                     ║
            ║                                            ║
            ║  1. By ID                                  ║
            ║     ├─ a) Ascending                        ║
            ║     └─ b) Descending                       ║
            ║                                            ║
            ║  2. By Turbine Age                         ║
            ║     ├─ a) Oldest turbine commissioning     ║
            ║     └─ b) Latest turbine commissioning     ║
            ║                                            ║
            ║  3. By Location                            ║
            ║     └─ a) Latitude                         ║
            ║           ├─ +) Ascending                  ║
            ║           └─ -) Descending                 ║
            ║                                            ║
            ║  4. By Scale                               ║
            ║     ├─ a) Wind turbine count               ║
            ║     └─ b) Manager count                    ║
            ║                                            ║
            ║  5. By Performance                         ║
            ║     └─ a) Total performance                ║
            ║           ├─ +) Ascending                  ║
            ║           └─ -) Descending                 ║
            ║                                            ║
            ║  6. By Name                                ║
            ║     └─ a) Wind farm name                   ║
            ║           ├─ +) A-Z                        ║
            ║           └─ -) Z-A                        ║
            ║                                            ║
            ║  7. Combined Sort                          ║
            ║     ├─ a) Age + Performance                ║
            ║     ├─ b) Turbine count + Performance      ║
            ║     └─ c) Turbine count + Manager count    ║
            ║                                            ║
            ║  0. Back to main menu                      ║
            ╚════════════════════════════════════════════╝
            Your choice:""";

    String ANALYSIS_MENU_MESSAGE = """
            ╔════════════════════════════════════════════════════╗
            ║              WINDFARM ANALYSIS MENU                ║
            ╠════════════════════════════════════════════════════╣
            ║ Please choose an analysis option:                  ║
            ║                                                    ║
            ║  1. Find southernmost wind-farm                    ║
            ║  2. Find wind-farm with highest performance        ║
            ║  3. Find wind-farm with most turbines              ║
            ║  4. Calculate total power of all wind-farms        ║
            ║                                                    ║
            ║  0. Return to main menu                            ║
            ╚════════════════════════════════════════════════════╝
            Your choice:""";

    String EXIT_MESSAGE = "The program is closing...";

    String PLAN_TABLE_HEAD = """
            ==============================
                    MAINTENANCE PLAN
            ==============================
            """;


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
    int ANALYSIS_WINDREPO = 4;
    int WINDFARM_PLAN = 5;
    int EXIT = 0;

    // User-Wahl fuer Sort-menue
    String ID_ASC = "1a";
    String ID_DESC = "1b";
    String TURBINE_AGE_OLD = "2a";
    String TURBINE_AGE_LATE = "2b";
    String LATITUDE_ASC = "3a+";
    String LATITUDE_DESC = "3a-";
    String TURBINE_COUNT = "4a";
    String MANAGER_COUNT = "4b";
    String PERFORMANCE_ASC = "5a+";
    String PERFORMANCE_DESC = "5a-";
    String NAME_AZ = "6a+";
    String NAME_ZA = "6a-";
    String AGE_PERFORMANCE = "7a";
    String TURBINE_PERFORMANCE = "7b";
    String TURBINE_MANAGER_COUNT = "7c";
    String EXIT_STRING = "0";

    // User-Wahl fuer Analysis-menue
    int FIND_SOUTHERNMOST = 1;
    int FIND_HIGHEST_PERFORMANCE = 2;
    int FIND_MOST_TURBINES = 3;
    int CALCULATE_TOTAL_PERFORMACE = 4;

    // Konstante, die fuer Tabellenausgabe verwendet werden
    int TOTAL_TABLE_WIDTH = 100;
    int ROWS_BETWEEN_FARMS = 1;
    int COL_ID = 10;
    int COL_YEAR = 15;
    int COL_LOCATION = 22;
    int ALL_SEPARATOR_COUNTER = 5;
    int COL_TYPES = TOTAL_TABLE_WIDTH - ALL_SEPARATOR_COUNTER - COL_ID - COL_YEAR - COL_LOCATION;
    int ELLIPSIS_LENGTH = 3;

    int TOTAL_DAYS_TABLE_WIDTH = 70;
    int ROWS_BETWEEN_DAYS = 1;

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
