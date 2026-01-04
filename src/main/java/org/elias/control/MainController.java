package org.elias.control;

import org.elias.model.*;
import org.elias.res.constant.ErrorMessages;
import org.elias.res.constant.GeneralConstants;
import org.elias.res.constant.ViewConstants;
import org.elias.util.CSVFileReader;
import org.elias.util.CSVLineParser;
import org.elias.view.ConsoleView;
import org.elias.view.TablePrinter;

import java.util.*;

/**
 * Singleton main Controller für den Hauptablauf der Anwendung.
 * Verwaltet die Interaktion mit der ConsoleView.
 * <p>
 * Diese Klasse folgt dem Singleton-Muster und muss vor der Verwendung
 * mit {@link #init(ConsoleView, WindFarmRepository)} initialisiert werden.
 */
public final class MainController
{
    private static MainController INSTANCE = null;

    /**
     * Die mit dem Controller verbundene ConsoleView.
     */
    private final ConsoleView view;
    private final WindFarmRepository germanWindFarms;


    private MainController (ConsoleView view, WindFarmRepository germanWindFarms)
    {
        this.view = view;
        this.germanWindFarms = germanWindFarms;
    }


    /**
     * Gibt die Singleton-Instanz zurück.
     *
     * @return die existierende MainController-Instanz
     * @throws IllegalStateException wenn die Instanz noch nicht initialisiert wurde
     * @precondition {@code init(ConsoleView view)} wurde vorher aufgerufen
     * @postcondition die gleiche Singleton-Instanz wird zurückgegeben
     */
    public static MainController getInstance ()
    {
        if (INSTANCE == null)
        {
            throw new IllegalStateException(ErrorMessages.MAIN_CONTROLLER_NOT_INITIALIZED);
        }
        return INSTANCE;
    }


    /**
     * Initialisiert den {@code MainController} mit einer {@code ConsoleView}.
     * Muss vor {@code getInstance()} aufgerufen werden.
     *
     * @param view die zu MainController verbindende ConsoleView
     * @throws IllegalStateException wenn die Instanz bereits initialisiert wurde
     * @precondition view ist nicht leer
     * @postcondition {@link #INSTANCE} wird auf eine neue MainController-Instanz gesetzt
     */
    public static void init (ConsoleView view, WindFarmRepository germanWindFarms)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new MainController(view, germanWindFarms);
        } else
        {
            throw new IllegalStateException(ErrorMessages.MAIN_CONTROLLER_ALREADY_INITIALIZED);
        }
    }


    public void start ()
    {
        view.printMessage(ViewConstants.WELCOME_MESSAGE);

        List<String> dataRows = CSVFileReader.convertCSVtoList(GeneralConstants.PATH_TO_CSV);
        List<String[]> CSVDataCells = CSVLineParser.convertToDataUnit(dataRows);
        WindFarmImporter.importData(CSVDataCells, germanWindFarms);

        calculateData();
        waitForEnter();
        programLoop();
    }


    private void calculateData ()
    {
        int windFarmCount = germanWindFarms.getGermanWindFarms().size();
        int invalidRowCount = germanWindFarms.getInvalidRows().size();

        view.printMessage(String.format(ViewConstants.TOTAL_WINDFARM_MESSAGE, windFarmCount));
        view.printMessage(String.format(ViewConstants.TOTAL_INVALID_MESSAGE, invalidRowCount));

        for (String[] failedRow : germanWindFarms.getInvalidRows().keySet())
        {
            view.printMessage(String.format(ViewConstants.FAILED_CAUSE_MESSAGE,
                    failedRow[0],
                    failedRow[1],
                    germanWindFarms.getInvalidRows().get(failedRow)));
        }

        view.makeSpace(GeneralConstants.INT_ONE);
    }


    private void programLoop ()
    {
        boolean isRunning = true;

        while (isRunning)
        {
            view.showMenu(); //TODO: Make menu better
            int userChoice = view.getChoice();

            switch (userChoice)
            {
                case ViewConstants.SHOW_WINDPARKS:
                    view.printCSV(CSVFileReader.convertCSVtoList(GeneralConstants.PATH_TO_CSV));
                    break;
                case ViewConstants.PRINT_WINDREPO:
                    TableController tableController = new TableController(TablePrinter.getInstance());
                    tableController.printRepository(germanWindFarms);
                    break;
                case ViewConstants.EXIT:
                    isRunning = false;
                    //TODO: goodbye message
                    break;
            }
        }
    }


    public void waitForEnter ()
    {
        boolean isNotEnter = true;

        while (isNotEnter)
        {
            view.pressEnterToContinue();

            if (view.getUserInput().isEmpty())
            {
                isNotEnter = false;
            }
        }
    }
}
