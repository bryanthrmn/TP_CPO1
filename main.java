package tp1;

import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;
import static tp1.Main.afficherMenuPrincipal;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean quitter = false;

        while (!quitter) {
            afficherMenuPrincipal();
            int choix = sc.nextInt();

            switch (choix) {
                case 1 -> afficherMenuModeJeu(sc);
                case 2 -> afficherCredits();
                case 3 -> afficherReglesDuJeu();
                case 4 -> {
                    quitter = true;
                    System.out.println("À bientôt !");
                }
                default -> System.out.println("Erreur, veuillez saisir un choix valide.");
            }
        }
        sc.close();
    }

    public static void afficherMenuPrincipal() {
        System.out.println("===============TP 1================");
        System.out.println("[1] Nouvelle Partie");
        System.out.println("[2] À propos");
        System.out.println("[3] Règles du jeu");
        System.out.println("[4] Quitter");
    }

    public static void afficherMenuModeJeu(Scanner sc) {
        boolean retourMenu = false;

        while (!retourMenu) {
            System.out.println("=============Mode de jeu=============");
            System.out.println("[1] Mode 1vs1");
            System.out.println("[2] Mode humain vs IA");
            System.out.println("[3] Retour");
            int choixMode = sc.nextInt();

            switch (choixMode) {
                case 1 -> commencerNouvellePartie(sc, false); // Mode 1v1
                case 2 -> commencerNouvellePartie(sc, true);  // Mode humain vs IA
                case 3 -> retourMenu = true;
                default -> System.out.println("Erreur, veuillez saisir un choix valide.");
            }
        }
    }

    public static void commencerNouvellePartie(Scanner sc, boolean modeIA) {
        System.out.println("Entrez le pseudo du joueur 1 : ");
        String pseudo1 = sc.next();
        String pseudo2;

        if (modeIA) {
            pseudo2 = "IA";
        } else {
            System.out.println("Entrez le pseudo du joueur 2 : ");
            pseudo2 = sc.next();
        }

        Joueur joueur1 = new Joueur(pseudo1);
        Joueur joueur2 = modeIA ? new Joueur() : new Joueur(pseudo2);

        System.out.println("\nGrille de " + joueur1.getPseudo() + ":");
        joueur1.afficherGrille();
        System.out.println("\nGrille de " + joueur2.getPseudo() + ":");
        joueur2.afficherGrille();

        boolean partieTerminee = false;

        while (!partieTerminee) {
            joueur1.tourJeu(joueur2);
            if (joueur1.verifGagne()) {
                System.out.println(joueur1.getPseudo() + " a gagné la partie !");
                partieTerminee = true;
                break;
            }

            if (modeIA) {
                joueur2.tourJeuIA(joueur1);
                if (joueur2.verifGagne()) {
                    System.out.println(joueur2.getPseudo() + " a gagné la partie !");
                    partieTerminee = true;
                }
            } else {
                joueur2.tourJeu(joueur1);
                if (joueur2.verifGagne()) {
                    System.out.println(joueur2.getPseudo() + " a gagné la partie !");
                    partieTerminee = true;
                }
            }
        }

        joueur1.afficherStats();
        joueur2.afficherStats();
        System.out.println("\nGrille finale de " + joueur1.getPseudo() + ":");
        joueur1.afficherGrille();
        System.out.println("\nGrille finale de " + joueur2.getPseudo() + ":");
        joueur2.afficherGrille();
    }

    public static void afficherCredits() {
        System.out.println("=============À propos=============");
        System.out.println("Par 2 étudiants de l'EPF : Thomas MOROT-GAUDRY et Bryan THIRIMANNA");
    }

    public static void afficherReglesDuJeu() {
        System.out.println("=============Règles du jeu :=============");
        System.out.println("Le jeu d'alignement oppose deux joueurs, chacun recevant une grille vide de 4x4.");
        System.out.println("À chaque tour, deux pions de couleurs différentes sont tirés au sort.");
        System.out.println("Le but est d'aligner quatre pions de la même couleur pour gagner un jeton de cette couleur.");
        System.out.println("Le premier à collecter un jeton de chaque couleur gagne la partie.");
    }
    
        public static int saisirEntier(Scanner sc, String messageErreur) {
        int entier = -1;
        boolean valide = false;
        while (!valide) {
            try {
                entier = sc.nextInt();
                valide = true;
            } catch (InputMismatchException e) {
                System.out.println(messageErreur);
                sc.next(); // Vide le buffer pour éviter une boucle infinie
            }
        }
        return entier;
    }
}

