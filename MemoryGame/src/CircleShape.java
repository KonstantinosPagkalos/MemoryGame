import java.awt.*;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
//Πάγκαλος Κωνσταντίνος 3212016139
//Νικόλας Κατής 3212016061
public class CircleShape {
    int x, y;

    public CircleShape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawShape(Graphics g, Color color) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle clipRect = g2d.getClipBounds();

        g2d.setColor(Color.ORANGE);
        g2d.fillRect(0,0,clipRect.width-1, clipRect.height-1);

        g2d.setColor(color);
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, clipRect.width/2, clipRect.height/2);
        g2d.fill(circle);
    }
}