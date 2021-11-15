import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Case {
    private final Laby laby;
    private boolean isMur;
    private int distance;
    private final int[] location;
    private Color color;

    public Case(Laby laby, int[] location, boolean isMur) {
        this.isMur = isMur;
        this.laby = laby;
        distance = -1;
        this.location = location;
        updateColor();
    }

    public Case(Laby laby, int posX, int posY, boolean isMur){
        this(laby, new int[]{posX, posY}, isMur);
    }

    public void updateColor(){
        if (distance==-1){
            color = (isMur? Color.BLACK : Color.WHITE);
        } else if (distance == -2){
            color = (Color.BLUE);
        } else {
            int max = laby.getMaxDistance();
            color = (new Color((distance*255)/max,255-((distance*255)/max),0));
        }
    }

    public void setIsMur(boolean isMur){
        this.isMur = isMur;
        updateColor();
    }

    public boolean isMur() {
        return isMur;
    }

    public void setDistance(int distance) {
        this.distance = distance;
        updateColor();
    }

    public int getDistance() {
        return distance;
    }

    public int[] getPosition() {
        return location;
    }

    public Color getColor() {
        return color;
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
