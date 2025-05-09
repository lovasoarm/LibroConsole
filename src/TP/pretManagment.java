package TP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PretManagment {
    private Connection connection;
    
    public PretManagment() throws ClassNotFoundException {
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
    
    // Emprunter un livre
    public void emprunter(int userId, int livreId, Date dateEmprunt) {
        try {
            String sql = "INSERT INTO prets (utilisateur_id, livre_id, date_emprunt) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, livreId);
            stmt.setDate(3, new java.sql.Date(dateEmprunt.getTime()));
            stmt.executeUpdate();
            System.out.println("Livre emprunté avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rendre(int pretId, Date dateRetour) {
        try {
            String sql = "UPDATE prets SET date_retour=? WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(dateRetour.getTime()));
            stmt.setInt(2, pretId);
            stmt.executeUpdate();

            verifierRetard(pretId, dateRetour);
            
            System.out.println("Livre rendu avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void verifierRetard(int pretId, Date dateRetour) {
        try {
            String sql = "SELECT date_emprunt FROM prets WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pretId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Date dateEmprunt = rs.getDate("date_emprunt");
                long diff = dateRetour.getTime() - dateEmprunt.getTime();
                long jours = diff / (1000 * 60 * 60 * 24);
                
                if (jours > 14) { // Supposons que la durée max est 14 jours
                    long joursRetard = jours - 14;
                    double penalite = joursRetard * 0.50; // 0.50€ par jour de retard (estimation)
                    System.out.println("Retard de " + joursRetard + " jours. Pénalité: " + penalite + "€");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Liste des prêts
    public List<Map<String, String>> listerPrets() {
        List<Map<String, String>> prets = new ArrayList<>();
        try {
            String sql = "SELECT p.id, u.nom as utilisateur, l.titre as livre, p.date_emprunt, p.date_retour " +
                         "FROM prets p " +
                         "JOIN utilisateurs u ON p.utilisateur_id = u.id " +
                         "JOIN livres l ON p.livre_id = l.id";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Map<String, String> pret = new HashMap<>();
                pret.put("id", String.valueOf(rs.getInt("id")));
                pret.put("utilisateur", rs.getString("utilisateur"));
                pret.put("livre", rs.getString("livre"));
                pret.put("date_emprunt", rs.getDate("date_emprunt").toString());
                pret.put("date_retour", rs.getDate("date_retour") != null ? rs.getDate("date_retour").toString() : "Non rendu");
                prets.add(pret);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prets;
    }
}
