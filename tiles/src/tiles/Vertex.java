package tiles;

import java.util.Set;
import java.util.TreeSet;

public class Vertex implements Comparable<Vertex> {

    private static int numberOfVertices = 0;

    private final double x;
    private final double y;
    private final int id;
    private final Set<Vertex> neighbors;
    private boolean selected;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
        this.id = Vertex.numberOfVertices++;
        this.neighbors = new TreeSet<>();
        this.selected = false;
    } // Vertex( double, double )

    public double getX() {
        return this.x;
    } // getX()

    public double getY() {
        return this.y;
    } // getY()

    public double getId() {
        return this.id;
    } // getId()

    public boolean isSelected() {
        return this.selected;
    } // isSelected()
    
    public void clearSelection() {
        this.selected = false;
    } // clearSelection()
    
    public void setSelection() {
        this.selected = true;
    } // setSelection()
    
    public Set<Vertex> getNeighbors() {
        return this.neighbors;
    } // getNeighbors()

    public final void addNeighbor(Vertex v) {
        this.neighbors.add(v);
    } // addNeighbor( Vertex )

    public final int getDegree() {
        return this.neighbors.size();
    } // getDegree()

    @Override
    public int compareTo(Vertex v) {
        if (this.id < v.id) {
            return -1;
        } // if
        else if (this.id > v.id) {
            return +1;
        } // else if
        else {
            return 0;
        } // else
    } // compareTo( Vertex )
} // Vertex
