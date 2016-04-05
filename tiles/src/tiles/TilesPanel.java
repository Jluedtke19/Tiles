package tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;

public class TilesPanel extends JPanel implements ActionListener {

    private static final Color BG_COLOR = new Color(248, 216, 180);
    private static final Color EDGE_COLOR = new Color(128, 192, 164);
    private static final Color VERTEX_COLOR = new Color(44, 56, 144);
    private static final Color SELECTED_VERTEX_COLOR = new Color( 248, 112, 122 );
    private static final double RADIUS = 0.02;
    private static final double SELECTED_RADIUS = 2 * RADIUS;
    private final Graph graph;
    private final List<Vertex> listOfVertices;
    private Vertex origin = null;
    private Vertex destination = null;
    private GeneralPath shortestPath = null;

    public TilesPanel() {
        this.setBackground(BG_COLOR);
        this.graph = new Graph(12);
        this.listOfVertices = new ArrayList<>(this.graph.getVertices());
    } // TilesPanel()

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        int w = this.getWidth();
        int h = this.getHeight();

        AffineTransform scale = new AffineTransform();
        scale.setToScale(w / 2, h / 2);

        AffineTransform translate = new AffineTransform();
        translate.setToTranslation(1.0, 1.0);

        AffineTransform transform = new AffineTransform();
        transform.concatenate(scale);
        transform.concatenate(translate);

        Stroke stroke = new BasicStroke(2.0F);
        g2D.setStroke(stroke);
        g2D.setColor(EDGE_COLOR);
        for (Edge e : this.graph.getEdges()) {
            Shape lineSegment = transform.createTransformedShape(e.getLineSegment());
            g2D.draw(lineSegment);
        } // for

        for (Vertex v : this.graph.getVertices()) {
            double r = RADIUS;
            if( v.isSelected() ) {
                g2D.setColor( SELECTED_VERTEX_COLOR );
                r = SELECTED_RADIUS;
            } // if
            else {
                g2D.setColor( VERTEX_COLOR );
            } // else
            double diameter = 2 * r;
            double ulx = v.getX() - r;
            double uly = v.getY() - r;
            Ellipse2D circle
                    = new Ellipse2D.Double(ulx, uly, diameter, diameter);
            g2D.fill(transform.createTransformedShape(circle));
        } // for
        
        if( this.shortestPath != null ) {
            g2D.setColor( Color.RED);
            g2D.draw(transform.createTransformedShape(shortestPath));
        }
    } // paintComponent( Graphics )

    @Override
    public void actionPerformed(ActionEvent e) {
       this.listOfVertices.get(0).clearSelection();
       this.listOfVertices.get(1).clearSelection();
       Collections.shuffle( this.listOfVertices );
       this.listOfVertices.get(0).setSelection();
       this.listOfVertices.get(1).setSelection();
       
       this.origin = this.listOfVertices.get(0);
       this.destination = this.listOfVertices.get(1);
       this.shortestPath = this.graph.getShortestPath(origin, destination);
       
       this.repaint();
    } // actionPerformed( ActionEvent )

} // TilesPanel
