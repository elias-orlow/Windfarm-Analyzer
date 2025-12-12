package org.elias.control;

import org.elias.res.constant.ErrorMessages;
import org.elias.util.CSVFileReader;
import org.elias.view.ConsoleView;

/**
 * Singleton main Controller für den Hauptablauf der Anwendung.
 * Verwaltet die Interaktion mit der ConsoleView.
 * <p>
 * Diese Klasse folgt dem Singleton-Muster und muss vor der Verwendung
 * mit {@link #init(ConsoleView)} initialisiert werden.
 */
public final class MainController
{
    private static MainController INSTANCE;

    /**
     * Die mit dem Controller verbundene ConsoleView.
     */
    private final ConsoleView view;


    private MainController (ConsoleView view)
    {
        this.view = view;
    }


    /**
     * Gibt die Singleton-Instanz zurück.
     *
     * @return die existierende MainController-Instanz
     * @throws IllegalStateException wenn die Instanz noch nicht initialisiert wurde
     * @precondition {@code init(ConsoleView view} wurde vorher aufgerufen
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
    public static void init (ConsoleView view)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new MainController(view);
        } else
        {
            throw new IllegalStateException(ErrorMessages.MAIN_CONTROLLER_ALREADY_INITIALIZED);
        }
    }


    public void start ()
    {
        boolean isRunning = true;

        while (isRunning)
        {
            for (String line : CSVFileReader.convertCSVtoList("src/main/resources/Windkraftanlagen_DE.csv"))
            {
                System.out.println(line);
            }


            isRunning = false;
        }

    }

}
