import java.awt.*;
import java.awt.geom.Ellipse2D;
//Πάγκαλος Κωνσταντίνος 3212016139
//Νικόλας Κατής 3212016061
public class RectangleShape {

    int x, y;

    public RectangleShape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawShape(Graphics g, Color color) {
        Graphics2D g2d = (Graphics2D) g;
        java.awt.Rectangle clipRect = g2d.getClipBounds();

        g2d.setColor(Color.ORANGE);
        g2d.fillRect(0,0,clipRect.width-1, clipRect.height-1);

        g2d.setColor(color);
        g2d.fillRect(clipRect.width/4,clipRect.height/4,clipRect.width/2, clipRect.height/2);
    }
}
