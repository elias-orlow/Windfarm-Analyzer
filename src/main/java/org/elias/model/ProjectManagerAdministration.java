package org.elias.model;

import java.util.HashSet;
import java.util.Set;

public class ProjectManagerAdministration
{
    private static ProjectManagerAdministration INSTANCE = null;
    private final Set<ProjectManager> projectManagerList = new HashSet<>();

    private ProjectManagerAdministration ()
    {
    }

    public static ProjectManagerAdministration getInstance ()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ProjectManagerAdministration();
        }
        return INSTANCE;
    }

    public Set<ProjectManager> getProjectManagerList ()
    {
        return projectManagerList;
    }

    public void addProjectManager (ProjectManager projectManager)
    {
        projectManagerList.add(projectManager);
    }

}
