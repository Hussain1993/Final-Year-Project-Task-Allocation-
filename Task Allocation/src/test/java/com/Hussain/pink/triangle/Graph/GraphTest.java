package com.Hussain.pink.triangle.Graph;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hussain on 03/11/2014.
 */
public class GraphTest {
    private  Graph<String> graph;

    @Before
    public void setUp(){
        graph = new Graph<String>();
    }

    @Test
    public void addEdgeTest(){
        graph.addEdge("LHR","JFK");//Added the edge now make sure that the relationship is there
        assertTrue(graph.hasRelationship("LHR","JFK"));
    }

    @Test
    public void getAdjacentVerticesTest(){
        graph.addEdge("A","B");
        graph.addEdge("A","C");
        List<String> adjacentVertices = graph.getAdjacentVertices("A");
        String [] vertices = adjacentVertices.toArray(new String [] {});
        assertArrayEquals(vertices,new String [] {"B","C"});
    }

    @Test
    public void getAllVerticesTest(){
        graph.addEdge("A","B");
        graph.addEdge("A","C");

        Set<String> verticesSet = graph.getAllVertices();
        String [] vertices = verticesSet.toArray(new String [] {});
        assertArrayEquals(vertices,new String [] {"A","B","C"});
    }
}
