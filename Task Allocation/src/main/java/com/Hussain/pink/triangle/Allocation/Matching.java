package com.Hussain.pink.triangle.Allocation;

import java.util.HashMap;

/**
 * This is a class that will be used to store all the
 * matches from employee to a task
 * Created by Hussain on 02/03/2015.
 */
public class Matching<V> {
    private HashMap<V,V> matching;//This ensures there is a one-to-one mapping, from employees to tasks

    public Matching(){
        matching = new HashMap<>();
    }

    /**
     * Add a new matching to the set
     * @param employee The employee that has been matched to the task
     * @param task The task that has been matched to the employee
     */
    public void addMatching(V employee, V task){
        matching.put(employee,task);
    }

    /**
     * Remove a matching from the set
     * @param employee The employee to remove the matching from
     */
    public void removeMatching(V employee){
        matching.remove(employee);
    }

    /**
     * Return the matching graph
     * @return The matching graph
     */
    public HashMap<V, V> getMatching() {
        return matching;
    }

    /**
     * Checks to see if there are any matches
     * @return true if there matches within the set, false otherwise
     */
    public boolean hasMatch(){
        return matching.size() >= 1;
    }

    /**
     * Checks to see if the set contains the specified matching
     * @param startNode This is the key of the set
     * @param endNode This is the value
     * @return true if the set contains the matching false otherwise
     */
    public boolean containsMatching(V startNode, V endNode){
        return matching.containsKey(startNode) && matching.get(startNode).equals(endNode);
    }


    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (V key : matching.keySet())
        {
            stringBuilder.append(String.format("(%s:%s)\n",key,matching.get(key)));
        }
        return stringBuilder.toString().trim();
    }
}
