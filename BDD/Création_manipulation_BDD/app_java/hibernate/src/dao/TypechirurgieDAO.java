/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.DocteurDAO.session;
import entite.Typechirurgie;
import org.hibernate.Transaction;

/**
 *
 * @author Mathi
 */
public class TypechirurgieDAO {

    public static void insert(Typechirurgie r) {
        session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        session.save(r);
        tx.commit();
        session.close();

    }
}