class Joueur {
    private final String pseudo;
    private final int[][] grille;
    private final boolean[] jetonsGagnes;
    private int nbreVides = 16;
    private final boolean estOrdinateur;
    private final int[] statsJetons = new int[5];

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.grille = new int[4][4];
        this.jetonsGagnes = new boolean[5];
        this.estOrdinateur = false;
    }

    public Joueur() {
        this.pseudo = "Ordinateur";
        this.grille = new int[4][4];
        this.jetonsGagnes = new boolean[5];
        this.estOrdinateur = true;
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


public void afficherStats() {
    System.out.println("Statistiques de " + pseudo + " :");
    System.out.println("Jetons gagnés - Bleu : " + statsJetons[1] + ", Vert : " + statsJetons[2] + ", Jaune : " + statsJetons[3] + ", Rouge : " + statsJetons[4]);
    System.out.println("Nombre de grilles utilisées : " + compteurGrillesUtilisees); // Ajout du compteur
}


    public String couleurEnLettres(int couleur) {
        return switch (couleur) {
            case 1 -> "Bleu";
            case 2 -> "Vert";
            case 3 -> "Jaune";
            case 4 -> "Rouge";
            default -> "Inconnue";
        };
    }

    public int[] tirageCouleur() {
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
            int choix = -1;
            boolean valide = false;
            while (!valide) {
                try {
                    System.out.println("Choisissez une couleur : 0 - " + couleurEnLettres(couleurs[0]) + ", 1 - " + couleurEnLettres(couleurs[1]));
                    choix = sc.nextInt();
                    if (choix == 0 || choix == 1) {
                        valide = true;
                    } else {
                        System.out.println("Erreur, veuillez choisir 0 ou 1.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erreur, saisie non valide. Veuillez choisir 0 ou 1.");
                    sc.next(); // Vide le buffer
                }
            }
            return choix == 0 ? couleurs[0] : couleurs[1];
        }
    }

private int choixCouleurIA(int[] couleurs) {
    for (int couleur : couleurs) {
        if (!jetonsGagnes[couleur]) {
            return couleur; // Priorise les couleurs manquantes
        }
    }
    return couleurs[0]; // Choisir une couleur par défaut si toutes les couleurs sont obtenues
}

private int[] choixCoordonneesIA(int couleur) {
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            if (grille[i][j] == 0) { 
                grille[i][j] = couleur; // Simuler un placement
                if (verifAligne(new int[]{i, j})) {
                    grille[i][j] = 0; // Retirer la couleur après vérification
                    return new int[]{i, j}; // Si c'est une position d'alignement
                }
                grille[i][j] = 0; // Réinitialisation
            }
        }
    }
    return positionAleatoire(); // Retourne une position libre aléatoire
}

private int[] positionAleatoire() {
    Random rand = new Random();
    int ligne, colonne;
    do {
        ligne = rand.nextInt(4);
        colonne = rand.nextInt(4);
    } while (grille[ligne][colonne] != 0);
    return new int[]{ligne, colonne};
}

public void tourJeuIA(Joueur adversaire) {
    System.out.println("C'est au tour de " + pseudo + " (IA).");

    int[] couleurs = tirageCouleur();
    int choix = choixCouleurIA(couleurs);
    int[] coordonnees = placeCouleurIA(choix);

    if (verifAligne(coordonnees)) { // Vérifier l'alignement avant de donner un jeton
        recoitJeton(choix);
    } else if (nbreVides == 0) { // Réinitialiser si la grille est pleine sans alignement
        System.out.println("La grille est remplie sans alignement. Elle va être réinitialisée.");
        videGrille();
    }
    afficherGrille();
}



public int[] placeCouleur(int couleur) {
    Scanner sc = new Scanner(System.in);
    int ligne = -1, colonne = -1;
    boolean positionValide = false;

    while (!positionValide) {
        System.out.println("Entrez une position (ex: A1, B3):");
        String coordonnee = sc.next();

        try {
            ligne = coordonnee.charAt(0) - 'A';
            colonne = coordonnee.charAt(1) - '1';

            if (ligne >= 0 && ligne < 4 && colonne >= 0 && colonne < 4 && grille[ligne][colonne] == 0) {
                positionValide = true;
            } else {
                System.out.println("Position invalide, veuillez réessayer.");
            }
        } catch (Exception e) {
            System.out.println("Erreur de format, veuillez entrer une position valide (ex: A1, B3).");
        }
    }

    grille[ligne][colonne] = couleur;
    nbreVides--;
    return new int[]{ligne, colonne};
}

