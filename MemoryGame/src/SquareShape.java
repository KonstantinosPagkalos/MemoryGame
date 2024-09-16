import java.awt.*;
import java.awt.Rectangle;
//Πάγκαλος Κωνσταντίνος 3212016139
//Νικόλας Κατής 3212016061
public class SquareShape {
    int x;

    public SquareShape(int x ) {
        this.x = x;
    }

    public void drawShape(Graphics g, Color color) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle clipRect = g2d.getClipBounds();

        g2d.setColor(Color.ORANGE);
        g2d.fillRect(0,0,clipRect.width-1, clipRect.height-1);

        g2d.setColor(color);
        g2d.fillRect(clipRect.width/4,clipRect.height/4,clipRect.width/2, clipRect.height/2);

    }
}
