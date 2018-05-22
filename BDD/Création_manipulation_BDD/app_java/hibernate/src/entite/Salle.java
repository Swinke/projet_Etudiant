package entite;
// Generated 16 avr. 2018 17:18:23 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Salle generated by hbm2java
 */
public class Salle implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salle_generator")
    @SequenceGenerator(name = "salle_generator", sequenceName = "SEQUENCE_SALLE")
    @Column(name = "idsalle", updatable = false, nullable = false, unique = true)

    private BigDecimal idsalle;

    private String nom;
    private Set specialisationsalles = new HashSet(0);

    public Salle() {
    }

    public Salle(String nom) {
        this.nom = nom;
    }

    public Salle(BigDecimal idsalle, String nom) {
        this.idsalle = idsalle;
        this.nom = nom;
    }

    public Salle(BigDecimal idsalle, String nom, Set specialisationsalles) {
        this.idsalle = idsalle;
        this.nom = nom;
        this.specialisationsalles = specialisationsalles;
    }

    public BigDecimal getIdsalle() {
        return this.idsalle;
    }

    public void setIdsalle(BigDecimal idsalle) {
        this.idsalle = idsalle;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set getSpecialisationsalles() {
        return this.specialisationsalles;
    }

    public void setSpecialisationsalles(Set specialisationsalles) {
        this.specialisationsalles = specialisationsalles;
    }

}
