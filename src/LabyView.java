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
            if (repaint){
                int[] sizeX = Utils.repartition(getWidth(), laby.getWidth());
                int[] sizeY = Utils.repartition(getHeight(), laby.getHeight());
                int posX = 0;
                for (int x=0; x<laby.getWidth(); x++){
                    int posY = 0;
                    for (int y=0; y<laby.getHeight(); y++){
                        Case caze = laby.getCase(x,y);
                        caze.setSize(new int[]{sizeX[x], sizeY[y]});
                        caze.setDrawPos(new int[]{posX, posY});
                        g.setColor(caze.getColor());
                        g.fillRect(posX, posY, sizeX[x], sizeY[y]);
                        posY+=sizeY[y];
                        System.out.print(caze+" | ");
                    }
                    System.out.println();
                    posX+=sizeX[x];
                }
                repaint = false;
            } else {
                for (Case caze : paintCases){
                    int sizeX = caze.getSize()[0];
                    int sizeY = caze.getSize()[1];
                    g.setColor(caze.getColor());
                    g.fillRect(caze.getDrawPos()[0], caze.getDrawPos()[1], sizeX, sizeY);
                }
                paintCases = new ArrayList<>();
            }
        }
    }

    public View getJFrame(){
        return parent;
    }
}
