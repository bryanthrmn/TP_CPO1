# Jeu d'Alignement en Java

**Auteurs**: Bryan Thirimanna & Thomas Morot-Gaudry  
**École**: EPF  
**Projet**: Java - Semestre 3  
**IDE**: NetBeans

## Introduction
Ce projet consiste à créer un jeu d'alignement en Java. Les joueurs jouent sur une grille de 4x4, en mode solo contre une IA ou en mode 1v1. Chaque joueur reçoit des pions de quatre couleurs différentes : vert, rouge, jaune, et bleu. L'objectif est d'aligner quatre pions de la même couleur pour gagner un jeton de cette couleur. Le premier joueur à collecter un jeton de chaque couleur remporte la partie.

## Fonctionnalités

- **Interface textuelle** : menus et contrôles en console sans bibliothèque graphique.
- **Mode de jeu IA** : L'IA priorise les couleurs non acquises et cherche les alignements.
- **Mécanique de jeu** : alignement de quatre pions (horizontalement, verticalement ou en diagonale) pour gagner un jeton.
- **Commandes** : `quit` ou `stop` pour quitter instantanément le jeu.
- **Statistiques** : affichage de statistiques de jeu à la fin de la partie.

## Structure du Programme

### Main.java
- Initialise le jeu et gère le menu principal et les sous-menus.
- Gère les différents modes de jeu et les boucles de jeu pour chaque tour.

### Joueur.java
- Gère les actions du joueur et de l'IA (tirage de couleurs, placement des pions, vérification des alignements).
- Implémente la logique de l'IA en priorisant les alignements stratégiques.

## Lancement du Jeu
Pour lancer le jeu, exécutez `Main.java` depuis votre IDE NetBeans. Un menu principal vous invite à choisir entre une nouvelle partie, afficher les crédits, voir les règles du jeu, ou quitter.

### Menu Principal
1. **Nouvelle Partie** : commence une nouvelle partie en mode 1v1 ou IA.
2. **À Propos** : affiche les informations sur les auteurs du projet.
3. **Règles du Jeu** : explique les règles et objectifs.
4. **Quitter** : ferme le jeu.

### Exemple de Partie
Lors du lancement, le joueur doit choisir un mode de jeu. En mode 1v1, deux pseudonymes sont demandés, et en mode IA, un seul pseudo est nécessaire. Les joueurs choisissent des couleurs aléatoires, placent leurs pions avec des coordonnées (XY) et tentent de réaliser des alignements.

**Commande spéciale**: Si `quit` ou `stop` est entré pendant le jeu, celui-ci s'interrompt immédiatement.

## Conclusion
Ce projet nous a permis d'appliquer des concepts Java et de concevoir une IA simple pour un jeu d'alignement. Bien que l'IA soit encore facile à battre, elle introduit une dynamique de jeu stratégique. Une prochaine étape pourrait être l'ajout d'une commande `save` pour sauvegarder les parties en cours.

### Liens
- **Projet sur GitHub** : [TP_CPO1](https://github.com/bryanthrmn/TP_CPO1)
- **Projet Python inspirant** : [Gomoku (puissance 5)](https://github.com/bryanthrmn/Gomoku)
