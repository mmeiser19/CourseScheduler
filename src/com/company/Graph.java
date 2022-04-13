package com.company;

class Graph<Type> {
    // the matrix stores the edge information
    private boolean[][] matrix;
    // this stores the values being stored by this graph
    private Type[] values;
    // the size of the graph
    private int size;
    // set the graph as empty
    public Graph(int size) {
        matrix = new boolean[size][size];
        this.size = size;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                matrix[i][j] = false;
            }
        }
        // make space for the values (and ignore the cast warning)
        @SuppressWarnings("unchecked")
        Type[] values = (Type[]) new Object[size];
        this.values = values;
    }
    // lookup a node number by value
    public int lookup(Type value) {
        for (int i = 0; i < size; i++) {
            if (values[i] != null && values[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
    // insert an edge by index
    public void insertEdge(int from, int to) {
        matrix[from][to] = true;
    }
    // insert an edge by value
    public void insertEdge(Type from, Type to) {
        int fromIndex = lookup(from);
        int toIndex = lookup(to);
        insertEdge(fromIndex, toIndex);
    }
    // remove an edge
    public void removeEdge(int from, int to) {
        matrix[from][to] = false;
    }
    // return whether these are connected
    public boolean isEdge(int from, int to) {
        return matrix[from][to];
    }
    // add a node's data to the graph
    public void setValue(int node, Type value) {
        values[node] = value;
    }
    // return the size of the graph
    public int getSize() {
        return size;
    }
    // get the value of a node
    public Type getValue(int index) {
        return values[index];
    }

    public int countEdges() {
        int edges = 0;
        for (int i = 0; i < getSize(); i++) {
            for (int j = i + 1; j < getSize(); j++) {
                if (isEdge(i, j)) {
                    edges++;
                }
            }
        }
        return edges;
    }

    public boolean hasPrereqs(Type course) {
        int i = lookup(course);
        for (int j = 0; j < size; j++) {
            if (isEdge(i, j)) {
                return true;
            }
        }
        return false;
    }
}
