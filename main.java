// Bryan THIRIMANNA & Thomas MOROT-GAUDRY
// 2A - Semestre 3
// Projet 1 Java
// Octobre - Novembre 2024

package tp1;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenue dans le jeu d'alignement !");
        boolean quitter = false;

        while (!quitter) {
            afficherMenuPrincipal();
            int choix = saisirEntier(sc, "Erreur, veuillez saisir un choix valide.");

            switch (choix) {
                case 1 -> {
                    boolean retour = false;
                    while (!retour) {
                        afficherMenuModeDeJeu();
                        int mode = saisirEntier(sc, "Erreur, veuillez saisir un choix valide.");

                        switch (mode) {
                            case 1, 2 -> {
                                System.out.print("Entrez le pseudo du joueur 1 : ");
                                String pseudo1 = sc.next();
                                Joueur joueur1 = new Joueur(pseudo1);
                                
                                Joueur joueur2 = (mode == 2) ? new Joueur("Ordinateur") : new Joueur(saisirPseudo(sc));
                                jouerPartie(joueur1, joueur2, mode == 2);
                                retour = true;
                            }
                            case 3 -> retour = true; // Retour au menu principal
                            default -> System.out.println("Erreur, veuillez saisir un choix valide.");
                        }
                    }
                }
                case 2 -> afficherCredits(); // Affichage de texte
                case 3 -> afficherReglesDuJeu(); // Affichage de texte
                case 4 -> {
                    quitter = true;
                    System.out.println("A bientot !"); // Ferme le jeu
                }
                default -> System.out.println("Erreur, veuillez saisir un choix valide.");
            }
        }
        sc.close();
    }

    public static void afficherMenuPrincipal() {
        System.out.println("===============TP Java Semestre 3================");
        System.out.println("[1] Nouvelle Partie");
        System.out.println("[2] A propos");
        System.out.println("[3] Regles du jeu");
        System.out.println("[4] Quitter");
        System.out.println(" ");

    }

    public static void afficherMenuModeDeJeu() {
        System.out.println("========== Choisissez le mode de jeu ==========");
        System.out.println("[1] Mode 1vs1");
        System.out.println("[2] Mode humain vs IA");
        System.out.println("[3] Retour");
        System.out.println(" ");

    }

    public static void afficherCredits() {
        System.out.println("=============A propos=============");
        System.out.println("Par deux etudiants de l'EPF : Thomas MOROT-GAUDRY et Bryan THIRIMANNA");
        System.out.println("Projet Java - Semestre 3 (IDE Netbeans)");
        System.out.println(" ");

    }

    public static void afficherReglesDuJeu() {
        System.out.println("=============Regles du jeu :=============");
        System.out.println("Le jeu d'alignement oppose deux joueurs, chacun recevant une grille vide de 4*4.");
        System.out.println("A chaque tour, deux pions de couleurs differentes sont tires au sort.");
        System.out.println("Le but est d'aligner quatre pions de la meme couleur pour gagner un jeton de cette couleur.");
        System.out.println("La grille d'un joueurs se renitialise pour chaque alignement effectues ou si la grille du joueur est remplie sans alignements");
        System.out.println("Le premier a collecter un jeton de chaque couleur gagne la partie.");
        System.out.println(" ");

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
                sc.next();
            }
        }
        return entier;
    }

    public static String saisirPseudo(Scanner sc) {
        System.out.print("Entrez le pseudo du joueur 2 : ");
        System.out.println(" ");
        return sc.next();
    }

    public static void jouerPartie(Joueur joueur1, Joueur joueur2, boolean modeIA) {
        System.out.println("Début de la partie !");
        System.out.println(" ");
        System.out.println("Grille du joueur 1 ");
        joueur1.afficherGrille();
        System.out.println(" ");
        System.out.println("Grille du joueur 2 ");
        joueur2.afficherGrille();

        boolean partieTerminee = false;
        while (!partieTerminee) {
            joueur1.tourJeu(joueur2);
            if (joueur1.verifGagne()) {
                System.out.println(joueur1.getPseudo() + " a gagne la partie !");
                System.out.println(" ");
                partieTerminee = true;
                break;
            }

            if (modeIA) {
                joueur2.tourJeuIA(joueur1);
            } else {
                joueur2.tourJeu(joueur1);
            }

            if (joueur2.verifGagne()) {
                System.out.println(joueur2.getPseudo() + " a gagne la partie !");
                System.out.println(" ");
                partieTerminee = true;
            }
        }
    }
}
