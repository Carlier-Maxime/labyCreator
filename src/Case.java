import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Case {
    private final Laby laby;
    private boolean isMur;
    private int distance;
    private final int[] location;
    private Color color;
    private int[] size;
    private int[] drawPos;

    public Case(Laby laby, int[] location, boolean isMur) {
        this.isMur = isMur;
        this.laby = laby;
        distance = -1;
        this.location = location;
        updateColor();
        size = new int[]{1,1};
        drawPos = new int[]{0,0};
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

    public int[] getSize() {
        return size;
    }

    public int[] getDrawPos() {
        return drawPos;
    }

    public void setDrawPos(int[] drawPos) {
        this.drawPos = drawPos;
    }

    public void setSize(int[] size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Case{" +
                "laby=" + laby +
                ", isMur=" + isMur +
                ", distance=" + distance +
                ", location=" + Arrays.toString(location) +
                ", color=" + color +
                ", size=" + Arrays.toString(size) +
                ", drawPos=" + Arrays.toString(drawPos) +
                '}';
    }
}
