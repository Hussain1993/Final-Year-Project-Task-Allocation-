package com.Hussain.pink.triangle.Allocation;

import java.util.HashMap;

/**
 * Created by Hussain on 02/03/2015.
 */
public class Matching<V> {
    private HashMap<V,V> matching;//This ensures there is a one-to-one mapping, from employees to tasks

    public Matching(){
        matching = new HashMap<>();
    }

    public void addMatching(V employee, V task){
        matching.put(employee,task);
    }

    public void removeMatching(V employee){
        matching.remove(employee);
    }

    public HashMap<V, V> getMatching() {
        return matching;
    }

    public boolean hasMatch(){
        return matching.size() >= 1;
    }

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
