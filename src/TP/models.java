package TP;
//ici les modèles de données
 class Livre {
	    private int id;
	    private String titre;
	    private String auteur;
	    private int annee;
	    private String genre;
	    
	    // Constructeurs
	    public Livre() {}
	    
	    public Livre(int id, String titre, String auteur, int annee, String genre) {
	        this.id = id;
	        this.titre = titre;
	        this.auteur = auteur;
	        this.annee = annee;
	        this.genre = genre;
	    }
	    
	    // Getters et Setters
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }
	    public String getTitre() { return titre; }
	    public void setTitre(String titre) { this.titre = titre; }
	    public String getAuteur() { return auteur; }
	    public void setAuteur(String auteur) { this.auteur = auteur; }
	    public int getAnnee() { return annee; }
	    public void setAnnee(int annee) { this.annee = annee; }
	    public String getGenre() { return genre; }
	    public void setGenre(String genre) { this.genre = genre; }
	    
	    @Override
	    public String toString() {
	        return "Livre [id=" + id + ", titre=" + titre + ", auteur=" + auteur + 
	               ", annee=" + annee + ", genre=" + genre + "]";
	    }
	}

	class Utilisateur {
	    private int id;
	    private String nom;
	    private String email;
	    private String telephone;

	    public Utilisateur() {}
	    
	    public Utilisateur(int id, String nom, String email, String telephone) {
	        this.id = id;
	        this.nom = nom;
	        this.email = email;
	        this.telephone = telephone;
	    }
	    
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }
	    public String getNom() { return nom; }
	    public void setNom(String nom) { this.nom = nom; }
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }
	    public String getTelephone() { return telephone; }
	    public void setTelephone(String telephone) { this.telephone = telephone; }
	    
	    @Override
	    public String toString() {
	        return "Utilisateur [id=" + id + ", nom=" + nom + ", email=" + email + 
	               ", telephone=" + telephone + "]";
	    }


	}

