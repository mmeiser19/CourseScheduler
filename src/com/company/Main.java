package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String filename = args[0];
        Scanner in1; //scanning in vertices
        Scanner in2; //scanning in edges
        try {
            FileInputStream file1 = new FileInputStream(filename);
            FileInputStream file2 = new FileInputStream(filename);
            in1 = new Scanner(file1);
            in2 = new Scanner(file2);
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be found");
            return;
        }

        int size = in1.nextInt(); //read in number of nodes
        Graph<String> graph = new Graph<>(size);

        //creates nodes for the graph
        int count = 0;
        while (in1.hasNext()) {
            //read in each course
            String course = in1.next();
            graph.setValue(count, course); //adds node for the course
            int numPrereqs = in1.nextInt(); //number of prereqs; discarded for this scanner
            for (int i = 0; i < numPrereqs; i++) {
                in1.next(); //individual prereqs; discarded for this scanner
            }
            count++;
        }

        //creates edges for the graph
        in2.nextInt(); //size of graph; discarded
        while (in2.hasNext()) {
            String course = in2.next();
            int numPrereqs = in2.nextInt(); //number of prereqs
            for (int i = 0; i < numPrereqs; i++) {
                String prereq = in2.next(); //individual prereqs
                graph.insertEdge(course, prereq); //i realized that i created the edge in the wrong direction but
                //it would mess up my code badly if i tried fixing it and i just dont wanna deal with that
                //i still get the correct solution, just the edge is in the wrong direction
            }
        }

        //topological ordering
        ArrayList<String> ordering = new ArrayList<>(); //ordering set to empty
        ArrayList<String> activeSet = new ArrayList<>(); //nodes without edges coming into them
        for (int i = 0; i < graph.getSize(); i++) {
            int prereqs = 0;
            for (int j = 0; j < graph.getSize(); j++) {
                if (graph.isEdge(i, j)) { //checks for incoming edges
                    prereqs++;
                }
            }
            if (prereqs == 0) {
                activeSet.add(graph.getValue(i)); //adds node to active set if it has no incoming edges
            }
        }
        while (!activeSet.isEmpty()) { //while there are nodes in the active set
            ordering.add(activeSet.get(0)); //move a node N from the active set to the ordering
            activeSet.remove(0); //removes node from active set
            String course = ordering.get(ordering.size()-1); //course that is checked to see if it is a prereq for another course
            int courseNum = graph.lookup(course);
            for (int i = 0; i < graph.getSize(); i++) {
                String course2 = graph.getValue(i);
                if (graph.isEdge(i, courseNum)) {
                    graph.removeEdge(i, courseNum);
                }
                //checks if course2 doesn't have any prereqs, isn't in ordering, and isn't in the active set
                //if the expression is true, then the course gets added to the active set
                if (!graph.hasPrereqs(course2) && !ordering.contains(course2) && !activeSet.contains(course2)) {
                    activeSet.add(course2);
                }
            }
        }
        int edges = 0;
        ArrayList<String> impossible = new ArrayList<>();
        for (int i = 0; i < graph.getSize(); i++) {
            for (int j = i + 1; j < graph.getSize(); j++) {
                if (graph.isEdge(i, j)) {
                    edges++;
                    String course = graph.getValue(i);
                    if (!impossible.contains(course)) { //ensures that the course is already not in impossible
                        impossible.add(course);
                    }
                }
            }
        }
        edges = graph.countEdges();
        if (edges > 0) { //no topological ordering
            System.out.println("The graph cannot be sorted topologically\n");
            System.out.println("These are the courses that are impossible to complete:");
            for (String s : impossible) {
                System.out.println(s);
            }
        }
        else { //there is topological ordering
            System.out.println("The graph was successfully ordered topologically!\n");
            for (String s : ordering) {
                System.out.println(s);
            }
        }
    }
}