import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Case extends JPanel {
    private final Laby laby;
    private boolean isMur;
    private int distance;
    private final int[] location;

    public Case(Laby laby, int[] location, boolean isMur) {
        this.isMur = isMur;
        this.laby = laby;
        distance = -1;
        this.location = location;
        setMinimumSize(new Dimension(1,1));
        updateColor();
    }

    public Case(Laby laby, int posX, int posY, boolean isMur){
        this(laby, new int[]{posX, posY}, isMur);
    }

    public void updateColor(boolean draw){
        if (distance==-1){
            setBackground(isMur? Color.BLACK : Color.WHITE);
        } else if (distance == -2){
            setBackground(Color.BLUE);
        } else {
            int max = laby.getMaxDistance();
            setBackground(new Color((distance*255)/max,255-((distance*255)/max),0));
        }
        if (draw) {
            try {
                Thread t = new Thread(() -> {
                    repaint();
                    revalidate();
                });
                t.start();
                t.join();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void updateColor(){
        updateColor(true);
    }
    public void setIsMur(boolean isMur){
        this.isMur = isMur;
        updateColor();
    }

    public boolean isMur() {
        return isMur;
    }

    public void setDistance(int distance, boolean draw) {
        this.distance = distance;
        updateColor(draw);
    }

    public void setDistance(int distance){
        setDistance(distance,true);
    }

    public int getDistance() {
        return distance;
    }

    public int[] getPosition() {
        return location;
    }

    @Override
    public String toString() {
        return "Case{" +
                "isMur=" + isMur +
                ", distance=" + distance +
                ", location=" + Arrays.toString(location) +
                '}';
    }
}
