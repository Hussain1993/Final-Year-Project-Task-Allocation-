package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;

import java.util.*;

/**
 * Created by Hussain on 27/02/2015.
 */
public class HopcroftKarp extends BiPartiteMatching{

    @Override
    public Matching<String> allocateTasks(BiPartiteGraph biPartiteGraph) {
        Set<String> employeeNodes = biPartiteGraph.getEmployeeNodes();
        Set<String> taskNodes = biPartiteGraph.getTaskNodes();
        unmatchedEmployees = new LinkedHashSet<>(employeeNodes);
        unmatchedTasks = new LinkedHashSet<>(taskNodes);
        hopcroftKarp(employeeNodes,biPartiteGraph);
        logUnmatchedEmployeesAndTasks();
        return matching;
    }

    private void hopcroftKarp(Set<String> employeeNodes, BiPartiteGraph biPartiteGraph){
        matching = GreedyMatching.greedy(unmatchedEmployees,unmatchedTasks,employeeNodes,biPartiteGraph);
        List<LinkedList<String>> augmentingPaths = findAugmentingPaths(biPartiteGraph);
        while(!augmentingPaths.isEmpty())
        {
            for (Iterator<LinkedList<String>> it = augmentingPaths.iterator(); it.hasNext();)
            {
                LinkedList<String> augmentingPath = it.next();
                unmatchedEmployees.remove(augmentingPath.getFirst());
                unmatchedTasks.remove(augmentingPath.getLast());
                processAugmentingPath(augmentingPath);
                it.remove();
            }
            augmentingPaths.addAll(findAugmentingPaths(biPartiteGraph));
        }
    }

    private void processAugmentingPath(LinkedList<String> augmentingPath){
        int operation = 0;
        while (augmentingPath.size() > 0)
        {
            String startNode = augmentingPath.poll();
            String endNode = augmentingPath.peek();
            if((operation % 2) == 0)
            {
                matching.addMatching(startNode,endNode);
            }
            else
            {
                matching.removeMatching(startNode);
            }
            operation++;
        }
    }

    private List<LinkedList<String>> findAugmentingPaths(BiPartiteGraph biPartiteGraph){
        List<LinkedList<String>> augmentingPaths = new ArrayList<>();

        HashMap<String, Set<String>> bfsMap = new HashMap<>();
        for (String employee : unmatchedEmployees)
        {
            bfsMap.put(employee, new HashSet<String>());
        }

        Set<String> evenLayer = new HashSet<>(unmatchedEmployees);
        Set<String> oddLayer;
        Set<String> nodesWeHaveUsed = new HashSet<>(unmatchedEmployees);

        while(true)
        {
            oddLayer = new HashSet<>();
            for (String node : evenLayer)
            {
                List<String> adjacentNodes = biPartiteGraph.getAdjacentNodes(node);
                for (String adjacentNode : adjacentNodes)
                {
                    if (!nodesWeHaveUsed.contains(adjacentNode))
                    {
                        oddLayer.add(adjacentNode);
                        if(!bfsMap.containsKey(adjacentNode))
                        {
                            bfsMap.put(adjacentNode, new HashSet<String>());
                        }
                        bfsMap.get(adjacentNode).add(node);
                    }
                }
            }
            nodesWeHaveUsed.addAll(oddLayer);

            if(oddLayer.size() == 0 || doSetsIntersect(oddLayer,unmatchedTasks))
            {
                break;
            }

            evenLayer = new HashSet<>();
            for (String node : oddLayer)
            {
                List<String> adjacentNodes = biPartiteGraph.getAdjacentNodes(node);
                for (String adjacentNode : adjacentNodes)
                {
                    if(!nodesWeHaveUsed.contains(adjacentNode) || !matching.containsMatching(node,adjacentNode))
                    {
                        evenLayer.add(adjacentNode);
                        if(!bfsMap.containsKey(adjacentNode))
                        {
                            bfsMap.put(adjacentNode,new HashSet<String>());
                        }
                        bfsMap.get(adjacentNode).add(node);
                    }
                }
            }
            nodesWeHaveUsed.addAll(evenLayer);
        }

        if(oddLayer.size() == 0)
        {
            return augmentingPaths;
        }
        else
        {
            oddLayer.retainAll(unmatchedTasks);
        }
        for(String node : oddLayer)
        {
            LinkedList<String> augmentingPath = depthFirstSearch(node,bfsMap);
            if(augmentingPath != null)
            {
                augmentingPaths.add(augmentingPath);
                for (String augmentingNode : augmentingPath)
                {
                    bfsMap.remove(augmentingNode);
                }
            }
        }
        return augmentingPaths;
    }

    private LinkedList<String> depthFirstSearch(String startNode, HashMap<String,Set<String>> bfsMap){
        if(!bfsMap.containsKey(startNode))
        {
            return null;
        }
        else if(unmatchedEmployees.contains(startNode))
        {
            LinkedList<String> list = new LinkedList<>();
            list.add(startNode);
            return list;
        }
        else
        {
            LinkedList<String> buildPath = null;
            for (String node : bfsMap.get(startNode))
            {
                buildPath = depthFirstSearch(node,bfsMap);
                if(buildPath != null)
                {
                    buildPath.add(startNode);
                    break;
                }
            }
            return buildPath;
        }
    }

    private boolean doSetsIntersect(Set<String> set1, Set<String> set2){
        for(String element : set1)
        {
            if(set2.contains(element))
            {
                return true;
            }
        }
        return false;
    }

}
