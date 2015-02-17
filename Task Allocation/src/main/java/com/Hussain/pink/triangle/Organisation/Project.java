package com.Hussain.pink.triangle.Organisation;

/**
 * Created by Hussain on 17/02/2015.
 */
public class Project {
    private int projectID;
    private String projectName;

    public Project(int projectID, String projectName){
        this.projectID = projectID;
        this.projectName = projectName;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public String toString(){
        return "Project: "+getProjectName();
    }

    @Override
    public boolean equals(Object other){
        if(other == null)
        {
            return false;
        }
        if(other == this)
        {
            return true;
        }
        if(!(other instanceof Project))
        {
            return false;
        }
        Project otherProject = (Project) other;
        return this.getProjectID() == otherProject.getProjectID();
    }

    @Override
    public int hashCode(){
        int hash = 1;
        hash = hash * 17 + getProjectID();
        hash = hash * 5 + getProjectName().hashCode();
        return hash;
    }
}
