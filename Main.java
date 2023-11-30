import java.io.*;
import java.util.*;

class GraphAlgorithm {
    class Edge {
        int src, dest, weight;

        Edge() {
            src = dest = weight = 0;
        }
    }

    int V, E;
    public ArrayList<Edge> edges;

    GraphAlgorithm(int v) {
        V = v;
        E = 0;
        edges = new ArrayList<Edge>();
    }

    void addEdge(int id, int dest, int weight) {
        edges.add(new Edge());
        edges.get(E).src = id;
        edges.get(E).dest = dest;
        edges.get(E).weight = weight;
        E++;
    }

    void BellmanFord(GraphAlgorithm graph, int src, int[] q) {
        int[] id = q;
        int V = graph.V, E = graph.E;
        int[] nexthop = new int[V + 1];
        int dist[] = new int[V + 1];

        for (int i = 0; i <= V; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0;
        for (int i = 1; i <= dist.length; i++) {
            for (int j = 0; j < E; j++) {
                int u = edges.get(j).src;
                int v = edges.get(j).dest;
                int weight = edges.get(j).weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    nexthop[v] = u;
                } else if (dist[v] != Integer.MAX_VALUE && dist[v] + weight < dist[u]) {
                    dist[u] = dist[v] + weight;
                    nexthop[u] = v;
                }
            }
        }
        print(dist, id, nexthop);
    }

    void print(int dist[], int id[], int nexthop[]) {
        try (PrintStream out = new PrintStream(new FileOutputStream("output.txt"))) {
            System.setOut(out);

            for (int i = 0; i < dist.length - 1; i++) {
                System.out.println(id[i] + "\t\t\t" + dist[id[i]]);
            }
            System.out.println("Output file: ");
            for (int i = 0; i < dist.length - 1; i++) {
                System.out.println(id[i] + "\t\t" + (nexthop[id[i]] == 0 ? id[i] : nexthop[i + 1]) + "\t\t" + dist[id[i]]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error writing to the output file.");
        }
    }

    void printChange(int dist[]) {
        try (PrintStream out = new PrintStream(new FileOutputStream("output.txt", true))) {
            System.setOut(out);

            int counter = 0;
            counter++;
            System.out.println("===================================");
            System.out.println("Change " + counter + ": ");
            System.out.println("Node    Distance from Source");
            for (int i = 0; i < dist.length - 1; i++)
                System.out.println(i + 1 + "\t\t" + dist[i + 1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error writing to the output file.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Set<Integer> Vertices = new HashSet<>();
        String topo = "topofile.txt";

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

            GraphAlgorithm graph = new GraphAlgorithm(numV);

            Scanner scanner2 = new Scanner(new File(topo));

            while (scanner2.hasNextLine()) {
                String line = scanner2.nextLine();
                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    int src = Integer.parseInt(parts[0]);
                    int dest = Integer.parseInt(parts[1]);
                    int weight = Integer.parseInt(parts[2]);
                    graph.addEdge(src, dest, weight);
                }
            }


            for (int i = 1; i <= numV; i++) {
                System.out.println("Source Node: " + i + "   Distance from Source");
                graph.BellmanFord(graph, i, id);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File not found. Make sure the file path is correct.");
        }
    }
}
