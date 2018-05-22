/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entite.Medicament;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mathi
 */
public class MedicamentDAO {

    static Session session = null;

    public static void insert(Medicament r) {
        session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        session.save(r);
        tx.commit();
        session.close();

    }

}
