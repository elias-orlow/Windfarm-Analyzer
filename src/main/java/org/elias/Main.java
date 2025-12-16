package org.elias;

import org.elias.control.MainController;
import org.elias.model.WindFarmRepository;
import org.elias.view.ConsoleView;

/**
 * Enthaelt die {@code main()} methode.
 *
 * @author Elias
 * @version 1.0
 */
public class Main
{
    /**
     * Enthaelt alle notwendigen Methoden, um das Programm zu starten.
     */
    static void main ()
    {
        MainController.init(ConsoleView.getInstance(), WindFarmRepository.getInstance());
        MainController mainController = MainController.getInstance();

        mainController.start();
    }
}
