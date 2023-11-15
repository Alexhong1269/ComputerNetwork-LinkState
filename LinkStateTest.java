import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

//the Link state program is basically dijkstras algo
class LinkStateTest{
    //starting value to find the vertix with min value
    static final int Vertex = 5;
    void dijikstra(int graph[][], int src){
        //distance array to hold the shortest path from source to i
        int dist[] = new int[Vertex];

        //a set to hold true or false value to see if the vertex is in shortest pathing
        Boolean dijSet[] = new Boolean[Vertex];

        //beinging and setting all distance to infinite and pathing value as false
        for(int i = 0; i < Vertex; i++){
            //setting the distance to infinte
            dist[i] = Integer.MAX_VALUE;
            //setting the pathing to false(not apart of shortest path)
            dijSet[i] = false;
        }

        //distance from source to itself is 0
        dist[src] = 0;

        //finding the shortest path(not including itself thats why its -1)
        for(int i = 1; i < Vertex; i++){
            //picking the min distance between adjacent vertices from the source
            int firstPick = minDistance(dist, dijSet);

            //marking the picked vertex as true(shortest path)
            dijSet[firstPick] = true;

            //updating the adjacent values of the vertices
            for(int j = 0; j < Vertex; j ++){
                //check the weighted value of the edges for the shortest path and to check if there is even an edge to a vertex
                if(!dijSet[j] && graph[firstPick][j] != 0
                    && dist[firstPick] != Integer.MAX_VALUE
                    && dist[firstPick] + graph[firstPick][j] < dist[j]){
                        dist[j] = dist[firstPick] + graph[firstPick][j];
                }
            }

        }
        printSol(dist);
    }

    int minDistance(int dist[], Boolean dijSet[]){
        //setting the min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for(int i = 0; i < Vertex; i++){
            //check to see if the pathing is true and if the current distance is less than the min
            if(dijSet[i] == false && dist[i] <= min){
                //updating the min distance
                min = dist[i];
                min_index = i;
            }
        }
        return min_index;
    }

    void printSol(int dist[]){
        //printing the headers
        System.out.println("Vertex \t\t Distance from Source");

        //printing the traversal number and the distance
        for(int i = 0; i < Vertex; i++){
            System.out.println(i + " \t\t " + dist[i]);
        }
    }
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(new FileReader("topofile.txt"))) {
            int numVertices = scanner.nextInt();
            // int numVertices = Integer.parseInt(bufferedReader.readLine().split("\\s+")[0]);

            int graph[][] = new int[Vertex][Vertex];

            // Initialize the graph with zeros
            for (int i = 0; i < Vertex; i++) {
                for (int j = 0; j < Vertex; j++) {
                    graph[i][j] = 0;
                }
            }

            String line;
            while (scanner.hasNextInt()) {
                int source = scanner.nextInt() - 1; // Adjusting for 0-based indexing
                int destination = scanner.nextInt() - 1;
                int weight = scanner.nextInt();

                System.out.println("Source: " + source + ", Destination: " + destination + ", Weight: " + weight);


                // Assuming the graph is undirected, update both directions
                // graph[source][destination] = weight;
                graph[destination][source] = weight;
            }

            // Now, you have the graph ready. Let's find the shortest path.
            LinkStateTest linkStateTest = new LinkStateTest();
            linkStateTest.dijikstra(graph, 0); // Assuming you want to find the shortest path starting from vertex 0
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
    }
}

