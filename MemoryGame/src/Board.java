import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
//Πάγκαλος Κωνσταντίνος 3212016139
//Νικόλας Κατής 3212016061
public class Board implements ActionListener {

    // Πλέγμα γραμμών και στηλών
    int rows;
    int cols;

    // Όνομα παίκτη
    String playerName;

    // Αριθμός καρτών στο παιχνίδι
    int cardsOnBoard;
    // Αριθμός ζευγών καρτών στο παιχνίδι
    int cardsPairOnBoard ;
    // Αριθμός Μπαλαντέρ στο παιχνίδι
    int jokerOnBoard;

    final int colorsOnCard = 5;
    final int shapesOnCard = 3;

    // Όλες οι κάρτες του παιχνιδιού
    ArrayList<Card> cards;

    // Κάρτες που έχει βρεί ο παίκτης και εμφανίζουν το σχήμα τους
    ArrayList<Card> cardsShow;

    // Ζεύγος καρτών επιλογής παίκτη προς έλεγχο
    ArrayList<Card> cardsCheck;

    // Μέγιστος αριθμός προσπαθειών παίκτη
    int maxFailCount;
    // Μετρητής αποτυχιών ζεύγους καρτών
    int failCounter;

    // data fields
    private JFrame mainFrame;					// top level window
    private Container mainContentPane;			// frame that holds card field and turn counter