public int[] placeCouleurIA(int couleur) {
    int[] coordonnees = choixCoordonneesIA(couleur);
    grille[coordonnees[0]][coordonnees[1]] = couleur;
    nbreVides--;

    System.out.println(pseudo + " place un pion " + couleurEnLettres(couleur) + " en " + (char) ('A' + coordonnees[0]) + (coordonnees[1] + 1));

    if (verifAligne(coordonnees)) {
        recoitJeton(couleur); // Réception du jeton si alignement
    } else if (nbreVides == 0) { // Vérifie si la grille est pleine
        System.out.println("La grille est remplie sans alignement. Elle va être réinitialisée.");
        videGrille(); // Réinitialisation de la grille
    }
    
    return coordonnees;
}

public void tourJeu(Joueur adversaire) {
    System.out.println("C'est au tour de " + pseudo);
    System.out.println(" ");
    System.out.println("Nombre de grilles utilisées : " + compteurGrillesUtilisees);
    System.out.println("Jetons Bleu : " + (statsJetons[1]) + " - Jetons Vert : " + (statsJetons[2]) + " - Jetons Jaune : " + (statsJetons[3]) + " - Jetons Rouge : " + (statsJetons[4]));
    int[] couleurs = tirageCouleur();
    System.out.println(" ");
    System.out.println("Les deux pions tirés sont de couleur " + couleurEnLettres(couleurs[0]) + " et " + couleurEnLettres(couleurs[1]));
    int couleurChoisie = choixCouleur(couleurs);

    System.out.println("Vous avez choisi la couleur : " + couleurEnLettres(couleurChoisie));
    int[] coordonnees = placeCouleur(couleurChoisie);

    if (coordonnees == null) {
        // Si les coordonnees sont null, cela signifie que le joueur a choisi d'arrêter le jeu.
        return; // Sortir de la méthode
    }

    if (verifAligne(coordonnees)) {
        recoitJeton(couleurChoisie);
    } else if (nbreVides == 0) { // Vérifie si la grille est pleine
        System.out.println("La grille est remplie sans alignement. Elle va être réinitialisée.");
        videGrille();
    }

    afficherGrille();
}


public void recoitJeton(int couleur) {
    jetonsGagnes[couleur] = true;  // Marque le jeton comme gagné
    statsJetons[couleur]++;
    System.out.println(pseudo + " reçoit un jeton de couleur " + couleurEnLettres(couleur) + " suite à un alignement.");
    videGrille();   // Réinitialise la grille pour repartir à zéro
}

public boolean verifAligne(int[] coordonnees) {
    int ligne = coordonnees[0];
    int colonne = coordonnees[1];
    int couleur = grille[ligne][colonne];
    boolean aligne = false;

    // Vérifie les alignements horizontaux et verticaux
    if (grille[ligne][0] == couleur && grille[ligne][1] == couleur && grille[ligne][2] == couleur && grille[ligne][3] == couleur) {
        System.out.println("Alignement horizontal détecté sur la ligne " + (ligne + 1));
        aligne = true;
    }
    if (grille[0][colonne] == couleur && grille[1][colonne] == couleur && grille[2][colonne] == couleur && grille[3][colonne] == couleur) {
        System.out.println("Alignement vertical détecté sur la colonne " + (colonne + 1));
        aligne = true;
    }

    // Vérifie l'alignement diagonal principal
    if (grille[0][0] == couleur && grille[1][1] == couleur && grille[2][2] == couleur && grille[3][3] == couleur) {
        System.out.println("Alignement diagonal détecté (de A1 à D4)");
        aligne = true;
    }

    // Vérifie l'alignement diagonal secondaire
    if (grille[3][0] == couleur && grille[2][1] == couleur && grille[1][2] == couleur && grille[0][3] == couleur) {
        System.out.println("Alignement diagonal secondaire détecté (de D1 à A4)");
        aligne = true;
    }

    return aligne;
}

    
    public boolean verifGagne() {
        for (boolean jeton : jetonsGagnes) {
            if (!jeton) return false;
        }
        return true;
    }
    
    public Joueur verifTermine(Joueur adversaire) {
    // Vérifie si le joueur courant a gagné
    if (this.verifGagne()) {
        return this;  // Retourne le joueur actuel comme vainqueur
    }

    // Vérifie si l'adversaire a gagné
    if (adversaire.verifGagne()) {
        return adversaire;  // Retourne l'adversaire comme vainqueur
    }

    // Vérifie si la grille est pleine (tous les emplacements sont occupés)
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            if (grille[i][j] == 0) { // 0 indique une case vide
                return null; // La partie n'est pas terminée
            }
        }
    }

    return null; // Retourne null s'il n'y a pas de gagnant et que la grille est pleine
}

  
private int compteurGrillesUtilisees = 0; // Compteur de grilles utilisées

public void videGrille() {
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            grille[i][j] = 0;
        }
    }
    nbreVides = 16;  // Réinitialise le nombre de cases vides
    compteurGrillesUtilisees++;  // Incrémente le compteur de grilles utilisées
}

}
