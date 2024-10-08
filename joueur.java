/*
 * thomas morot-gaudry et bryan thiranna 25/09/2024 GRP Fb
 */
package tp1;

import java.util.Scanner;

/**
 *
 * @author thoma
 */
public class Joueur {

    //attributs
    private String pseudo;
    private int grille[][];
    private boolean jetonBleu;
    private boolean jetonVert;
    private boolean jetonJaune;
    private boolean jetonRouge;
    private int nbreVides;

    // constructeurs
    public Joueur(String valPseudo) {
        pseudo = valPseudo;

        grille = new int[4][4];
        jetonBleu = false;
        jetonVert = false;
        jetonJaune = false;
        jetonRouge = false;
        nbreVides = 16;

    }

    public void afficher() {
        System.out.println("pseudo:" + pseudo);
        afficherGrille();

    }

    //Affichage grille
    public void afficherGrille() {
        for (int i = 0; i < 4; i++) {
            System.out.print((char) ('A' + i) + "|");
            for (int j = 0; j < 4; j++) {
                System.out.print(grille[i][j] + "|");
            }
            System.out.println();
        }

    }

}
