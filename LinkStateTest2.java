import java.io.*;
import java.util.*;

class Graph {
    class Edge {
        int dest, weight;
        Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    int V;
    public ArrayList<ArrayList<Edge>> adjacencyList;

    public Graph(int v) {
        V = v;
        adjacencyList = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    void addEdge(int src, int dest, int weight) {
        adjacencyList.get(src).add(new Edge(dest, weight));
    }

    void dijkstra(int src, int[] id, PrintWriter writer) {
        int[] nexthop = new int[V + 1];
        int[] dist = new int[V + 1];
        boolean[] dijSet = new boolean[V + 1];

        for (int i = 1; i <= V; i++) {
            dist[i] = Integer.MAX_VALUE;
            dijSet[i] = false;
        }

        dist[src] = 0;

        for (int count = 1; count <= V; count++) {
            int u = minDistance(dist, dijSet);
            dijSet[u] = true;

            for (Edge neighbor : adjacencyList.get(u - 1)) {
                int v = neighbor.dest;
                int weight = neighbor.weight;
                if (!dijSet[v] && dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    nexthop[v] = u;
                }
            }
        }
        print(dist, id, nexthop, writer);
    }

    int minDistance(int dist[], boolean dijSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int i = 1; i <= V; i++) {
            if (!dijSet[i] && dist[i] <= min) {
                min = dist[i];
                min_index = i;
            }
        }
        return min_index;
    }

    void print(int dist[], int id[], int nexthop[], PrintWriter writer) {
        writer.println("Source Node\tDestination Node\tDistance\tNext Hop");
        for (int i = 1; i <= V; i++) {
            writer.print(id[i - 1] + "\t\t\t" + (dist[id[i - 1]] == Integer.MAX_VALUE ? "INF" : dist[id[i - 1]]));
            writer.println("\t\t\t" + (nexthop[id[i - 1]] == 0 ? "INF" : id[nexthop[id[i - 1]] - 1]));
        }
    }

    public static void main(String[] args) {
        Set<Integer> Vertices = new HashSet<>();
        String topo = "topofile.txt";
        String change = "change.txt";

        try {
            Scanner scanner = new Scanner(new File(topo));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    int src = Integer.parseInt(parts[0]);
                    int dest = Integer.parseInt(parts[1]);
                    Vertices.add(src);
                    Vertices.add(dest);
                    int weight = Integer.parseInt(parts[2]);
                }
            }

            int numV = Vertices.size();
            int[] id = Vertices.stream().mapToInt(Integer::intValue).toArray();

            Graph graph = new Graph(numV);

            Scanner scanner2 = new Scanner(new File(topo));

            while (scanner2.hasNextLine()) {
                String line = scanner2.nextLine();
                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    int src = Integer.parseInt(parts[0]);
                    int dest = Integer.parseInt(parts[1]);
                    Vertices.add(src);
                    Vertices.add(dest);
                    int weight = Integer.parseInt(parts[2]);
                    graph.addEdge(src, dest, weight);
                }
            }

            // Use PrintWriter to write to a text file
            PrintWriter writer = new PrintWriter(new FileWriter("output.txt"));

            for (int i = 1; i <= numV; i++) {
                writer.println("Source Node: " + i + "   Distance from Source");
                graph.dijkstra(i, id, writer);
            }

            // Close the writer to ensure the changes are saved
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File not found. Make sure the file path is correct.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to the file.");
        }
    }
}
