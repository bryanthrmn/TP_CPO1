package tp1;

import java.util.Scanner;
import java.util.Random;

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
}

class Joueur {
    private final String pseudo;
    private final int[][] grille;
    private final boolean[] jetonsGagnes;
    private int nbreVides = 16;
    private final boolean estOrdinateur;
    private final int[] statsJetons = new int[5]; // Pour les statistiques de fin

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
            System.out.println("Choisissez une couleur : 0 - " + couleurEnLettres(couleurs[0]) + ", 1 - " + couleurEnLettres(couleurs[1]));
            int choix = sc.nextInt();
            return choix == 0 ? couleurs[0] : couleurs[1];
        }
    }

    
private int choixCouleurIA(int[] couleurs) {
    int couleur1 = couleurs[0];
    int couleur2 = couleurs[1];

    if (!jetonsGagnes[couleur1]) {
        return couleur1;  // Priorité : obtenir un jeton manquant
    } else if (!jetonsGagnes[couleur2]) {
        return couleur2;
    } else {
        return couleur1;  // Choix arbitraire si déjà tous les jetons gagnés
    }
}

public void tourJeuIA(Joueur adversaire) {
    System.out.println("C'est au tour de " + pseudo + " (IA).");

    int[] couleurs = tirageCouleur();           // IA reçoit deux couleurs
    int choix = choixCouleurIA(couleurs);       // Choisit une couleur intelligente
    int[] coordonnees = choixCoordonneesIA(choix); // Choisit une position stratégique

    grille[coordonnees[0]][coordonnees[1]] = choix;
    nbreVides--;

    if (verifAligne(coordonnees)) {
        recoitJeton(choix);  // Gagne un jeton et réinitialise la grille
    }

    afficherGrille();
}

private int[] choixCoordonneesIA(int couleur) {
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            if (grille[i][j] == 0) {
                grille[i][j] = couleur;   // Simule le placement
                if (verifAligne(new int[]{i, j})) {
                    grille[i][j] = 0;    // Retourne à l'état initial
                    return new int[]{i, j}; // Choix stratégique
                }
                grille[i][j] = 0;        // Annule le placement simulé
            }
        }
    }

        // Choix aléatoire si aucun alignement direct n'est trouvé

     Random rand = new Random();
    int ligne, colonne;
    do {
        ligne = rand.nextInt(4);
        colonne = rand.nextInt(4);
    } while (grille[ligne][colonne] != 0);
    return new int[]{ligne, colonne};
}
   
    public boolean verifAligne(int[] coordonnees) {
        int ligne = coordonnees[0];
        int colonne = coordonnees[1];
        int couleur = grille[ligne][colonne];
        
        boolean aligne = checkDirection(ligne, colonne, couleur, 1, 0) || 
                         checkDirection(ligne, colonne, couleur, 0, 1) || 
                         checkDirection(ligne, colonne, couleur, 1, 1) || 
                         checkDirection(ligne, colonne, couleur, 1, -1);
        
        return aligne;
    }

    private boolean checkDirection(int ligne, int colonne, int couleur, int deltaLigne, int deltaColonne) {
        int count = 1;
        for (int d = -3; d <= 3; d++) {
            if (d != 0) {
                int l = ligne + d * deltaLigne;
                int c = colonne + d * deltaColonne;
                if (l >= 0 && l < 4 && c >= 0 && c < 4 && grille[l][c] == couleur) {
                    count++;
                    if (count == 4) return true;
                } else {
                    count = 1;
                }
            }
        }
        return false;
    }

public void recoitJeton(int couleur) {
    jetonsGagnes[couleur] = true;  // Marque le jeton comme gagné
    statsJetons[couleur]++;
    System.out.println(pseudo + " reçoit un jeton de couleur " + couleurEnLettres(couleur));
    resetGrille();   // Réinitialise la grille pour repartir à zéro
}

public void resetGrille() {
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            grille[i][j] = 0;
        }
    }
    nbreVides = 16;  // Réinitialise le nombre de cases vides
}
    public boolean verifGagne() {
        for (int i = 1; i <= 4; i++) {
            if (!jetonsGagnes[i]) {
                return false;
            }
        }
        return true;
    }

    public void tourJeu(Joueur adversaire) {
        System.out.println("C'est au tour de " + pseudo + ".");
        int[] couleurs = tirageCouleur();
        int choix = choixCouleur(couleurs);

        Scanner sc = new Scanner(System.in);
        int ligne, colonne;
        boolean positionValide;
        do {
            System.out.println("Entrez une ligne (A-D) et une colonne (1-4) :");
            String coordonnee = sc.next();
            ligne = coordonnee.charAt(0) - 'A';
            colonne = coordonnee.charAt(1) - '1';
            positionValide = ligne >= 0 && ligne < 4 && colonne >= 0 && colonne < 4 && grille[ligne][colonne] == 0;
            if (!positionValide) System.out.println("Position invalide, veuillez réessayer.");
        } while (!positionValide);

        grille[ligne][colonne] = choix;
        nbreVides--;

        if (verifAligne(new int[]{ligne, colonne})) {
            recoitJeton(choix);
        }

        afficherGrille();
    }
}
