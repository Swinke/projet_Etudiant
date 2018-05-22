/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.*;

/**
 *
 * @author DanielLesieur
 */
public class ConnectOracle {

    private static String url = "";
    private static String user = "";
    private static String password = "";

    public static Connection conn = null;
    public static Statement stmt = null;

    /**
     * Ouverture de la connexion selon les arguments passés en paramètres.
     *
     * @param args argument(s) au nombre de 3, passé(s) en paramètres args[0]
     * url de la connexion args[1] le nom d'usager args[2] le mot de passe
     *
     * url = jdbc:oracle:thin:@localhost:1521:XE url = jdbc:oracle:thin:@"+ url
     * +":1521:baclab
     */
    public static void openConn(String[] args) {
        url = args[0];
        user = args[1];
        password = args[2];

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            System.out.println("Connection réussie!");
        } catch (SQLException se) {
            if (se.getErrorCode() == 1017) {
                System.out.println("Accès refusé : nom d'usager et/ou mot de passe invalide");
                System.exit(0);
            } else {
                System.out.println("La connection n'a pu être établie avec l'hôte distant : " + url);
                System.out.println("Veuillez SVP vérifier l'url");
                System.exit(0);
            }
        } catch (Exception se) {
            System.out.println("Problèmes majeurs avec l'application. Veuillez contacter le département TI!");
            System.exit(0);
        }
    }

}
