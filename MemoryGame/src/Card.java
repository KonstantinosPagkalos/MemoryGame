import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
//Πάγκαλος Κωνσταντίνος 3212016139
//Νικόλας Κατής 3212016061

// Αντιπροσωπεύει μία κάρτα στο παιχνίδι
public class Card extends JLabel implements MouseListener {



    // Διαστάσεις κάρτας
    final int cardWidth = 72;
    final int cardHeight = 72;

    // Καθορίζει εάν φάινεται στο σχήμα στη κάρτα ή όχι
    boolean shapeVisible;

    // Καθορίζει εάν πρόκειται για κάρτα "Μπαλαντέρ"
    boolean joker;

    // Το Σχήμα στη κάρτα
    EnumShape shape;    // το σχήμα στη κάρτα
    Color color;        // το χρώμα γέμισης του σχήματος

    Board board;

    public Card(Board board,  EnumShape shape, Color color, boolean joker)
    {

        this.board = board;

        // Μπαλαντέρ
        this.joker = joker;

        // Aρχικά το σχήμα δεν φαίνεται
        this.shapeVisible = false;

        // Σχήμα
        this.shape = shape;
        // Χρώμα γέμισης σχήματος
        this.color = color;

        // ενεργοποίηση αντίληψης ενεργειών ποντικιού στη κάρτα
        this.addMouseListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2;
        g2 = (Graphics2D) g;

        Rectangle clipRect = g2.getClipBounds();
        g2.setColor(Color.red);
        g2.drawRect(0,0,clipRect.width, clipRect.height);

        if (this.shapeVisible) {
            if (this.shape== EnumShape.CIRCLE){
                CircleShape circle = new CircleShape(18, 18 );
                circle.drawShape(g, this.color);
            }
            else if (shape== EnumShape.RECTANGLE){
                RectangleShape rect = new RectangleShape(15, 30);
                rect.drawShape(g, this.color);
                //g2.fillRect(15,30,   65,   50);
            }
            else if (shape== EnumShape.RHOMBUS){

            }
            else if (shape== EnumShape.TRIANGLE){

            }
            else if (this.shape== EnumShape.SQUARE){
                SquareShape square = new SquareShape(18);
                square.drawShape(g, this.color);
            }
            this.shapeVisible = true;
        }
        else {
            g2.setColor(Color.ORANGE);
            g2.fillRect(0, 0, clipRect.width - 1, clipRect.height - 1);
            this.shapeVisible = false;
        }

    }

    /**
     * Try to turn face up
     */
    public void shapeShow()
    {
        // το σχήμα της κάρτας είναι ήδη ορατό
        if (this.shapeVisible) {
            return;
        }

        board.addSelectedCard(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        if ((!(shape == null)) && (!(color==null))) {
            return shape == card.shape &&
                    color.equals(card.color);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape, color);
    }

    // Ποντίκι πάνω από κάρτα
    private boolean overCard(int x, int y)
    {
        // Υπολογισμός απόστασης από το κέντρο της κάρτας
        int distX = Math.abs(x - (this.getWidth() / 2));
        int distY = Math.abs(y - (this.getHeight() / 2));
        // Εκτός
        if(distX > this.cardWidth || distY > this.cardHeight){
            return false;
        }
        // Εντός
        return true;
    }

    // Εμφάνιση σχήματος κάρτας
    public boolean isShapeVisible() {
        return shapeVisible;
    }

    // Οριμσός εμφάνισης ή μη σχήματος κάρτας
    public void setShapeVisible(boolean shapeVisible) {
        this.shapeVisible = shapeVisible;
        repaint();
    }

    // Πρότειται περί μπαλαντέρ;
    public boolean isJoker() {
        return joker;
    }

    public void setJoker(boolean joker) {
        this.joker = joker;
    }


    // Μέθοδοι για MouseListener
    public void mouseClicked(MouseEvent e)
    {

    }

    public void mousePressed(MouseEvent e)
    {
        boolean isOverCard = overCard(e.getX(), e.getY());
        if (isOverCard) {
            this.shapeShow();
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        boolean isOverCard = overCard(e.getX(), e.getY());
        if (isOverCard) {
            this.board.checkSelectedCards();
        }
    }

   public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    @Override
    public String toString() {
        return "Card{" +
                "shapeVisible=" + shapeVisible +
                ", shape=" + shape +
                ", color=" + color +
                '}';
    }
}
