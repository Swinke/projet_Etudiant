/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import dao.*;
import entite.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Hippolyte Damand, DanielLesieur
 */
public class Hibernate {

    static void insertionBDD() {
        Docteur doc = new Docteur();
        Salle s = new Salle();
        Typechirurgie type1 = new Typechirurgie();
        Specialisationsalle specsalle = new Specialisationsalle();
        Ordonnance ordc = new Ordonnance();
        Ordonnance ordm = new Ordonnance();
        Ordonnancechirurgie ordchir = new Ordonnancechirurgie();
        Ordonnancemedicaments ordmed = new Ordonnancemedicaments();
        Medicament med = new Medicament();
        Chirurgie chir = new Chirurgie();
        Dossierpatient dp = new Dossierpatient();

        try {
            doc = new Docteur("FOLE", "Folamour", "Étienne", "Etudiant", new BigDecimal(100));
            DocteurDAO.insert(doc);
        } catch (Throwable se) {
            System.out.println(se);
        }

        try {
            doc = new Docteur("HEBF", "Hébert", "François", "Interne", new BigDecimal(0));
            DocteurDAO.insert(doc);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            s = new Salle("B-52");
            SalleDAO.insert(s);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            type1 = new Typechirurgie("micro-chirurgie");
            TypechirurgieDAO.insert(type1);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            Categories cat = new Categories("codéine");
            CategoriesDAO.insert(cat);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            med = new Medicament("xunofene", new BigDecimal(5));
            MedicamentDAO.insert(med);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            Specialite spec = new Specialite("MICRO", "micro-chirurgie");
            SpecialiteDAO.insert(spec);
            doc.setSpecialite(spec);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            specsalle = new Specialisationsalle(new SpecialisationsalleId(new BigDecimal(6), new BigDecimal(5)), s, type1, new Date());
            SpecialisationsalleDAO.insert(specsalle);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            dp = new Dossierpatient(doc, "Salavdor", "Henri", 'M', "123", new Date(), new Date(), new HashSet());
            DossierpatientDAO.insert(dp);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            ordm = new Ordonnance("Prendre avec de la nourriture", "Medicaments", new Date());
            OrdonnanceDAO.insert(ordm);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            ordc = new Ordonnance("Chirurgie", new Date());
            OrdonnanceDAO.insert(ordc);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            chir = new Chirurgie(specsalle, new Date());
            ChirurgieDAO.insert(chir);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            ordchir = new Ordonnancechirurgie(new OrdonnancechirurgieId(new BigDecimal(8), new BigDecimal(1)), ordc, chir, new BigDecimal(3));
            OrdonnancechirurgieDAO.insert(ordchir);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            ordmed = new Ordonnancemedicaments(new OrdonnancemedicamentsId(new BigDecimal(1), new BigDecimal(4)), ordm, med, new BigDecimal(4));
            OrdonnancemedicamentsDAO.insert(ordmed);
        } catch (Throwable se) {
            System.out.println(se);
        }
        try {
            Consultation cons = new Consultation(new ConsultationId("FOLE", new BigDecimal(6), new Date()), doc, dp, "bon");
            ConsultationDAO.insert(cons);
        } catch (Throwable se) {
            System.out.println(se);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        HibernateUtil.openConn(args);
        insertionBDD();

        /**
         * Requête *
         */
        final Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.println("--- Cas d'utilisation ---");
        System.out.println("Cas 1 : Le nombre de chirurgie par salle");

        Query query = session.createQuery("select count(idchir), specialisationsalle.salle.nom "
                + "from Chirurgie "
                + "group by specialisationsalle.salle.nom "
                + "order by count(idchir) desc");
        List<Object[]> result = query.list();

        for (Object[] line : result) {
            System.out.println("Salle " + line[1] + " : " + line[0]);
        }

        System.out.println("\nCas 2 : Le nombre de consultations par spécialité");

        query = session.createQuery("select count(docteur), docteur.specialite.titre "
                + "from Consultation "
                + "group by docteur.specialite.titre "
                + "order by count(docteur) desc");
        result = query.list();

        for (Object[] line : result) {
            System.out.println(line[1] + " : " + line[0]);
        }

        System.out.println("\nCas 3 : Le nombre de consultations par docteur");
        String sql = "select count(docteur), docteur.prenomm, docteur.nomm "
                + "from Consultation "
                + "group by  docteur.prenomm, docteur.nomm order by count(docteur) desc";
        query = session.createQuery(sql);
        result = query.list();

        for (Object[] line : result) {
            System.out.println(line[1] + " " + line[2] + " : " + line[0]);
        }

        System.out.println("\nCas 4 : Le nombre de médicaments par docteur");
        sql = "select count(o.id.idmed), c.docteur.prenomm||' '||c.docteur.nomm "
                + "from Consultation c, Ordonnancemedicaments o "
                + "where c.ordonnance.numord = o.id.numord "
                + "group by c.docteur.prenomm||' '||c.docteur.nomm order by count(o.id.idmed) desc";
        query = session.createQuery(sql);
        result = query.list();

        for (Object[] line : result) {
            System.out.println(line[1] + " : " + line[0]);
        }

        HibernateUtil.getSessionFactory().close();
    }
}
