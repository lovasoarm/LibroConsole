package TP;

import java.util.*;

// Classe principale de l'application
public class GestionBibliotheque {
    public static void main(String[] args) throws ClassNotFoundException {
        // Instance des classes
        Repository<Livre> livreRepo = new Repository<>(Livre.class);
        Repository<Utilisateur> userRepo = new Repository<>(Utilisateur.class);
        PretManagment pretManager = new PretManagment();
        gestion myGestion = new gestion();

        // Menu principal (choix de l'utilisateur)
        Scanner scanner = new Scanner(System.in);
        int choix;
        
        do {
            System.out.println("\n=== Gestion de Bibliothèque ===");
            System.out.println("1. Gérer les livres");
            System.out.println("2. Gérer les utilisateurs");
            System.out.println("3. Gérer les prêts");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            
            choix = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choix) {
                case 1:
                    myGestion.gererLivres(livreRepo, scanner);
                    break;
                case 2:
                	myGestion.gererUtilisateurs(userRepo, scanner);
                    break;
                case 3:
                    myGestion.gererPrets(pretManager, livreRepo, userRepo, scanner);
                    break;
                case 0:
                    System.out.println("Au revoir!");
                    break;
                default:
                    System.out.println("Choix invalide!");
            }
        } while (choix != 0);
        
        scanner.close();
    }
    

}






