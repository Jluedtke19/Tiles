package tiles;

import java.awt.geom.GeneralPath;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graph {

    private static final double WIDTH = 1.98;
    private static final double HEIGHT = 1.98;

    private final Vertex[][] nodes;
    private final Set<Vertex> vertices = new TreeSet<>();
    private final Set<Edge> edges = new TreeSet<>();
    private final Map<Vertex, Set<Vertex>> adjacencyList = new TreeMap<>();

    public Graph(int n) {
        // Build array of prospective vertices.
        this.nodes = new Vertex[n][n];

        double radius = 2.0 / n;
        double deltaX = 2.0 * radius * Math.sin(Math.PI / 3.0);
        double deltaY = radius + radius * Math.cos(Math.PI / 3.0);

        double x = -1.0;
        double y = -1.0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nodes[i][j] = new Vertex(x, y);
                x += deltaX;
            } // for
            if (i % 2 == 0) {
                x = -1.0 + deltaX / 2;
            } // if
            else {
                x = -1.0;
            } // else
            y += deltaY;
        } // for

        // Build list of edges and vertices.
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                Vertex a = this.nodes[i][j];
                Vertex b = this.nodes[i][j + 1];
                Vertex c = this.nodes[i + 1][j];

                if (i % 2 == 0) {
                    this.addEdge(b, c);
                } // if
                else {
                    Vertex d = this.nodes[i + 1][j + 1];
                    this.addEdge(a, d);
                } // else

                this.addEdge(a, b);
                this.addEdge(a, c);
            } // for
        } // for

        // Build adjacency list.
        for (Vertex v : this.vertices) {
            adjacencyList.put(v, v.getNeighbors());
        } // for
    } // Graph( double )

    private boolean inBounds(Vertex v) {
        double x = v.getX();
        double y = v.getY();
        return Math.abs(x) < WIDTH / 2 && Math.abs(y) < HEIGHT / 2;
    } // inBounds()

    private void addEdge(Vertex head, Vertex tail) {
        if (this.inBounds(head) && this.inBounds(tail)) {
            this.edges.add(new Edge(head, tail));
            this.vertices.add(head);
            this.vertices.add(tail);
        } // if    
    } // addEdge( Vertex, Vertex )

    public Set<Vertex> getVertices() {
        return this.vertices;
    } // getVertices()

    public Set<Edge> getEdges() {
        return this.edges;
    } // getEdges()

    public Map<Vertex, Set<Vertex>> getAdjacencyList() {
        return this.adjacencyList;
    } // getAdjacencyList()

    public GeneralPath getShortestPath(Vertex origin, Vertex destination) {
        Map<Vertex, Vertex> previousVertex = new TreeMap<>();
        Map<Vertex, Integer> distanceToVertex = new TreeMap<>();
        Queue<Vertex> queue = new LinkedList<>();

        queue.add(origin);
        distanceToVertex.put(origin, 0);

        while (!queue.isEmpty()) {
            Vertex u = queue.remove();
            for (Vertex v : u.getNeighbors()) {
                if (!distanceToVertex.containsKey(v)) {
                    queue.add(v);
                    distanceToVertex.put(v, 1 + distanceToVertex.get(u));
                    previousVertex.put(v, u);
                } // if       
            } // for
        } // while

        GeneralPath path = new GeneralPath();
        Vertex v = destination;
        path.moveTo( v.getX(), v.getY());
        v = previousVertex.get(v);
        
        while (v != null && distanceToVertex.containsKey(v)) {
            path.lineTo(v.getX(), v.getY());
            v = previousVertex.get(v);
        } // while
        
        return path;
    } // getShortestPath( Vertex, Vertex )
} // Graph
