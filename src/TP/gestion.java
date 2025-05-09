package TP;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Ici, on gère la gestion des données
public class gestion{
 void gererUtilisateurs(Repository<Utilisateur> repo, Scanner scanner) {
    int choix;
    do {
    	//ce qui s'affiche sur la console
        System.out.println("\n=== Gestion des Utilisateurs ===");
        System.out.println("1. Ajouter un utilisateur");
        System.out.println("2. Lister tous les utilisateurs");
        System.out.println("3. Modifier un utilisateur");
        System.out.println("4. Supprimer un utilisateur");
        System.out.println("0. Retour");
        System.out.print("Choix: ");
        
        choix = scanner.nextInt();
        scanner.nextLine(); 
        
        switch (choix) {
            case 1:
                // Ajout d'un utilisateur
                Utilisateur user = new Utilisateur();
                System.out.print("Nom: ");
                user.setNom(scanner.nextLine());
                System.out.print("Email: ");
                user.setEmail(scanner.nextLine());
                System.out.print("Téléphone: ");
                user.setTelephone(scanner.nextLine());
                
                repo.create(user);
                break;
                
            case 2:
                // Liste de tous les utilisateurs
                List<Utilisateur> users = repo.readAll();
                for (Utilisateur u : users) {
                    System.out.println(u);
                }
                break;
                
            case 3:
                System.out.print("ID de l'utilisateur à modifier: ");
                int idModif = scanner.nextInt();
                scanner.nextLine();
                Utilisateur userModif = repo.findById(idModif);
                
                if (userModif != null) {
                    System.out.print("Nouveau nom (" + userModif.getNom() + "): ");
                    String nouveauNom = scanner.nextLine();
                    if (!nouveauNom.isEmpty()) userModif.setNom(nouveauNom);
                    
                    System.out.print("Nouvel email (" + userModif.getEmail() + "): ");
                    String nouvelEmail = scanner.nextLine();
                    if (!nouvelEmail.isEmpty()) userModif.setEmail(nouvelEmail);
                    
                    System.out.print("Nouveau téléphone (" + userModif.getTelephone() + "): ");
                    String nouveauTel = scanner.nextLine();
                    if (!nouveauTel.isEmpty()) userModif.setTelephone(nouveauTel);
                    
                    repo.update(userModif);
                } else {
                    System.out.println("Utilisateur non trouvé!");
                }
                break;
                
            case 4:
                // Suppression d'un utilisateur
                System.out.print("ID de l'utilisateur à supprimer: ");
                int idSuppr = scanner.nextInt();
                scanner.nextLine();
                repo.delete(idSuppr);
                break;
                
            case 0:
                break;
                
            default:
                System.out.println("Choix invalide!");
        }
    } while (choix != 0);
}

