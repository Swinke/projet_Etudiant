package entite;
// Generated 16 avr. 2018 17:18:23 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Chirurgie generated by hbm2java
 */
public class Chirurgie implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salle_generator")
    @SequenceGenerator(name = "salle_generator", sequenceName = "SEQUENCE_CHIRURGIE")
    @Column(name = "idchir", updatable = false, nullable = false, unique = true)

    private BigDecimal idchir;
    private Specialisationsalle specialisationsalle;
    private Date datechirurgie;
    private BigDecimal heuredebut;
    private BigDecimal heurefin;
    private Set ordonnancechirurgies = new HashSet(0);

    public Chirurgie() {
    }

    public Chirurgie(BigDecimal idchir, Specialisationsalle specialisationsalle, Date datechirurgie) {
        this.idchir = idchir;
        this.specialisationsalle = specialisationsalle;
        this.datechirurgie = datechirurgie;
    }

    public Chirurgie(Specialisationsalle specialisationsalle, Date datechirurgie) {
        this.specialisationsalle = specialisationsalle;
        this.datechirurgie = datechirurgie;
    }

    public Chirurgie(BigDecimal idchir, Specialisationsalle specialisationsalle, Date datechirurgie, BigDecimal heuredebut, BigDecimal heurefin, Set ordonnancechirurgies) {
        this.idchir = idchir;
        this.specialisationsalle = specialisationsalle;
        this.datechirurgie = datechirurgie;
        this.heuredebut = heuredebut;
        this.heurefin = heurefin;
        this.ordonnancechirurgies = ordonnancechirurgies;
    }

    public BigDecimal getIdchir() {
        return this.idchir;
    }

    public void setIdchir(BigDecimal idchir) {
        this.idchir = idchir;
    }

    public Specialisationsalle getSpecialisationsalle() {
        return this.specialisationsalle;
    }

    public void setSpecialisationsalle(Specialisationsalle specialisationsalle) {
        this.specialisationsalle = specialisationsalle;
    }

    public Date getDatechirurgie() {
        return this.datechirurgie;
    }

    public void setDatechirurgie(Date datechirurgie) {
        this.datechirurgie = datechirurgie;
    }

    public BigDecimal getHeuredebut() {
        return this.heuredebut;
    }

    public void setHeuredebut(BigDecimal heuredebut) {
        this.heuredebut = heuredebut;
    }

    public BigDecimal getHeurefin() {
        return this.heurefin;
    }

    public void setHeurefin(BigDecimal heurefin) {
        this.heurefin = heurefin;
    }

    public Set getOrdonnancechirurgies() {
        return this.ordonnancechirurgies;
    }

    public void setOrdonnancechirurgies(Set ordonnancechirurgies) {
        this.ordonnancechirurgies = ordonnancechirurgies;
    }

}
