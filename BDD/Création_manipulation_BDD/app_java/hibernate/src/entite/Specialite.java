package entite;
// Generated 16 avr. 2018 17:18:23 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * Specialite generated by hbm2java
 */
public class Specialite implements java.io.Serializable {

    private String code;
    private String titre;
    private String description;
    private Set docteurs = new HashSet(0);

    public Specialite() {
    }

    public Specialite(String code, String titre) {
        this.code = code;
        this.titre = titre;
    }

    public Specialite(String code, String titre, String description, Set docteurs) {
        this.code = code;
        this.titre = titre;
        this.description = description;
        this.docteurs = docteurs;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set getDocteurs() {
        return this.docteurs;
    }

    public void setDocteurs(Set docteurs) {
        this.docteurs = docteurs;
    }

}
