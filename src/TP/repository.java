package TP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class Repository<T> {
    private Class<T> type;
    private Connection connection;
    
    // Constructeur
    public Repository(Class<T> type) throws ClassNotFoundException {
        this.type = type;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bibliotheque", 
                "root", 
                ""      
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour créer un objet dans la base de données
    public void create(T obj) {
        try {
            if (type == Livre.class) {
                Livre livre = (Livre) obj;
                String sql = "INSERT INTO livres (titre, auteur, annee, genre) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, livre.getTitre());
                stmt.setString(2, livre.getAuteur());
                stmt.setInt(3, livre.getAnnee());
                stmt.setString(4, livre.getGenre());
                stmt.executeUpdate();
                System.out.println("Livre ajouté avec succès!");
            } else if (type == Utilisateur.class) {
                Utilisateur user = (Utilisateur) obj;
                String sql = "INSERT INTO utilisateurs (nom, email, telephone) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, user.getNom());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getTelephone());
                stmt.executeUpdate();
                System.out.println("Utilisateur ajouté avec succès!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour lire tous les objets de la table
    @SuppressWarnings("unchecked") //J'utilise ça car je veux supprimer les warnings inutiles
	public List<T> readAll() {
        List<T> liste = new ArrayList<>();
        try {
            if (type == Livre.class) {
                String sql = "SELECT * FROM livres";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    Livre livre = new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getInt("annee"),
                        rs.getString("genre")
                    );
                    liste.add((T) livre);
                }
            } else if (type == Utilisateur.class) {
                String sql = "SELECT * FROM utilisateurs";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    Utilisateur user = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                    );
                    liste.add((T) user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
    
    // Méthode pour mettre à jour un objet
    public void update(T obj) {
        try {
            if (type == Livre.class) {
                Livre livre = (Livre) obj;
                String sql = "UPDATE livres SET titre=?, auteur=?, annee=?, genre=? WHERE id=?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, livre.getTitre());
                stmt.setString(2, livre.getAuteur());
                stmt.setInt(3, livre.getAnnee());
                stmt.setString(4, livre.getGenre());
                stmt.setInt(5, livre.getId());
                stmt.executeUpdate();
                System.out.println("Livre mis à jour avec succès!");
            } else if (type == Utilisateur.class) {
                Utilisateur user = (Utilisateur) obj;
                String sql = "UPDATE utilisateurs SET nom=?, email=?, telephone=? WHERE id=?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, user.getNom());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getTelephone());
                stmt.setInt(4, user.getId());
                stmt.executeUpdate();
                System.out.println("Utilisateur mis à jour avec succès!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour supprimer un objet
    public void delete(int id) {
        try {
            if (type == Livre.class) {
                String sql = "DELETE FROM livres WHERE id=?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Livre supprimé avec succès!");
            } else if (type == Utilisateur.class) {
                String sql = "DELETE FROM utilisateurs WHERE id=?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Utilisateur supprimé avec succès!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour trouver un objet par son ID
    @SuppressWarnings("unchecked")
	public T findById(int id) {
        try {
            if (type == Livre.class) {
                String sql = "SELECT * FROM livres WHERE id=?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    return (T) new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getInt("annee"),
                        rs.getString("genre")
                    );
                }
            } else if (type == Utilisateur.class) {
                String sql = "SELECT * FROM utilisateurs WHERE id=?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    return (T) new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Méthode pour rechercher des livres par critères
    public List<Livre> searchLivres(String critere, String valeur) {
        List<Livre> resultats = new ArrayList<>();
        try {
            String sql = "SELECT * FROM livres WHERE " + critere + " LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + valeur + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Livre livre = new Livre(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getInt("annee"),
                    rs.getString("genre")
                );
                resultats.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultats;
    }
}