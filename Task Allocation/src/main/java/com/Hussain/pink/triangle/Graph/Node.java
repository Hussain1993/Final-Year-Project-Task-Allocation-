package com.Hussain.pink.triangle.Graph;

/**
 * Created by Hussain on 14/11/2014.
 */
public class Node<V> {
    private V element;

    public Node(V element) {
        this.element = element;
    }

    public V getElement(){
        return this.element;
    }

    @Override
    public int hashCode(){
        return this.element.hashCode();
    }

    @Override
    public String toString(){
        return this.element.toString();
    }

    @Override
    public boolean equals(Object other) {
        if(other == null)
        {
            return false;
        }
        if(other == this)
        {
            return true;
        }
        if(! (other instanceof Node))
        {
            return false;
        }
        Node otherNode = (Node) other;
        V myElement = getElement();
        V otherElement = (V) otherNode.getElement();
        if(myElement.equals(otherElement))
        {
            return true;
        }
        return false;
    }
}
