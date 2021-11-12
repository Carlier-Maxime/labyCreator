import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class View extends JFrame {
    private final Laby laby;

    public View(){
        super("Labyrinthe");
        laby = new Laby(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(getToolkit().getScreenSize());
        setLayout(new GridBagLayout());

        reDrawLaby();

        addKeyListener(new Controller(laby, this));

        setVisible(true);
    }

    public void reDrawLaby(){
        getContentPane().removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;

        JPanel pan = new JPanel();
        pan.setLayout(new GridBagLayout());
        pan.setBorder(new EmptyBorder(5,5,5,5));
        add(pan, c);

        for (int i=0; i<laby.getHeight(); i++){
            c.gridy = i;
            for (int j=0; j<laby.getWidth(); j++){
                c.gridx = j;
                pan.add(laby.getCase(j,i), c);
            }
        }
        reDraw();
    }

    public void reDraw(){
        repaint();
        revalidate();
    }

    public static void main(String[] args) {
        new View();
    }
}
