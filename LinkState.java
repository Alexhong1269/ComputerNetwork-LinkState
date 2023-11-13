import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//the Link state program is basically dijkstras algo
class LinkState{
    //starting value to find the vertix with min value
    static final int Vertex = 9;
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
        for(int i = 0; i < Vertex - 1; i++){
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
        int min = Integer.MIN_VALUE, min_index = -1;

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
    public static void main(String[] args) throws IOException{
        int ch;
        
        try (FileReader fileReader = new FileReader("topofile.txt")) {
            // Reading and printing the content of the file
            while ((ch = fileReader.read()) != -1) {
                System.out.print((char) ch);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch(IOException e) {
            System.out.println("An error occurred while reading the file.");
        }        
    }
}
