package org.elias;

import org.elias.control.MainController;
import org.elias.model.service.WindFarmRepository;
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
     * Einstiegspunkt der Anwendung.
     * <p>
     * Enthaelt alle notwendigen Methoden, um das Programm zu starten.
     *
     * @precondition {@link ConsoleView} und {@link WindFarmRepository} koennen erfolgreich
     * initialisiert werden.
     * @postcondition Der {@link MainController} wurde gestartet.
     */
    static void main ()
    {
        MainController.init(ConsoleView.getInstance(), WindFarmRepository.getInstance());
        MainController mainController = MainController.getInstance();

        mainController.start();
    }
}
