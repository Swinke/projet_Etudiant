/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.*;

/**
 *
 * @author Hippolite Damand, Daniel Lesieur
 */
public class Jdbc {

    /**
     * @param args au nombre de trois : url = l'adresse de connexion à la BD
     * user = le nom d'usager de la BD password = le mot de passe pour accéder à
     * la BD
     */
    public static void main(String[] args) {
        lancerFonctionSelonArgumentsLigneCommande(args);
    }

    /**
     * Définir l'action à entreprendre selon les arguments passés en paramètres.
     *
     * @param args argument(s) passé(s) en paramètres
     * @return decision soit l'action à entreprendre
     */
    protected static void lancerFonctionSelonArgumentsLigneCommande(String[] args) {
        if (args.length == 3) {
            lancerInsertionDonnees(args);
        } else {
            System.out.println("Le nombre d'argument n'est pas valide!");
            System.out.println("Veuillez SVP relancer la commande tel que...");
            System.out.println("jdbc.jar url user password");            
            System.exit(0);
        }
    }

    /**
     * Insertion et affichage des données.
     *
     * @param args argument(s) passé(s) en paramètres
     */
    protected static void lancerInsertionDonnees(String[] args) {

        String sql;

        /* Ouverture de la connection */
        ConnectOracle.openConn(args);

        try {
            ResultSet rs;

            try {
                sql = "INSERT INTO SPECIALITE(code,titre,description)"
                        + " VALUES ('PEDIA','Pediatrie','Médecine des enfants')";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("ERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante ou titre non unique dans SPECIALITE");
                } else if (se.getErrorCode() == 1400) {
                    System.out.print("Le titre de la spécialité ne peut pas être nul");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO TYPECHIRURGIE (idType,nom,description)"
                        + "VALUES(SEQUENCE_TYPECHIR.NEXTVAL,'Chirurgie foie','Chirurgie du foie')";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans TYPECHIRURGIE");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO CATEGORIES (idCategorie,nom,description)"
                        + "VALUES(SEQUENCE_CATMED.NEXTVAL,'ANTIDREPRESSEUR','Calme l''anxiété')";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans CATEGORIE");
                } else if (se.getErrorCode() == 1400) {
                    System.out.print("Le nom de la catégorie ne peut pas être nul");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)"
                        + "VALUES(SEQUENCE_MED.NEXTVAL,'Sauge',2.55,1)";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans SPECIALITE");
                } else if (se.getErrorCode() == 1400) {
                    System.out.print("Le nom du médicament ne peut pas être nul");
                } else if (se.getErrorCode() == 2290) {
                    System.out.print("Le prix d'un médicament ne peut pas être négatif");
                } else if (se.getErrorCode() == 2291) {
                    System.out.print("La catégorie est erronnée");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO SALLE (idSalle,nom) VALUES(SEQUENCE_SALLE.NEXTVAL,'C-44')";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();

            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans SALLE");
                } else if (se.getErrorCode() == 1400) {
                    System.out.print("Le nom de la salle ne peut pas être nul");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO DOCTEUR (matricule,nomM,prenomM,specialite,ville,adresse,niveau,nbrPatients)"
                        + "VALUES ('JEAL','Jean','Léo','PEDIA','France','la petite riviere','Etudiant',10)";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans DOCTEUR");
                } else if (se.getErrorCode() == 1400) {
                    System.out.print("Le nom ou le prénom du docteur ne peut pas être nul");
                } else if (se.getErrorCode() == 2290) {
                    System.out.print("Le nombre de patients ne peut pas être négatif");
                } else if (se.getErrorCode() == 2291) {
                    System.out.print("La sécialité est erronnée");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)"
                        + "VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Leclair','John','M','LECJ12345678',to_date('01-07-1964','DD-MM-YYYY'),'KENO')";

                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans DOSSIERPATIENT");
                } else if (se.getErrorCode() == 1400) {
                    System.out.print("Le nom ou le prénom du patient ne peut pas être nul");
                } else if (se.getErrorCode() == 2291) {
                    System.out.print("Le matricule du docteur est erronné");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO CONSULTATION (CodeDocteur, numDos, diagnostic,numOrd)"
                        + "VALUES ('PALS',4,'Cancer stade 3',5)";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans CONSULTATION");
                } else if (se.getErrorCode() == 2291) {
                    System.out.print("Le numéro d'ordonnance, le numéro de dossier ou le matricule du docteur est erronné");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO ORDONNANCE (numOrd,recommandations,type)"
                        + "VALUES (SEQUENCE_NUMORD.NEXTVAL,'3 fois par jour','Chirurgie')";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans ORDONNANCE");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO ORDONNANCE (numOrd,recommandations,type)"
                        + "VALUES (SEQUENCE_NUMORD.NEXTVAL,'Administrer avant le coucher','Medicaments')";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans ORDONNANCE");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO SPECIALISATIONSALLE (idType,idSalle)"
                        + "VALUES (1,2)";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans SPECIALISATIONSALLE");
                } else if (se.getErrorCode() == 2291) {
                    System.out.print("Le de salle ou le type de chirurgie est erronné");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)"
                        + "VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,1,1,to_date('12-01-2020','DD-MM-YYYY'),0700,1000)";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();

            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans CHIRURGIE");
                } else if (se.getErrorCode() == 2290) {
                    System.out.print("L'heure de début ou de fin est erronnée");
                } else if (se.getErrorCode() == 1841) {
                    System.out.print("L'année de la date de chirurgie est erronnée");
                } else if (se.getErrorCode() == 1843) {
                    System.out.print("Le mois de de la date de chirurgie est erronné");
                } else if (se.getErrorCode() == 1847) {
                    System.out.print("Le jour de la date de chirurgie est erronné");
                } else if (se.getErrorCode() == 2291) {
                    System.out.print("Le numéro de salle ou le type de chirurgie est erronné");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO ORDONNANCEMEDICAMENTS (numOrd,idMed,nbBoites)"
                        + "VALUES (1,1,1)";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante dans ORDONNANCEMEDICAMENTS");
                } else if (se.getErrorCode() == 2290) {
                    System.out.print("Le nombre de boîtes ne peut pas être négatif");
                } else if (se.getErrorCode() == 2291) {
                    System.out.print("Le numéro d'ordonnance ou le code du médicament est erronné");
                } else {
                    se.printStackTrace();
                }
            }

            try {
                sql = "INSERT INTO ORDONNANCECHIRURGIE (numOrd,idChir,rang)"
                        + "VALUES (1,2,2)";
                rs = ConnectOracle.stmt.executeQuery(sql);
                rs.close();
            } catch (SQLException se) {
                System.out.print("\nERREUR : ");
                if (se.getErrorCode() == 1) {
                    System.out.print("Clé primaire existante ou ordonnance/rang dupliqué dans ORDONNANCECHIRURGIE");
                } else if (se.getErrorCode() == 2290) {
                    System.out.print("Le rang ne peut pas être négatif");
                } else if (se.getErrorCode() == 2291) {
                    System.out.print("Le numéro d'ordonnance ou le code de chirurgie est erronné");
                } else {
                    se.printStackTrace();
                }
            }

            System.out.println("\n\n--- Cas d'utilisation ---");
            System.out.println("Cas 1a : nombre de chirurgie par docteur");
            sql = "SELECT * FROM NB_CHIRURGIE_DOCTEUR";
            rs = ConnectOracle.stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("----\nMatricule : " + rs.getString(1));
                System.out.println("Docteur : " + rs.getString(2));
                System.out.println("nombre chirurgies : " + rs.getInt(3));
            }
            rs.close();
            System.out.println("----\n");

            System.out.println("Cas 2a : nombre de consultations par docteur");
            sql = "SELECT * FROM NB_CONS_DOCTEUR";
            rs = ConnectOracle.stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("----\nMatricule : " + rs.getString(1)
                        + "\nnombre consultation : " + rs.getInt(2));
            }
            rs.close();
            System.out.println("----\n");

            System.out.println("Cas 2b : nombre de consultations par spécialité");
            sql = "SELECT * FROM NB_CONS_SPECIALITE";
            rs = ConnectOracle.stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("----\nSpécialité : " + rs.getString(1)
                        + "\nnombre consultation : " + rs.getInt(2));
            }
            rs.close();
            System.out.println("----\n");

            System.out.println("Cas 3a : nombre de consultations moyen par mois");
            sql = "SELECT * FROM NB_CONS_MOY_MOIS";
            rs = ConnectOracle.stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Nombre moyen de consultation par mois : " + rs.getDouble(1));
            }
            rs.close();
            System.out.println("----\n");

            System.out.println("Cas 3b : nombre de consultations par année");
            sql = "SELECT * FROM NB_CONS_AN";
            rs = ConnectOracle.stmt.executeQuery(sql);
            System.out.println("Année\tnombre consultations");
            System.out.println("----\t-------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1)
                        + "\t" + rs.getInt(2));
            }
            rs.close();
            System.out.println("----\n");

            System.out.println("Cas 3a : nombre de médicaments prescrits par docteur");
            sql = "SELECT * FROM NB_MED_DOCTEUR";
            rs = ConnectOracle.stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("----\nMatricule : " + rs.getString(1)
                        + "\nDocteur : " + rs.getString(2)
                        + "\nnombre de médicaments prescrits : " + rs.getInt(3));
            }
            rs.close();
            System.out.println("----\n");

            ConnectOracle.stmt.close();
            ConnectOracle.conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception se) {
            se.printStackTrace();
        }

    }

}
