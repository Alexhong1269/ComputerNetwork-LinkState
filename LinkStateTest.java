import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class LinkStateTest {
    static int Vertex = 0;

    void dijkstra(int graph[][], int src) {
        int dist[] = new int[Vertex];
        int nextHop[] = new int[Vertex]; // Array to store next hop information

        Boolean dijSet[] = new Boolean[Vertex];

        for (int i = 0; i < Vertex; i++) {
            dist[i] = Integer.MAX_VALUE;
            dijSet[i] = false;
            nextHop[i] = -1;
        }

        dist[src] = 0;

        for (int i = 0; i < Vertex - 1; i++) {
            int firstPick = minDistance(dist, dijSet, nextHop, graph);

            dijSet[firstPick] = true;

            for (int j = 0; j < Vertex; j++) {
                if (!dijSet[j] && graph[firstPick][j] != 0
                        && dist[firstPick] != Integer.MAX_VALUE
                        && dist[firstPick] + graph[firstPick][j] < dist[j]) {
                    dist[j] = dist[firstPick] + graph[firstPick][j];
                    nextHop[j] = firstPick; // Update next hop information
                }
            }
        }
        printSol(dist, nextHop);
    }

    int minDistance(int dist[], Boolean dijSet[], int nextHop[], int graph[][]) {
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int i = 0; i < Vertex; i++) {
            if (!dijSet[i] && dist[i] <= min) {
                min = dist[i];
                min_index = i;
            }
        }
        
        if(min_index != -1){
            for (int i = 0; i < Vertex; i++) {
                if (i != min_index && graph[min_index][i] != 0 && dist[min_index] + graph[min_index][i] == dist[i]) {
                    nextHop[i] = min_index;
                }
            }
        }
        return min_index;
    }

    void printSol(int dist[], int nextHop[]) {
        System.out.println("Vertex \t\t Next Hop \t\t Distance from Source ");

        for (int i = 0; i < Vertex; i++) {
            System.out.println(i + " \t\t " + nextHop[i] + "\t\t\t\t " + dist[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(new FileReader("topofile.txt"))) {
            int numVertices = scanner.nextInt();

            int graph[][] = new int[Vertex][Vertex];

            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int source = scanner.nextInt() - 1;
                    if (scanner.hasNextInt()) {
                        int destination = scanner.nextInt() - 1;
                        if (scanner.hasNextInt()) {
                            int weight = scanner.nextInt();

                            int maxVertex = Math.max(source, destination) + 1;

                            if (maxVertex > Vertex) {
                                Vertex = maxVertex;

                                int newGraph[][] = new int[Vertex][Vertex];

                                for (int i = 0; i < graph.length; i++) {
                                    System.arraycopy(graph[i], 0, newGraph[i], 0, graph[i].length);
                                }
                                graph = newGraph;
                            }
                            if (source >= 0 && source < Vertex && destination >= 0 && destination < Vertex) {
                                graph[source][destination] = weight;
                                graph[destination][source] = weight;
                            } else {
                                System.out.println("Error: Indices out of bounds.");
                            }
                        } else {
                            System.out.println("Error: Missing weight value.");
                        }
                    } else {
                        System.out.println("Error: Missing destination value.");
                    }
                } else {
                    scanner.next();
                }
            }

            LinkStateTest linkStateTest = new LinkStateTest();
            linkStateTest.dijkstra(graph, 0);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
    }
}


