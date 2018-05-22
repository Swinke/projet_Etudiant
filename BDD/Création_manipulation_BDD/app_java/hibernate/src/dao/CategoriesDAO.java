/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entite.Categories;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mathi
 */
public class CategoriesDAO {

    static Session session = null;

    public static void insert(Categories r) {
        session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        session.save(r);
        tx.commit();
        session.close();

    }

    public static void update(Categories r, String name) {
        session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();

        r.setNom(name);
        session.update(r);
        tx.commit();
        session.close();

    }

    public static void delete(Categories r) {
        session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        session.delete(r);
        tx.commit();
        session.close();

    }

}
