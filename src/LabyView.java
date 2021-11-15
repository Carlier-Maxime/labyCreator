import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LabyView extends JPanel {
    private View parent;
    private final Laby laby;
    private Random rng;
    private ArrayList<Case> paintCases;
    private boolean repaint = false;

    public LabyView(View parent){
        super();
        this.parent = parent;
        rng = new Random();
        paintCases = new ArrayList<>();
        laby = new Laby(this);
        addKeyListener(new Controller(laby, this));
        requestFocus();
        setVisible(true);
    }

    public void paint(Case caze){
        paintCases.add(caze);
        repaint = false;
        paintComponents(getGraphics());
    }
    public void paint(ArrayList<Case> casesPaint){
        paintCases.addAll(casesPaint);
        repaint = false;
        paintComponents(getGraphics());
    }

    public void paint(){
        repaint = true;
        paintComponents(getGraphics());
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        if (laby != null && !laby.arrayIsNull()){
            int sizeX = getWidth()/laby.getWidth();
            int sizeY = getHeight()/ laby.getHeight();
            if (repaint){
                for (int x=0; x<laby.getWidth(); x++){
                    for (int y=0; y<laby.getHeight(); y++){
                        Case caze = laby.getCase(x,y);
                        g.setColor(caze.getColor());
                        g.fillRect(x*sizeX, y*sizeY, sizeX, sizeY);
                    }
                }
                repaint = false;
            } else {
                for (Case caze : paintCases){
                    g.setColor(caze.getColor());
                    g.fillRect(caze.getPosition()[0]*sizeX, caze.getPosition()[1]*sizeY, sizeX, sizeY);
                }
                paintCases = new ArrayList<>();
            }
        }
    }

    public View getJFrame(){
        return parent;
    }
}