 	void gererPrets(PretManagment pretManager, Repository<Livre> livreRepo,  Repository<Utilisateur> userRepo, Scanner scanner) {
    int choix;
    do {
        System.out.println("\n=== Gestion des Prêts ===");
        System.out.println("1. Emprunter un livre");
        System.out.println("2. Rendre un livre");
        System.out.println("3. Lister tous les prêts");
        System.out.println("0. Retour");
        System.out.print("Choix: ");
        
        choix = scanner.nextInt();
        scanner.nextLine();
        
        switch (choix) {
            case 1:
                System.out.print("ID de l'utilisateur: ");
                int userId = scanner.nextInt();
                System.out.print("ID du livre: ");
                int livreId = scanner.nextInt();
                scanner.nextLine();
                
                if (userRepo.findById(userId) == null || livreRepo.findById(livreId) == null) {
                    System.out.println("Utilisateur ou livre non trouvé!");
                    break;
                }
                
                pretManager.emprunter(userId, livreId, new Date());
                break;
                
            case 2:
                System.out.print("ID du prêt à retourner: ");
                int pretId = scanner.nextInt();
                scanner.nextLine();
                
                pretManager.rendre(pretId, new Date());
                break;
                
            case 3:
            	List<Map<String, String>> prets = pretManager.listerPrets();
                for (Map<String, String> pret : prets) {
                    System.out.println("ID: " + pret.get("id"));
                    System.out.println("Utilisateur: " + pret.get("utilisateur"));
                    System.out.println("Livre: " + pret.get("livre"));
                    System.out.println("Date emprunt: " + pret.get("date_emprunt"));
                    System.out.println("Date retour: " + pret.get("date_retour"));
                    System.out.println("-------------------");
                }
                break;
                
            case 0:
                break;
                
            default:
                System.out.println("Choix invalide!");
        }
    } while (choix != 0);
}
void gererLivres(Repository<Livre> repo, Scanner scanner) {
 int choix;
 do {
     System.out.println("\n=== Gestion des Livres ===");
     System.out.println("1. Ajouter un livre");
     System.out.println("2. Lister tous les livres");
     System.out.println("3. Modifier un livre");
     System.out.println("4. Supprimer un livre");
     System.out.println("5. Rechercher des livres");
     System.out.println("0. Retour");
     System.out.print("Choix: ");
     
     choix = scanner.nextInt();
     scanner.nextLine();
     
     switch (choix) {
         case 1:
             // Ajout d'un livre
             Livre livre = new Livre();
             System.out.print("Titre: ");
             livre.setTitre(scanner.nextLine());
             System.out.print("Auteur: ");
             livre.setAuteur(scanner.nextLine());
             System.out.print("Année: ");
             livre.setAnnee(scanner.nextInt());
             scanner.nextLine();
             System.out.print("Genre: ");
             livre.setGenre(scanner.nextLine());
             
             repo.create(livre);
             break;
             
         case 2:
             List<Livre> livres = repo.readAll();
             for (Livre l : livres) {
                 System.out.println(l);
             }
             break;
             
         case 3:
           
             System.out.print("ID du livre à modifier: ");
             int idModif = scanner.nextInt();
             scanner.nextLine();
             Livre livreModif = repo.findById(idModif);
             
             if (livreModif != null) {
                 System.out.print("Nouveau titre (" + livreModif.getTitre() + "): ");
                 String nouveauTitre = scanner.nextLine();
                 if (!nouveauTitre.isEmpty()) livreModif.setTitre(nouveauTitre);
                 
                 System.out.print("Nouvel auteur (" + livreModif.getAuteur() + "): ");
                 String nouvelAuteur = scanner.nextLine();
                 if (!nouvelAuteur.isEmpty()) livreModif.setAuteur(nouvelAuteur);
                 
                 System.out.print("Nouvelle année (" + livreModif.getAnnee() + "): ");
                 String nouvelleAnneeStr = scanner.nextLine();
                 if (!nouvelleAnneeStr.isEmpty()) {
                     livreModif.setAnnee(Integer.parseInt(nouvelleAnneeStr));
                 }
                 
                 System.out.print("Nouveau genre (" + livreModif.getGenre() + "): ");
                 String nouveauGenre = scanner.nextLine();
                 if (!nouveauGenre.isEmpty()) livreModif.setGenre(nouveauGenre);
                 
                 repo.update(livreModif);
             } else {
                 System.out.println("Livre non trouvé!");
             }
             break;
             
         case 4:
             System.out.print("ID du livre à supprimer: ");
             int idSuppr = scanner.nextInt();
             scanner.nextLine();
             repo.delete(idSuppr);
             break;
             
         case 5:
             System.out.println("Rechercher par:");
             System.out.println("1. Auteur");
             System.out.println("2. Genre");
             System.out.println("3. Titre");
             System.out.print("Choix: ");
             int critereRecherche = scanner.nextInt();
             scanner.nextLine();
             
             String critere = "";
             switch (critereRecherche) {
                 case 1: critere = "auteur"; break;
                 case 2: critere = "genre"; break;
                 case 3: critere = "titre"; break;
                 default: 
                     System.out.println("Choix invalide!");
                     continue;
             }
             
             System.out.print("Terme de recherche: ");
             String terme = scanner.nextLine();
             
             List<Livre> resultats = repo.searchLivres(critere, terme);
             if (resultats.isEmpty()) {
                 System.out.println("Aucun résultat trouvé.");
             } else {
                 for (Livre l : resultats) {
                     System.out.println(l);
                 }
             }
             break;
             
         case 0:
             break;
             
         default:
             System.out.println("Choix invalide!");
     }
 } while (choix != 0);
}
}