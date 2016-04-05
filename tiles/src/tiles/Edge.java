package tiles;

import java.awt.geom.Line2D;

public class Edge implements Comparable<Edge> {

    private static int numberOfEdges = 0;

    private final Vertex head;
    private final Vertex tail;
    private final int id;

    public Edge(Vertex head, Vertex tail) {
        this.head = head;
        this.tail = tail;
        this.id = Edge.numberOfEdges++;
        this.head.addNeighbor(tail);
        this.tail.addNeighbor(head);
    } // Edge( Vertex, Vertex )

    public int getId() {
        return this.id;
    } // getId()

    public Line2D getLineSegment() {
        double x0 = this.head.getX();
        double y0 = this.head.getY();
        double x1 = this.tail.getX();
        double y1 = this.tail.getY();

        return new Line2D.Double(x0, y0, x1, y1);
    } // getLineSegment()

    @Override
    public int compareTo(Edge e) {
        if (this.id < e.id) {
            return -1;
        } // if
        else if (this.id > e.id) {
            return +1;
        } // else if
        else {
            return 0;
        } // else
    } // compareTo( Edge )
} // Edge
