package tp1;

import java.util.Scanner;

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
            pseudo2 = sc.next(); // Demander le pseudo du joueur 2
        }

        Joueur joueur1 = new Joueur(pseudo1);
        Joueur joueur2 = new Joueur(pseudo2);

        // Afficher les grilles initiales
        System.out.println("\nGrille de " + joueur1.getPseudo() + ":");
        joueur1.afficherGrille();
        System.out.println("\nGrille de " + joueur2.getPseudo() + ":");
        joueur2.afficherGrille();

        boolean partieTerminee = false;

        while (!partieTerminee) {
            // Tour du joueur 1
            joueur1.tourJeu(joueur2);
            if (joueur1.verifGagne()) {
                System.out.println(joueur1.getPseudo() + " a gagné la partie !");
                partieTerminee = true;
                break;
            }

            // Tour du joueur 2
            joueur2.tourJeu(joueur1);
            if (joueur2.verifGagne()) {
                System.out.println(joueur2.getPseudo() + " a gagné la partie !");
                partieTerminee = true;
            }
        }

        // Affichage final des grilles
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
