package main;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Graph {
    private List<Vertex> vertices = new ArrayList<Vertex>();

    public Graph(Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    public int getDistance(String from, String to) {
        int distance = -1;
        int indice = trouver(from);
        if (indice != -1) {
            Vertex Depart = vertices.get(indice);
            for (Edge edge : Depart.getEdges()) {
                if (to == edge.getTarget().getName()) {
                    distance = edge.getDistance();
                }
            }
        }
        return distance;

    }

    private int trouver(String nom) {
        boolean a = false;
        int i = 0;
        while (!a && i < this.vertices.size()) {
            if (nom == vertices.get(i).getName()) {
                a = true;
            }
            i++;
        }
        if (!a) {
            i = 0;
        }
        return i - 1;
    }

    public int Distance2(String from, String by, String to) {
        int distance = -1;
        int d1 = getDistance(from, by);
        int d2 = getDistance(by, to);
        if (d1 != -1 && d2 != -1) {
            distance = d1 + d2;
        }
        return distance;
    }

    public int distance_escale(String depart, String arrivee) {
        ArrayList<Integer> distances = new ArrayList<Integer>();
        int indice = trouver(depart);
        Vertex debut = vertices.get(indice);
        for (Edge edge : debut.getEdges()) {
            int distance = Distance2(depart, edge.getTarget().getName(), arrivee);
            if (distance != -1) {
                distances.add(distance);
            }

        }
        int Distance = -1;
        if (distances.size() != 0) {
            Distance = Collections.min(distances);
        }
        return Distance;
    }

    public int distance_n_escales(String depart, String arrivee, int n) {
        int Distance = -1;
        if (n >= 2) {
            ArrayList<Integer> distances = new ArrayList<Integer>();
            int indice = trouver(depart);
            Vertex debut = vertices.get(indice);
            for (Edge edge : debut.getEdges()) {
                String by = edge.getTarget().getName();
                int d = distance_n_escales(by, arrivee, n - 1);
                if (d != -1) {
                    int distance = d + getDistance(depart, by);
                    distances.add(distance);
                }
            }
            if (distances.size() != 0) {
                Distance = Collections.min(distances);
            }
        }
        if (n==1){
            Distance= distance_escale(depart, arrivee);
        }
        if (n==0){
            getDistance(depart,arrivee);
        }
        return Distance;
    }

    public int distance_optimale(String depart, String arrivee) {
        int Distance = -1;
        ArrayList<Integer> distances = new ArrayList<Integer>();
        for (int n=0; n <= this.vertices.size(); n++ ){
            int d = distance_n_escales(depart,arrivee,n);
            if (d != -1){
                distances.add(d);
            }
        }
        if (distances.size()!=0){
            Distance = Collections.min(distances);
        }
        return Distance;
    }
}
