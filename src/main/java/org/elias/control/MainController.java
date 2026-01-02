package org.elias.control;

import org.elias.model.*;
import org.elias.res.constant.ErrorMessages;
import org.elias.res.constant.ProjectConstants;
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
    private static MainController INSTANCE;

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
        List<String> dataRows = CSVFileReader.convertCSVtoList(ProjectConstants.PATH_TO_CSV);
        List<String[]> CSVDataCells = CSVLineParser.convertToDataUnit(dataRows);
        WindFarmImporter.importData(CSVDataCells, germanWindFarms);

        programLoop();

    }

    private void programLoop ()
    {
        boolean isRunning = true;

        while (isRunning)
        {
            view.showMenu(); //TODO: Make menu more beautiful
            int userChoice = view.getChoice();

            switch (userChoice)
            {
                case ViewConstants.SHOW_WINDPARKS:
                    view.printCSV(CSVFileReader.convertCSVtoList(ProjectConstants.PATH_TO_CSV));
                    break;
                case ViewConstants.PRINT_WINDREPO:
                    TablePrinter tablePrinter = new TablePrinter();
                    TableController tableController = new TableController(tablePrinter);
                    tableController.printRepository(germanWindFarms);
                    break;
                case ViewConstants.EXIT:
                    isRunning = false;
                    //TODO: goodbye message
                    break;
            }
        }

    }

}
