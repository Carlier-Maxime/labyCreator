import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
    private final Laby laby;
    private final LabyView view;
    private Thread thread;

    public Controller(Laby laby, LabyView view) {
        this.laby = laby;
        this.view = view;
        thread = null;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (thread != null && thread.isAlive()){
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_C){
            new SizeView(view.getJFrame(), laby);
        } else if (e.getKeyCode() == KeyEvent.VK_I){
            laby.initArray(25,25);
        } else if (e.getKeyCode() == KeyEvent.VK_B){
            if(!laby.breakOneMur()){
                laby.allPartsInOne();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_R){
            thread = new Thread(laby::resolve);
            thread.start();
        } else if (e.getKeyCode() == KeyEvent.VK_P){
            view.repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_S){
            System.out.println("NONE ACTION");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
