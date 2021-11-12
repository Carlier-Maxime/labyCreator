import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
    private final Laby laby;
    private final View view;
    private Thread thread;

    public Controller(Laby laby, View view) {
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
            new SizeView(view, laby);
        } else if (e.getKeyCode() == KeyEvent.VK_I){
            laby.initArray(271,201);
        } else if (e.getKeyCode() == KeyEvent.VK_B){
            if(!laby.breakOneMur()){
                laby.allPartsInOne();
            };
        } else if (e.getKeyCode() == KeyEvent.VK_R){
            thread = new Thread(laby::resolve);
            thread.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
