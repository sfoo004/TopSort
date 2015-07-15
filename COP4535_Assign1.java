
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author stevefoo
 */
public class Topological {

    public static void main(String[] args) {
        for (String str : args) {
            try {
                System.out.print(str+": ");
                double start = System.nanoTime();
                construct(str);
                double stop = System.nanoTime();
                double math = (stop - start)/1000000000;
                System.out.printf("TIME: %.4f seconds\n", math);
            }
            catch (IOException e){
	       System.err.println("Error processing" + str);
            }
        }
    }

    static void construct(String str) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(str));
        Hashtable<String, edge> list = new Hashtable<>();
        LinkedList<String> box = new LinkedList<>();// holds the vertex with 0 inedges and out edges
        String lines = "";
        while ((lines = in.readLine()) != null) {
            String[] line = lines.split(" ");
            if(line.length==2){
            String adj = line[0];
            String vertex = line[1];
            map(vertex, adj, list);//adds the adj and vertex to the Hashtable
            }
        }
        for(String vertex: list.keySet()){
            if(list.get(vertex).incoming_adj.size()==0)//checks table for all keys that have an incmoming_adj list size of 0
                box.add(vertex);
        }
        LinkedList<String> print = new LinkedList<>();
        topsort(list, box, print);

    }

    static void topsort(Hashtable<String, edge> list, LinkedList<String> box, LinkedList<String> print) {
      //  HashMap<String, LinkedList<String>> outlist = new HashMap<>();//flip keys and value from in and out map.
        boolean loop = false;
        while ((!box.isEmpty() || !loop) && list.size() != 0) {
            if (box.isEmpty() && !loop) {//if box is empty then there is a loop. If statement only true once.
                loop = true;
                for (String edge : list.keySet()) { // runs >O(n) 
                    if (list.get(edge).outgoing_adj.size()==0) {//gets edges with no outgoing adj edges(edge-->out_adj)
                        box.add(edge);
                    }
                }
            }
            String temp = box.poll();
            if (!loop) {
                in_trim_map(temp, list, box);
            } else {
                out_trim_map(temp, list, box);
            }
            if (!loop) {
                print.add(temp);
            }
        }
        if (loop) {
            print.clear();
            printloop(list);
        } else {
            for (int i = 0; i < print.size(); i++) {
                if (i == print.size() - 1) {
                    System.out.print(print.get(i)+"\n");
                } else {
                    System.out.print(print.get(i) + ", ");
                }
            }
        }

    }
    //prints the loop
    static void printloop(Hashtable<String, edge> outlist) {
        System.out.print("graph has a cycle: ");
        Random rand = new Random();
        String temp = "";
        LinkedList<String> print = new LinkedList<>();
        for (String edge : outlist.keySet()) {
            temp = edge;
            break;
        }
        while (!print.contains(temp)) {
            print.add(temp);
            temp = outlist.get(temp).outgoing_adj.get(rand.nextInt(outlist.get(temp).outgoing_adj.size()));
        }
        print.add(temp);
        for (int i = print.indexOf(temp); i < print.size(); i++) {
            if (i == print.size() - 1) {
                System.out.print(print.get(i)+"\n");
            } else {
                System.out.print(print.get(i) + "->");
            }
        }

    }
    //trims the edge from the outgoing adj of other edges
    static void out_trim_map(String temp, Hashtable<String, edge> list, LinkedList<String> box) {
        for (String key : list.get(temp).incoming_adj) {//sparse graph incoming_adj list will run O(1)
                list.get(key).outgoing_adj.remove(temp);
                if (list.get(key).outgoing_adj.size() == 0) {
                    box.add(key);
                }
        }
        list.remove(temp);
    }
    //trims the edge from the incoming adj of other edges
    static void in_trim_map(String temp, Hashtable<String, edge> list, LinkedList<String> box) {
        for (String key : list.get(temp).outgoing_adj) {//sparse graph outgoing_adj list will run O(1)
                list.get(key).incoming_adj.remove(temp);
                if (list.get(key).incoming_adj.size() == 0) {
                    box.add(key);
                }
        }
        list.remove(temp);
    }
    
     static void map(String keyedge, String adj, Hashtable<String, edge> list) {
         
        if (list.get(keyedge) == null) {//keyedge is not in the key list yet
            list.put(keyedge, new edge(adj));//creates a key in the list from the keyedge and adds the adj to the incoming_adj
        } else if (list.get(keyedge) != null) {//key is already in the key list
            if (!list.get(keyedge).incoming_adj.contains(adj)) {//incoming adj has not been set by the key. Sparse graph runs O(1)
                list.get(keyedge).incoming_adj.add(adj);
            }
        }
        if (list.get(adj) == null) {//adj is not a key list
            list.put(adj, new edge(keyedge, keyedge));//creates a key in the list from the adj and adds the keyedge to the outgoing_adj
        } else if (list.get(adj) != null) {//adj is already in the key list
            if (!list.get(adj).outgoing_adj.contains(keyedge)) {//outgoing keyedge has not been set by the adj. Sparse graph run O(1)
                list.get(adj).outgoing_adj.add(keyedge);
            }
        }
    }
    
    static class edge{
        LinkedList<String> incoming_adj = new LinkedList<>();
        LinkedList<String> outgoing_adj = new LinkedList<>();
        
        edge(String e){//constructor for incoming_adj
            incoming_adj.add(e);
        }
        
        edge(String k, String e){//contructor for outgoing_adj
            outgoing_adj.add(e);
            
        }
    }
}
