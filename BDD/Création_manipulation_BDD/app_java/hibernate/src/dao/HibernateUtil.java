/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Mathi
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    static String url, password, user;

    public static void openConn(String[] args) {
        if (args.length == 3) {
            url = args[0];
            user = args[1];
            password = args[2];
        } else {
            System.out.println("Le nombre d'argument n'est pas valide!");
            System.out.println("Veuillez SVP relancer la commande tel que...");
            System.out.println("hibernate.jar url user password");
            System.exit(0);
        }

        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            //sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();            
            Configuration conf = new Configuration();
            conf.configure("hibernate.cfg.xml");
            conf.setProperty("hibernate.connection.url", url);
            conf.setProperty("hibernate.connection.username", user);
            conf.setProperty("hibernate.connection.password", password);

            sessionFactory = conf.buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            
            System.out.println("\n\n***\nLa connection n'a pu être établie avec l'hôte distant : " + url);
            System.out.println("Veuillez SVP vérifier l'url, le nom d'usager et le mot de passe\n****");
            //throw new ExceptionInInitializerError(ex);
            System.exit(0);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
