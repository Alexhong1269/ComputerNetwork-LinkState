// A Java program for Dijkstra's single source shortest path
// algorithm. The program is for adjacency matrix
// representation of the graph
import java.io.*;
import java.lang.*;
import java.util.*;
 
class ShortestPath {
    // A utility function to find the vertex with minimum
    // distance value, from the set of vertices not yet
    // included in shortest path tree
    static final int V = 5;
    int[][] graph;
    int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;
 
        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
 
        return min_index;
    }
 
    // A utility function to print the constructed distance
    // array
    void printSolution(int dist[])
    {
        System.out.println(
            "Vertex \t\t Distance from Source");
        for (int i = 1; i < V; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }
 
    // Function that implements Dijkstra's single source
    // shortest path algorithm for a graph represented using
    // adjacency matrix representation
    void dijkstra(int weight, int src, int dest)
    {
        int dist[] = new int[V]; 
        Boolean sptSet[] = new Boolean[V];

        for (int i = 0; i < V; i++) {
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;
        
        
        // Find shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum distance vertex from the set
            // of vertices not yet processed. u is always
            // equal to src in first iteration.
            int u = minDistance(dist, sptSet);
 
            // Mark the picked vertex as processed
            sptSet[u] = true;
 
            // Update dist value of the adjacent vertices of
            // the picked vertex.
            for (int v = 0; v < V; v++)
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
        }
 
        // print the constructed distance array
        printSolution(dist);
    }
 
    // Driver's code
    public static void main(String[] args){
        ShortestPath shortestPath = new ShortestPath();
        shortestPath.graph = readGraphFromFile("topofile.txt");

        // Read source, destination, and weight from the user or any source
        int src = 1; // Replace with the actual source vertex
        int dest = 2; // Replace with the actual destination vertex
        int weight = 8; // Replace with the actual weight

        shortestPath.dijkstra(src, dest, weight);

    }

    static int[][] readGraphFromFile(String fileName) {
        int[][] graph = new int[V][V];
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    int src = Integer.parseInt(parts[0]);
                    int dest = Integer.parseInt(parts[1]);
                    int weight = Integer.parseInt(parts[2]);
                    graph[src - 1][dest - 1] = weight;  // Adjust indices by subtracting 1
                    graph[dest - 1][src - 1] = weight;  // Assuming an undirected graph
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File not found. Make sure the file path is correct.");
        }
        return graph;
    }
}
