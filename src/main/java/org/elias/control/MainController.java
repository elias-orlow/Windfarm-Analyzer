package org.elias.control;

import org.elias.model.*;
import org.elias.res.constant.ErrorMessages;
import org.elias.res.constant.ProjectConstants;
import org.elias.res.constant.ViewConstants;
import org.elias.util.CSVFileReader;
import org.elias.util.CSVLineParser;
import org.elias.view.ConsoleView;

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

        programLoop();
        //List<String[]> test = CSVFileReader.convertToDataUnit(new ArrayList<>(List.of("1,Windpark Wittighausen,2002,5.94,9,Vestas V47-660kW (9×),Unterwittighausen,TBB,49.6197,9.8028,UMaAG,Am 17.3.2019 brach durch das Sturmtief Eberhard bei einer Anlage ein Flügel ab. Die Anlage wurde nicht repariert. Der Windpark ist derzeit außer betrieb.,,,,,,,")));
        /* Test
        List<String[]> test = CSVLineParser.convertToDataUnit(CSVFileReader.convertCSVtoList(ProjectConstants.PATH_TO_CSV));
        for (String[] row : test){
            for (String item : row)
            {
                System.out.print(item + " | ");
            }
            System.out.println();
        }
        */

    }

    private void programLoop ()
    {
        boolean isRunning = true;

        while (isRunning)
        {
            view.showMenu();
            int userChoice = view.getChoice();

            switch (userChoice)
            {
                case ViewConstants.SHOW_WINDPARKS:
                    view.printCSV(CSVFileReader.convertCSVtoList(ProjectConstants.PATH_TO_CSV));
                    break;
                case ViewConstants.EXIT:
                    isRunning = false;
                    //TODO: goodbye message
                    break;
            }
        }

    }

}
