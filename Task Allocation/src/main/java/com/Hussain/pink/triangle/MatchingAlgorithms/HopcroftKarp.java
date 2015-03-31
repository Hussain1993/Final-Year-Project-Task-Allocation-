package com.Hussain.pink.triangle.MatchingAlgorithms;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.GroupTask;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.*;

/**
 * http://jgrapht.org/
 * https://github.com/jgrapht/jgrapht
 *
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
        return matching;
    }

    /**
     * This is the start of the HopcroftKarp algorithm, where we will
     * be doing a greedy match of the employees within the graph,
     * then augmenting paths will be found from all the unmatched
     * employees
     * @param employeeNodes All the unmatched employee nodes
     * @param biPartiteGraph We pass this graph to the method
     *                       because it allows us get the object using
     *                       its name
     */
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
                processAugmentingPath(augmentingPath,biPartiteGraph);
                it.remove();
            }
            augmentingPaths.addAll(findAugmentingPaths(biPartiteGraph));
        }
    }

    /**
     * This processes the augmenting path, as the augmenting path alternates between
     * edges that are not in the matching and edges that are in the matching, this method
     * will remove the relevant objects from the matching object
     * @param augmentingPath The augmenting path to process
     * @param biPartiteGraph So we can get the object from the string
     */
    private void processAugmentingPath(LinkedList<String> augmentingPath, BiPartiteGraph biPartiteGraph){
        int operation = 0;//Because the augmenting path alternates between an edge that is in the matching and a new matching
        while (augmentingPath.size() > 0)
        {
            String startNode = augmentingPath.poll();
            String endNode = augmentingPath.peek();
            if((operation % 2) == 0)
            {
                if(!matching.containsMatching(startNode,endNode))
                {
                    String taskMatchedPreviously = matching.getMatching().get(startNode);
                    addTaskToProject(taskMatchedPreviously,biPartiteGraph);
                    matching.addMatching(startNode,endNode);
                }
            }
            else
            {
                matching.removeMatching(endNode);
                addTaskToProject(endNode,biPartiteGraph);
            }
            operation++;
        }
    }

    /**
     * This method finds the augmenting paths within
     * the bipartite graph, starting from all the unmatched
     * employee nodes
     * @param biPartiteGraph This is so that we can get the
     *                       object from the name
     * @return A list representing a augmenting path
     */
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

    /**
     * Depth first search on the tree that has been created to
     * find an augmenting path
     * @param startNode This is the start node of the search
     * @param bfsMap This is the tree to search on
     * @return A list representing an augmenting path
     */
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

    /**
     * Checks to see if two sets intersect.
     * This is when two sets, share a common element
     * @param set1 This is the first set to check
     * @param set2 This is the second set to check
     * @return Return true if second set contains at least
     * one element that is contained within the first set, false otherwise
     */
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

    /**
     * Helper method to deal with when the user decides to
     * group the tasks by their projects
     * @param taskName This is the task name
     * @param biPartiteGraph This is so we can get the object from the name
     */
    private void addTaskToProject(String taskName, BiPartiteGraph biPartiteGraph){
        Task task = biPartiteGraph.getTaskByName(taskName);
        if(AdvancedOptions.groupTasksByProject())
        {
            GroupTask.addNewTaskToGroup(task.getProject());
        }
    }

}
