package org.elias.model.service;

import org.elias.model.ProjectManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Verwaltet alle {@link ProjectManager} waehrend der Programmausfuehrung.
 * <p>
 * Diese Klasse folgt dem Singleton-Muster, sodass genau eine zentrale
 * Verwaltung aller Projektmanager existiert.
 */
public class ProjectManagerAdministration
{
    /**
     * Singleton-Instanz der Klasse
     */
    private static ProjectManagerAdministration INSTANCE = null;
    private final Set<ProjectManager> projectManagerList = new HashSet<>();


    /**
     * Privater Konstruktor zur Verhinderung externer Instanziierung.
     *
     * @precondition es existiert noch keine Instanz dieser Klasse.
     * @postcondition eine Projektmanager-Verwaltung wurde initialisiert.
     */
    private ProjectManagerAdministration ()
    {
    }


    /**
     * Liefert die Singleton-Instanz der {@link ProjectManagerAdministration}.
     * Falls noch keine Instanz existiert, wird sie erzeugt.
     *
     * @return die einzige Instanz der {@code ProjectManagerAdministration}.
     * @precondition keine.
     * @postcondition eine gueltige Instanz der Klasse ist verfuegbar.
     */
    public static ProjectManagerAdministration getInstance ()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ProjectManagerAdministration();
        }
        return INSTANCE;
    }

    // --- Getter ---

    public Set<ProjectManager> getProjectManagerList ()
    {
        return projectManagerList;
    }


    /**
     * Fuegt einen neuen Projektmanager zur Verwaltung hinzu.
     * <p>
     * Da ein Set verwendet wird, werden doppelte Projektmanager automatisch ignoriert.
     *
     * @param projectManager der hinzuzufuegende Projektmanager.
     * @precondition {@code projectManager} ist nicht null.
     * @postcondition der Projektmanager ist in der Verwaltung enthalten,
     * wenn er vorher noch nicht existiert hat.
     */
    public void addProjectManager (ProjectManager projectManager)
    {
        projectManagerList.add(projectManager);
    }

}
