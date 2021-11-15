import java.util.Arrays;
import java.util.Scanner;

public class RepartitionTest {
    public static int[] repartition(int longeur, int nbCase){
        int[] tab = new int[nbCase];
        for (int i=0; i<nbCase; i++){
            tab[i] = longeur/nbCase;
        }
        int s=longeur-(longeur%nbCase);
        while (longeur-s!=0){
            double prePas = nbCase*1.0/(longeur-s);
            int pas = (int)prePas;
            //System.out.println(prePas+" "+(prePas-pas));
            if (prePas-pas!=0){
                pas++;
            }
            //System.out.print(pas+" ");
            for (int i=0; i<nbCase; i++){
                if (i%pas == 0){
                    tab[i]++;
                }
            }
            s = 0;
            for (int n : tab){
                s+=n;
            }
            //System.out.println(s);
            if (s>longeur){
                System.exit(1);
            }
        }
        return tab;
    }
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        System.out.print("Longueur dispo : ");
        int longeur = scnr.nextInt();
        System.out.print("Nbr cases : ");
        int nbCase = scnr.nextInt();
        if (nbCase > longeur){
            System.out.println("nb cases plus grande que longeur, impossible");
            System.exit(1);
        }
        for (int i=1; i<1400; i++){
            repartition(1400,i);
        }
        int[] tab = RepartitionTest.repartition(longeur,nbCase);
        System.out.println(Arrays.toString(tab));
        int s=0;
        for (int n : tab){
            s++;
        }
        System.out.println("Longeur affichage = "+s);
    }
}
