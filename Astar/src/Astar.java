import javax.xml.crypto.NodeSetData;
import java.util.*;

class Node {
    int x;
    int y;
    double h;
    int g;
    double f;
    char c;
    Node previous;

    public Node(char c, int x, int y) {
        this(c, x, y, 0, 0, 0);
    }

    public Node(char c, int x, int y, int h, int g, int f) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.h = h;
        this.g = g;
        this.f = f;
    }

    public void set_f() {
        this.f =(double) (this.g + this.h);
    }

    public void set_g(int weight) {
        this.g = previous.g + weight;
    }

    public void set_h(int x, int y) {
        h = (Math.abs(this.x - x) * Math.abs(this.x - x) ) + (Math.abs(this.y - y) * Math.abs(this.y - y));
        h =  Math.sqrt(h);
    }

    public int get_g() {
        return g;
    }

}


class Graph {
    private int v;
    public ArrayList<Integer>[] adjList;
    public ArrayList<Integer>[] weightOfEdges;
    ArrayList<Node> openList;
    ArrayList<Node> closeList;
    ArrayList<Node> path;
    Node[] nodes;

    public Graph(int v) {
        this.v = v;
        openList = new ArrayList<>();
        closeList = new ArrayList<>();
        path = new ArrayList<>();
        adjList = new ArrayList[v];
        weightOfEdges = new ArrayList[v];
        for (int i = 0; i < v; i++) {
            adjList[i] = new ArrayList<Integer>();
            weightOfEdges[i] = new ArrayList<Integer>();
        }
    }

    public void addEdge(int x, int y, int weight) {
        adjList[x].add(y);
        weightOfEdges[x].add(weight);
        adjList[y].add(x);
        weightOfEdges[y].add(weight);
    }

    public ArrayList<Node> a_star(char start , char end) {
        double min ;
        Node current, target;
        int indexOfCurrent, indexOfTarget,counter;
        openList.add(nodes[Astar.charToNumber(start)]);
        current = nodes[0];
        target = nodes[Astar.charToNumber(end)];
        indexOfTarget = Astar.charToNumber(end);
        while (openList.size() > 0) {
            min=Integer.MAX_VALUE;
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).f <= min) {
                    min = openList.get(i).f;
                    current = openList.get(i);
                }
            }
            indexOfCurrent = Astar.charToNumber(current.c);
            closeList.add(current);
            openList.remove(current);
            if (closeList.contains(nodes[indexOfTarget])) {
                while (target != null) {
                    path.add(target);
                    target = target.previous;
                }
                return path;
            }
            counter=0;
            for (int index : adjList[indexOfCurrent]) {

                if (closeList.contains(nodes[index])){
                    counter++;
                    continue;
                }

                if (!(openList.contains(nodes[index]))) {
                    nodes[index].previous = current;
                    nodes[index].set_h(nodes[indexOfTarget].x, nodes[indexOfTarget].y);
                    nodes[index].set_g(weightOfEdges[indexOfCurrent].get(counter));
                    nodes[index].set_f();
                    openList.add(nodes[index]);
                } else {
                    int new_g = weightOfEdges[indexOfCurrent].get(counter) + current.get_g();
                    if (new_g + nodes[index].h < nodes[index].f) {
                        nodes[index].g = new_g;
                        nodes[index].set_f();
                        nodes[index].previous = current;
                    }

                }
                counter++;
            }
        }
        return path;
    }

}





public class Astar {
    public static int charToNumber(char ch) {
        return (int) ch - 65;
    }

    public static char numberTOChar(int n) {
        return (char) (n+65);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int m = input.nextInt();
        Graph graph = new Graph(n);
        Node[] nodes = new Node[n];
        ArrayList<Node> path ;
        for (int i = 0; i < n; i++) {
            char ch = input.next().charAt(0);
            int x = input.nextInt();
            int y = input.nextInt();
            nodes[i] = new Node(ch, x, y);
        }
        for (int i = 0; i < m; i++) {
            char x = input.next().charAt(0);
            char y = input.next().charAt(0);
            int weight = input.nextInt();
            graph.addEdge(charToNumber(x), charToNumber(y), weight);
        }
        graph.nodes = nodes;
        path=graph.a_star('A','D');
        for (int i =path.size()-1; i >=0 ; i--) {
            System.out.print(path.get(i).c);
            System.out.print("  ");
        }

    }
}
