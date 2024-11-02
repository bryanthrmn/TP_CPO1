// Bryan THIRIMANNA & Thomas MOROT-GAUDRY
// 2A - Semestre 3
// Projet 1 Java
// Octobre - Novembre 2024

package tp1;

import java.util.Random;
import java.util.Scanner;

class Joueur {
    private final String pseudo;
    private final int[][] grille = new int[4][4];
    private boolean jetonBleu, jetonVert, jetonJaune, jetonRouge;
    private int nbreVides = 16;
    private final int nbgrilles = 0; // Nouveau champ pour compter les grilles utilisées
    private final boolean estOrdinateur;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.estOrdinateur = pseudo.equals("Ordinateur");
    }

    public String getPseudo() {
        return pseudo;
    }

    public void afficherGrille() {
        System.out.println("      1     2     3     4");
        for (int i = 0; i < 4; i++) {
            System.out.print((char) ('A' + i) + "  |");
            for (int j = 0; j < 4; j++) {
                String symbole = switch (grille[i][j]) {
                    case 1 -> "B";
                    case 2 -> "V";
                    case 3 -> "J";
                    case 4 -> "R";
                    default -> " ";
                };
                System.out.print("  " + symbole + "  |");
            }
            System.out.println();
        }
    }

    public Joueur verifTermine(Joueur adversaire) {
        if (this.verifGagne()) return this;
        if (adversaire.verifGagne()) return adversaire;
        return null;
    }

    public int[] tirageCouleurs() {
        Random rand = new Random();
        int couleur1 = rand.nextInt(4) + 1;
        int couleur2;
        do {
            couleur2 = rand.nextInt(4) + 1;
        } while (couleur1 == couleur2);
        return new int[]{couleur1, couleur2};
    }

    public int choixCouleur(int[] couleurs) {
        if (estOrdinateur) {
            return choixCouleurIA(couleurs);
        } else {
            Scanner sc = new Scanner(System.in);
            int choix;
            System.out.println("Choisissez une couleur : 0 - " + couleurEnLettres(couleurs[0]) + ", 1 - " + couleurEnLettres(couleurs[1]));
            choix = Main.saisirEntier(sc, "Erreur, veuillez saisir 0 ou 1.");
            return choix == 0 ? couleurs[0] : couleurs[1];
        }
    }

private int choixCouleurIA(int[] couleurs) {
    // l'IA choisit en priorité une couleur d'un jeton qu'elle ne possède pas
    if (!jetonBleu && (couleurs[0] == 1 || couleurs[1] == 1)) return 1;
    if (!jetonVert && (couleurs[0] == 2 || couleurs[1] == 2)) return 2;
    if (!jetonJaune && (couleurs[0] == 3 || couleurs[1] == 3)) return 3;
    if (!jetonRouge && (couleurs[0] == 4 || couleurs[1] == 4)) return 4;

    // Par défaut, elle choisira la 1re couleur proposée
    return couleurs[0];
}


    public int[] placeCouleur(int couleur) {
        if (estOrdinateur) {
            return placeCouleurIA(couleur);
        } else {
            Scanner sc = new Scanner(System.in);
            int ligne = 0, colonne = 0;
            while (true) {
                System.out.println("Entrez une position (XY ; avec X une lettre entre A et D - Y un chiffre entre 1 et 4):");
                String coordonnee = sc.next();
                if (coordonnee.equalsIgnoreCase("stop") || coordonnee.equalsIgnoreCase("quit")) {
                    System.out.println("La partie a ete interrompue.");
                    System.out.println(" ");
                    System.exit(0);
            }
                ligne = coordonnee.charAt(0) - 'A';
                colonne = coordonnee.charAt(1) - '1';
                if (ligne >= 0 && ligne < 4 && colonne >= 0 && colonne < 4 && grille[ligne][colonne] == 0) {
                    break;
                } else {
                    System.out.println("Position invalide ou case deja utilisee, veuillez reessayer.");
                }
            }
            grille[ligne][colonne] = couleur;
            nbreVides--;
            return new int[]{ligne, colonne};
        }
    }

private int[] placeCouleurIA(int couleur) {
    // Tentative de trouver une case libre proche d'un pion de même couleur pour faciliter un alignement
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            if (grille[i][j] == 0 && caseFavorisantAlignement(i, j, couleur)) {
                grille[i][j] = couleur;
                nbreVides--;
                return new int[]{i, j};
            }
        }
    }

    // Si aucune case stratégique n'est trouvée, elle place son pion dans une case libre aléatoirement
    Random rand = new Random();
    int ligne, colonne;
    do {
        ligne = rand.nextInt(4);
        colonne = rand.nextInt(4);
    } while (grille[ligne][colonne] != 0);
    grille[ligne][colonne] = couleur;
    nbreVides--;
    return new int[]{ligne, colonne};
}

