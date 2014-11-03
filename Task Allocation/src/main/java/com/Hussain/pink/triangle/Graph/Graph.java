package com.Hussain.pink.triangle.Graph;

import java.util.*;

/**
 * Created by Rai Skumar
 * http://geekrai.blogspot.in/2014/08/graphjava.html
 */
public class Graph<V> {
    private Map<V,List<Node<V>>> list;
    private Set<V> verticies;

    public Graph(){
        list = new HashMap<>();
        verticies = new HashSet<>();
    }


    private static class Node<V>{
        private V name; //The name of the vertex

        public Node(V name){
            this.name = name;
        }

        public V getName(){
            return this.name;
        }

        @Override
        public int hashCode(){
            return this.getName().hashCode();
        }

        @Override
        public String toString(){
            return this.name + "";
        }
    }

    public Map<V,List<Node<V>>> getMap(){
        return this.list;
    }

    public boolean isEmpty(){
        return verticies.isEmpty();
    }

    private void addEdge(V source, Node<V> destination){
        List<Node<V>> adjacentVertices = list.get(source);
        if(adjacentVertices == null || adjacentVertices.isEmpty())
        {
            adjacentVertices = new ArrayList<Node<V>>();
            adjacentVertices.add(destination);
        }
        else
        {
            adjacentVertices.add(destination);
        }
        list.put(source,adjacentVertices);
    }

    public void addEdge(V source, V destination){
        this.addEdge(source, new Node<V>(destination));

        //The nodes and the edges have been added update the
        //set containing all the vertices
        this.verticies.add(source);
        this.verticies.add(destination);

    }

    public boolean hasRelationship(V source, V destination){
        if(source == null && destination == null)
        {
            return true;
        }
        if(source != null && destination == null)
        {
            return true;
        }
        if(source == null && destination != null)
        {
            return false;
        }
        List<Node<V>> nodes;

        if(list.containsKey(source))
        {
            nodes = list.get(source);
            if(nodes != null && !nodes.isEmpty())
            {
                for(Node<V> neighbors : nodes)
                {
                    if(neighbors.getName().equals(destination))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<V> getAdjacentVertices(V vertex){
        List<Node<V>> adjacentNodes = this.list.get(vertex);
        List<V> neighborVertex = new ArrayList<>();

        if(adjacentNodes != null && !adjacentNodes.isEmpty())
        {
            for(Node<V> v : adjacentNodes)
            {
                neighborVertex.add(v.getName());
            }
        }
        return neighborVertex;
    }

    public Set<V> getAllVertices(){
        return Collections.unmodifiableSet(this.verticies);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Set of Edges :\n");
        for (V v : this.list.keySet()) {
            List<Node<V>> neighbour = this.list.get(v);
            for (Node<V> vertex : neighbour) {
                sb.append(v + " ----->" + vertex.getName() + "\n");
            }
        }
        sb.append("& Set of vertices :" + this.getAllVertices());
        return sb.toString();
    }
}
