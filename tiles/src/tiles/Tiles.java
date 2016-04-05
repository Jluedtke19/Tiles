package tiles;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Tiles extends JFrame {

    private static final int TILES_WIDTH = 512;
    private static final int TILES_HEIGHT = 512;
    private static final String TILES_TITLE = "Tiles";

    public Tiles() {
        this.setSize(TILES_WIDTH, TILES_HEIGHT);
        this.setTitle(TILES_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Container pane = this.getContentPane();
        TilesPanel panel = new TilesPanel();
        Timer timer = new Timer( 2000, panel );
        timer.start();
        pane.add( panel );
        
        this.setVisible(true);
    } // Tiles()

    public static void main(String[] args) {
        Tiles tiles = new Tiles();
    } // main( String [] )

} // Tiles
