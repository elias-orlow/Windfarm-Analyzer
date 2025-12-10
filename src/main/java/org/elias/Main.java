package org.elias;

import org.elias.control.MainController;
import org.elias.view.ConsoleView;

/**
 * Enthaelt die {@code main()} methode.
 *
 * @author Elias
 */
public class Main
{
    /**
     * Enthaelt alle notwendigen Methoden, um das Programm zu starten.
     *
     */
    static void main ()
    {
        ConsoleView consoleView = ConsoleView.getInstance();

        MainController.init(consoleView);
        MainController mainController = MainController.getInstance();

        mainController.start();
    }
}