    public Board( ) {

        // Δημιουργία παραθύρων
        this.mainFrame = new JFrame("Παιχνίδι μνήμης - Κώστας ΠάγκαλοςΚατής Νικόλας");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(400,500);
        this.mainContentPane = this.mainFrame.getContentPane();
        this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS));

        // Γραμμή μενού
        JMenuBar menuBar = new JMenuBar();
        this.mainFrame.setJMenuBar(menuBar);

        // Μενού παιχνιδιών
        JMenu gameMenu = new JMenu("Παιχνίδι");
        menuBar.add(gameMenu);
        newMenuItem("Έναρξη νέου παχνιδιού", gameMenu, this);
        newMenuItem("Ακύρωση/Τερματισμός νέου παχνιδιού", gameMenu, this);
        newMenuItem("Έισαγωγή στοιχείων χρήστη", gameMenu, this);
        newMenuItem("Επίπεδο δυσκολίας", gameMenu, this);
        newMenuItem("Εμφάνιση ρεκόρ προηγούμενων παικτών", gameMenu, this);
        newMenuItem("Έξοδος από το παιχνίδι", gameMenu, this);

        // Μενού οδηγιών
        JMenu helpMenu = new JMenu("Οδηγίες");
        menuBar.add(helpMenu);
        newMenuItem("Οδηγίες παιχνιδιού", helpMenu, this);
        newMenuItem("Ταυτότητα", helpMenu, this);

        // Εμφάνιση παραθύρου
        this.mainFrame.setVisible(true);
    }

    // Προσθήκη μενού με τη συνοδευτική ενέργεια και ακροατή, προσθήκη στο κύριο μενού
    private static void newMenuItem(String text, JMenu menu, ActionListener listener)
    {
        JMenuItem newItem = new JMenuItem(text);
        newItem.setActionCommand(text);
        newItem.addActionListener(listener);
        menu.add(newItem);
    }

    // Οδηγίες παιχνιδιού
    private void showInstructions()
    {
        final String HOWTOPLAYTEXT =
                "Οδηγίες για το παιχνίδι\r\n" +
                        "\r\n" +
                        "Το παιχνίδι περιέχει ζεύγη ομοίων καρτών.  Με την έναρξη του παιχνιδιού,\r\n"+
                        "όλες οι κάρτες είναι κρυφές.  Σκοπός του παιχνιδιού είναι να βρεθούν τα ζεύγη καρτών που είναι ίδια\r\n"+
                        "και να εμφανιστούν τα σχήματα που περιέχουν.\r\n"+
                        "\r\n"+
                        "Κάνοντας κλικ σε δύο κάρτες, εμφανίζεται το σχήμα που περιέχουν.\r\n"+
                        "Εάν οι κάρτες είναι ίδιες, εξακολουθούν και παραμένουν ορατές, διαφορετικά αποκρύπτονται \r\n"+
                        "μετά από μία μικρή χρονική υστέρηση.  Η διαδικασία επαναλαμβάνετε μέχρι την εμφάνιση όλων των ζευγών καρτών\r\n"+
                        "Το παιχνίδι ολοκληρώνεται και ο παίκτης κερδίζει με την επιτυχή εμφάνιση του συνόλου των ζευγών καρτών\r\n"+
                        "\r\n"+
                        "Κάθε φορά που επιλέγονται δύο κάρτες ο μετρητής προσπαθειών αυξένεται\r\n"+
                        " Προσπαθείστε να κερδίσετε με τον λιγότερο αριθμό προσπαθειών!!!";
        JOptionPane.showMessageDialog(this.mainFrame, HOWTOPLAYTEXT
                , "Οδηγίες Παιχνιδιού", JOptionPane.PLAIN_MESSAGE);
    }

    // Ταυτότητα παιχνιδιού
    private void showAbout()
    {
        final String ABOUTTEXT =
                "Το παιχνίδει μνήμης είναι μία εφαρμογή σε Java\r\n" +
                        "που αναπτύχθηκε από το Κώστα Πάγκαλο και τον Κατή Νικόλαο.\r\n" +
                        "\r\n" +
                        "Προπτυχιακοί φοιτητές\r\n" +
                        "στο Τμήμα Μηχανικών Πληροφοριακών και Επικοινωνιακών Συστημάτων\r\n" +
                        "του Πανεπιστημίου Αιγαίου στη Σάμο\r\n" +
                        "\r\n" +
                        "------------------------------------------\r\n" +
                        "Σάμος Ιανουάριος 2020";
        JOptionPane.showMessageDialog(this.mainFrame, ABOUTTEXT
                , "Ταυτότητα παχνιδιού μνήμης", JOptionPane.PLAIN_MESSAGE);
    }

    // Επιλογή μενού
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Έναρξη νέου παχνιδιού")) {
            getLevel();
        }
        else if(e.getActionCommand().equals("Έισαγωγή στοιχείων χρήστη")){
            getPlayerName();
        }
        else if(e.getActionCommand().equals("Επίπεδο δυσκολίας")) {
            getLevel();
        }
        else if(e.getActionCommand().equals("Οδηγίες παιχνιδιού")) {
            showInstructions();
        }
        else if(e.getActionCommand().equals("Ταυτότητα")) {
            showAbout();
        }
        else if(e.getActionCommand().equals("Έξοδος από το παιχνίδι")) {
            System.exit(0);
        }
    }

    // Λήψη ονόματος παίκτη
    public void getPlayerName(){
        // Λήψη ονόματος παίκτη και επίπεδο δυσκολίας
        playerName = JOptionPane.showInputDialog(this.mainFrame, "Δώστε το όνομά σας");
    }

    //Λήψη επιπέδου δυσκολίας
    public void getLevel(){
        String levelGame = JOptionPane.showInputDialog(this.mainFrame, "Επιλέξτε (1,2 ή 3), το επίπεδο δυσκολίας: 1. Εύκολο, 2. Μέτριο, 3. Δύσκολο");

        if (levelGame.equals("1")){
            rows=5;
            cols=5;
        }
        else if (levelGame.equals("2")){
            rows=8;
            cols=8;
        }
        else if (levelGame.equals("3")){
            rows=10;
            cols=10;
        }
        newGame(rows, cols);
    }

    // Δημιουργία καρτών παιχνιδιού
    public JPanel makeCards()
    {
        // Δημιουργία πανελ για προσθήκη καρτών
        JPanel panel = new JPanel(new GridLayout(this.rows, this.cols));
        Card newCard;

        // Προσθέτει όλα τα σχήματα
        for (int i = 0 ; i<2 ; i++){
            newCard = new Card(this, EnumShape.CIRCLE, Color.blue, false);
            cards.add(newCard);
            newCard = new Card(this, EnumShape.SQUARE, Color.blue, false);
            cards.add(newCard);
            newCard = new Card(this, EnumShape.RECTANGLE, Color.blue, false);
            cards.add(newCard);
        }

        // Εισαγωγή Joker στις κάρτες
        newCard = new Card(this,EnumShape.JOKER, Color.black, true);
        cards.add(newCard);
        if (this.jokerOnBoard==2) {
            newCard = new Card(this, EnumShape.JOKER, Color.black, true);
            cards.add(newCard);
        }

        int pairCards = this.cardsPairOnBoard - 3;
        int shapeNo = 0;
        int colorNo = 0;

        // Επαναληπτικά προσθέτει υπόλοιπες κάρτες
        // με τυχαία επιλογή σχήματος και χρώματος γέμισης
        EnumShape cardShape = EnumShape.NONE;
        Color colorShape = null;
        for (int i = 0 ; i < pairCards ; i++){

            // Τυχαία επιλογή σχήματος
            shapeNo = getRandomShapeOrColor(this.shapesOnCard);
            if (shapeNo == 1){
                cardShape = EnumShape.CIRCLE;
            }
            else if (shapeNo == 2) {
                cardShape = EnumShape.RECTANGLE;
            }
            else if (shapeNo == 3) {
                cardShape = EnumShape.SQUARE;
            }

            // Τυχαία επιλογή χρώματος γέμισης
            colorNo = getRandomShapeOrColor(this.colorsOnCard);
            if (colorNo == 1 ){
                colorShape = Color.MAGENTA;
            }
            if (colorNo == 2 ){
                colorShape = Color.BLUE;
            }
            if (colorNo == 3 ){
                colorShape = Color.GREEN;
            }
            if (colorNo == 4 ){
                colorShape = Color.CYAN;
            }
            if (colorNo == 5 ){
                colorShape = Color.PINK;
            }

            // Δημιουργία ζεύγους καρτών και προσθήκη αυτών στη λίστα
            newCard = new Card(this,cardShape, colorShape, false);
            cards.add(newCard);
            newCard = new Card(this,cardShape, colorShape, false);
            cards.add(newCard);
        }

        // ανακάτεμα καρτών στο παιχνίδι
        long seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));

        // Προσθήκη καρτών στο παράθυρο
        for (Card item : cards){
            panel.add(item);
        }

        // επιστρέφει το πλαίσιο με τις κάρτες του παιχνιδιού
        return panel;
    }

    // Επιλογή τυχαίου αριθμού σχήματος ή χρώματος
    private int getRandomShapeOrColor(int maxShapesOrColors){
        long seed = System.nanoTime();
        Random randomizer = new Random(seed);
        int d = 1 + randomizer.nextInt(maxShapesOrColors);
        return d;
    }


    // Έναρξη νέου παιχνιδιού
    public void newGame(int rows, int cols)
    {
        // Καθορίζει το πλέγμα στο παιχνίδι
        this.rows = rows;
        this.cols = cols;

        // Ορίζουμε το μέγιστο αριθμό προσπθειών ίσο με το πλήθος των καρτών
        this.maxFailCount =   ( rows * cols );
        this.failCounter = 0;

        // Υπολογισμός αριθμού Μπαλαντέρ και αριθμού ζευγών καρτών
        if ((rows * cols) % 2 == 1) {
            this.cardsPairOnBoard = ((rows * cols) - 1) / 2;
            this.jokerOnBoard = 1;
        }
        else {
            this.cardsPairOnBoard = ((rows * cols) - 2) / 2;
            this.jokerOnBoard = 2;
        }
        // Αριθμός καρτών σοτ παιχνίδι πλην Μπαλαντέρ
        this.cardsOnBoard = 2 * this.cardsPairOnBoard;

        // Αρχικοποίηση λιστών καρτών
        cards = new ArrayList<>();
        cardsShow = new ArrayList<>();
        cardsCheck = new ArrayList<>();

        // Εκκαθάριση γραφικών στοιχείων από το περιεχόμενο
        this.mainContentPane.removeAll();
        // Δημιουργία νέων καρτών και προσθήκη αυτών στο παράθυρο
        this.mainContentPane.add(makeCards());
        // Εμφάνιση παραθύρου
        this.mainFrame.setVisible(true);

    }

    // Προσθήκη καρτών με το κλικ του παίκτη
    public void addSelectedCard(Card card){
        if (cardsCheck.size() == 0){
            cardsCheck.add(card);
            card.setShapeVisible(true);
        }
        else if (cardsCheck.size() == 1){
            cardsCheck.add(card);
            card.setShapeVisible(true);
        }
    }

    // Έλεγχος εάν οι κάρτες είναι ίδιες
    public void checkSelectedCards(){
        if (cardsCheck.size() == 2){
            if (cardsCheck.get(0).equals(cardsCheck.get(1))){
                cardsShow.add(cardsCheck.get(0));
                cardsShow.add(cardsCheck.get(1));

                // Έλεγχος εάν ο παίκτης έχει βρει όλα τα ζεύγη καρτών
                if (cardsShow.size() == this.cardsOnBoard){
                    JOptionPane.showMessageDialog(this.mainFrame, "Μπαράβο !!! Βρήκατε όλα τα ζεύγη"
                            , "Αποτέλεσμα", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
            }
            else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cardsCheck.get(0).setShapeVisible(false);
                cardsCheck.get(1).setShapeVisible(false);

                this.failCounter +=1;

                // Έλεγχος εάν ο παίκτης έχει υπερβεί το μέγιστο αριθμό προσπαθειών
                if (this.failCounter > this.maxFailCount ){
                    JOptionPane.showMessageDialog(this.mainFrame, "Αποτυχία... Υπερβήκατε το μέγιστο αριθμό προσπαθειών:  " + this.maxFailCount
                            , "Αποτέλεσμα", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
            }

            // Εκκαθάριση ζεύγους επιλεγμένων καρτών
            cardsCheck.clear();
        }
    }

}