// Méthode auxiliaire pour vérifier si le placement est stratégique
private boolean caseFavorisantAlignement(int ligne, int colonne, int couleur) {
    int totalAligne = 0;

    // l'IA vérifie les lignes pour compter les pions des mêmes couleurs
    for (int j = 0; j < 4; j++) {
        if (grille[ligne][j] == couleur) totalAligne++;
    }
    if (totalAligne >= 2) return true;

    // l'IA vérfie les colonnes 
    totalAligne = 0;
    for (int i = 0; i < 4; i++) {
        if (grille[i][colonne] == couleur) totalAligne++;
    }
    if (totalAligne >= 2) return true;

    // l'IA vérifie les diagonales
    if (ligne == colonne) { // Diagonale principale
        totalAligne = 0;
        for (int d = 0; d < 4; d++) {
            if (grille[d][d] == couleur) totalAligne++;
        }
        if (totalAligne >= 2) return true;
    }
    if (ligne + colonne == 3) { // Diagonale secondaire
        totalAligne = 0;
        for (int d = 0; d < 4; d++) {
            if (grille[d][3 - d] == couleur) totalAligne++;
        }
        if (totalAligne >= 2) return true;
    }

    return false;
}


    public boolean verifAligne(int[] coordonnees) {
        int ligne = coordonnees[0], colonne = coordonnees[1], couleur = grille[ligne][colonne];
        return (grille[ligne][0] == couleur && grille[ligne][1] == couleur && grille[ligne][2] == couleur && grille[ligne][3] == couleur) ||
               (grille[0][colonne] == couleur && grille[1][colonne] == couleur && grille[2][colonne] == couleur && grille[3][colonne] == couleur) ||
               (grille[0][0] == couleur && grille[1][1] == couleur && grille[2][2] == couleur && grille[3][3] == couleur) ||
               (grille[3][0] == couleur && grille[2][1] == couleur && grille[1][2] == couleur && grille[0][3] == couleur);
    }

    public void videGrille() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grille[i][j] = 0;
            }
        }
        nbreVides = 16;
    }

    public void recoitJeton(int couleur) {
        switch (couleur) {
            case 1 -> jetonBleu = true;
            case 2 -> jetonVert = true;
            case 3 -> jetonJaune = true;
            case 4 -> jetonRouge = true;
        }
    }

    public void tourJeu(Joueur adversaire) {
        System.out.println("C'est au tour de " + pseudo);
        int[] couleurs = tirageCouleurs();
        int couleur = choixCouleur(couleurs);
        int[] position = placeCouleur(couleur);

        if (verifAligne(position)) {
            recoitJeton(couleur);
            System.out.println("Felicitations, " + pseudo + " a aligne 4 pions " + couleurEnLettres(couleur) + " et gagne un jeton " + couleurEnLettres(couleur) + " !");
            System.out.println(" ");
            videGrille();
        }
        afficherGrille();
    }

public void tourJeuIA(Joueur adversaire) {
    System.out.println("C'est au tour de l'" + pseudo + ".");

    // Si la grille est pleine sans alignement, on la vide pour continuer le jeu
    if (nbreVides == 0) {
        System.out.println(pseudo + " a rempli la grille sans alignement. Reinitialisation de la grille.");
        System.out.println(" ");

        videGrille();
    }

    int[] couleurs = tirageCouleurs();
    int couleur = choixCouleurIA(couleurs);
    int[] position = placeCouleurIA(couleur);

    // Vérifie si un alignement est formé après le tour
    if (verifAligne(position)) {
        recoitJeton(couleur);
        System.out.println(pseudo + " a aligne 4 pions " + couleurEnLettres(couleur) + " et gagne un jeton " + couleurEnLettres(couleur) + " !");
        System.out.println(" ");
        videGrille(); // Réinitialise la grille si un alignement est détecté
    }

    afficherGrille();
}

    public boolean verifGagne() {
        return jetonBleu && jetonVert && jetonJaune && jetonRouge;
    }

    private String couleurEnLettres(int couleur) {
        return switch (couleur) {
            case 1 -> "bleu";
            case 2 -> "vert";
            case 3 -> "jaune";
            case 4 -> "rouge";
            default -> "inconnue";
        };
    }
}
