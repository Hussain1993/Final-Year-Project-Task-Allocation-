package com.Hussain.pink.triangle.Model.Graph;

/**
 * Created by Hussain on 27/01/2015.
 */
public class Node<V> {
    private V object;
    private NodeType nodeType;

    public Node(V object, NodeType nodeType) {
        this.object = object;
        this.nodeType = nodeType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    @Override
    public String toString(){
        return "[Node: " + object.toString() + " Type:" + getNodeType()+"]";
    }

    public V getObject(){
        return this.object;
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
        if(!(other instanceof Node))
        {
            return false;
        }
        Node otherNode = (Node) other;
        return this.object.equals(otherNode.getObject()) && this.getNodeType().equals(otherNode.getNodeType());
    }

    @Override
    public int hashCode(){
        int hash = 1;
        hash = hash * 17 + getObject().hashCode();
        hash = hash * 13 + getNodeType().hashCode();
        return hash;
    }
}
