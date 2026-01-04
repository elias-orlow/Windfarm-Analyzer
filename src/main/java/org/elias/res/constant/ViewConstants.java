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

    String MAIN_MENU_MESSAGE = """
                        ╔══════════════════════════════════════╗
                        ║          WINDFARM ANALYZER           ║
                        ╠══════════════════════════════════════╣
                        ║ Please choose an option:             ║
                        ║                                      ║
                        ║  1. Show Windparks                   ║
                        ║  2. Print Repository of WindFarms    ║
                        ║  0. Exit Program                     ║
                        ║                                      ║
                        ╚══════════════════════════════════════╝
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


    int SHOW_WINDPARKS = 1;
    int PRINT_WINDREPO = 2;
    int EXIT = 0;

}
