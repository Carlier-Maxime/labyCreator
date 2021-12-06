import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Laby {
    private LabyView view;
    private final Random rng;
    private ArrayList<ArrayList<Case>> array;
    private ArrayList<Case> walls;
    private ArrayList<ArrayList<Case>> partsOfLaby;
    private int maxDistance;
    private int[] size;

    public Laby(LabyView view, int w, int h) {
        this.view = view;
        rng = new Random();
        maxDistance = 1;
        size = new int[]{w,h};
        //createLaby(w,h);
    }

    public Laby(LabyView view) {
        this(view, 25, 25);
    }



    /*
    Methode initialisant le tableau en forme de quadrillage comme ceci :
    1 1 1 1 1 1 1
    0 0 1 0 1 0 1
    1 1 1 1 1 1 1
    1 0 1 0 1 0 1
    1 1 1 1 1 1 1
    1 0 1 0 1 0 0
    1 1 1 1 1 1 1
     */
    public void initArray(int w, int h){
        if (w%2==0){
            w++;
        }
        if (h%2==0){
            h++;
        }
        size = new int[]{w,h};
        array = null;
        view.repaint();
        array = new ArrayList<>(h);
        ArrayList<Case> paints;

        paints = new ArrayList<>();
        for (int i=0; i<h; i++){
            array.add(new ArrayList<>(w));
            for (int j=0; j<w; j++){
                Case caze = new Case(this, j, i, (i%2==0 || j%2==0) && !(i==1 && j==0 || i==h-2 && j==w-1));
                array.get(i).add(caze);
                paints.add(caze);
            }
        }
        createPartsOfLaby();
        view.paint();
    }

    /*
    Methode créant la liste des murs et les parties du labyrinthe
    Ne fonctionne uniquement juste après l'initArray()
     */
    public void createPartsOfLaby(){
        int w = size[0];
        int h = size[1];
        walls = new ArrayList<>(w*4);
        partsOfLaby = new ArrayList<>();
        maxDistance = (w-1)*(h-1)/4;

        ArrayList<Integer> indexPart = new ArrayList<>(maxDistance);
        for (int i=0; i<maxDistance; i++){
            indexPart.add(i);
        }
        Collections.shuffle(indexPart);

        for (int i=0; i<h; i++){
            for (int j=0; j<w; j++){
                Case caze = array.get(i).get(j);
                if (caze.isMur()){
                    walls.add(caze);
                } else {
                    if ((i==1 && j==0 || i==h-2 && j==w-1)){
                        walls.add(caze);
                    } else {
                        ArrayList<Case> part = new ArrayList<>();
                        int dist = indexPart.get(rng.nextInt(indexPart.size()));
                        caze.setDistance(dist);
                        indexPart.remove((Integer) dist);
                        part.add(caze);
                        partsOfLaby.add(part);
                    }
                }
            }
        }
    }

    /*
    Methode regroupant toutes les parties du labyrinthe en une seule,
    (permet de créer un chemin de resolution)
    Et remet les distances a -1
     */
    public void allPartsInOne(){
        while (partsOfLaby.size()>1){
            breakOneMur();
        }
        maxDistance = (partsOfLaby.get(0).size()-1)/2;
        for (Case caze : partsOfLaby.get(0)){
            caze.setDistance(-1);
        }
        view.paint(partsOfLaby.get(0));
    }

    public boolean breakOneMur(){
        if (partsOfLaby.size()<=1){
            return false;
        }
        int w = size[0];
        int h = size[1];
        ArrayList<Case> part = partsOfLaby.get(rng.nextInt(partsOfLaby.size()));
        for (Case caze : part) {
            int n = rng.nextInt(4);
            Case interCase;
            Case case2;
            int[] direction;
            int avant = n;
            boolean find = true;
            while (true) {
                direction = getDirection(n);
                interCase = getCase(caze.getPosition()[0]+direction[0], caze.getPosition()[1]+direction[1]);
                if (!(interCase.getPosition()[0]<=0 || interCase.getPosition()[0]>=w-1 || interCase.getPosition()[1]<=0 || interCase.getPosition()[1]>=h-1)){
                    case2 = getCase(interCase.getPosition()[0]+direction[0], interCase.getPosition()[1]+direction[1]);
                    if (!(case2.getDistance() == caze.getDistance() || case2.getDistance() == -1)){
                        break;
                    }
                }
                n++;
                if (n>=4){n=0;}
                if (n==avant){
                    case2 = null;
                    find = false;
                    break;
                }
            }
            if (find){
                interCase.setIsMur(false);
                interCase.setDistance(caze.getDistance());
                part.add(interCase);
                for (ArrayList<Case> partBis : partsOfLaby){
                    if (partBis.get(0).getDistance() != case2.getDistance()){
                        continue;
                    }
                    for (Case c : partBis){
                        c.setDistance(caze.getDistance());
                        part.add(c);
                    }
                    view.paint(partBis);
                    partsOfLaby.remove(partBis);
                    break;
                }
                break;
            }
        }
        return true;
    }

    private int[] getDirection(int n){
        int[] direction;
        switch (n) {
            case 0 : direction = new int[]{0, -1}; break;
            case 1 : direction = new int[]{1, 0};break;
            case 2 : direction = new int[]{0, 1};break;
            case 3 : direction = new int[]{-1, 0};break;
            default :
                direction = new int[]{0, 0};
                System.out.println("Problem de construction du labyrinthe ! (switch du random direction)");
                System.exit(1);
        }
        return direction;
    }

    public void resolve(boolean draw){
        if (!draw){maxDistance = (partsOfLaby.get(0).size()-1)/2;}
        ArrayList<ArrayList<Case>> paths = new ArrayList<>();
        ArrayList<Case> path = new ArrayList<>();
        int newMaxDist = 0;
        getCase(0,1).setDistance(0);
        path.add(getCase(0,1));
        paths.add(path);

        boolean find = false;
        while (paths.size()>0){
            path = getShortPath(paths);
            Case endCase = path.get(path.size()-1);
            if (endCase.getPosition()[0] == getWidth()-1 && endCase.getPosition()[1] == getHeight()-2){
                find = true;
                break;
            }
            for (int n=0; n<4; n++){
                int[] direction = getDirection(n);
                int[] pos = new int[]{endCase.getPosition()[0]+direction[0], endCase.getPosition()[1]+direction[1]};
                if (pos[0]>0 && pos[1]>0 && pos[0]<getWidth() && pos[1]<getHeight()){
                    Case caze = getCase(pos);
                    if (!caze.isMur() && (path.size()==1 || caze != path.get(path.size()-2))){
                        if (draw){caze.setDistance(path.size());view.paint(caze);}
                        ArrayList<Case> path2 = new ArrayList<>(path);
                        path2.add(caze);
                        paths.add(path2);
                        newMaxDist = path2.size()-1;
                    }
                }
            }
            paths.remove(path);
        }
        if (!draw){
            maxDistance = newMaxDist;
            resolve(true);
        }
        if (find && draw){
            for (Case caze : path){
                caze.setDistance(-2);
                view.paint(caze);
            }
        }
    }

    public void resolve(){
        resolve(false);
    }

    private ArrayList<Case> getShortPath(ArrayList<ArrayList<Case>> paths){
        ArrayList<Case> shortPath = paths.get(0);
        for (ArrayList<Case> path : paths){
            if (path.size() < shortPath.size()){
                shortPath = path;
            }
        }
        return shortPath;
    }

    public void createLaby(int w, int h){
        initArray(w,h);
        allPartsInOne();
    }

    public ArrayList<ArrayList<Case>> getPartsOfLaby() {
        return partsOfLaby;
    }

    public int getWidth(){
        return size[0];
    }

    public int getHeight(){
        return size[1];
    }

    public boolean arrayIsNull(){
        return array==null;
    }

    public Case getCase(int x, int y){
        return array.get(y).get(x);
    }

    private Case getCase(int[] pos) {
        return getCase(pos[0], pos[1]);
    }

    public void setSize(int w, int h) {
        this.size = new int[]{w,h};
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public static void main(String[] args) {
        new Laby(null);
    }
}
