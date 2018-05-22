SET ECHO ON
SPOOL script/resultats/create.out

Prompt Format de la date
ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';  

Prompt Création des séquences
CREATE SEQUENCE SEQUENCE_MED MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQUENCE_SALLE MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQUENCE_NUMDOS MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQUENCE_CHIRURGIE MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQUENCE_TYPECHIR MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQUENCE_CATMED MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQUENCE_NUMORD MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1;

Prompt Creation des tables
Prompt Création des tables sans contraintes de clés étrangères
CREATE TABLE SPECIALITE
(code VARCHAR(45) NOT NULL,
 titre VARCHAR(45) NOT NULL,
 description VARCHAR(255),
 CONSTRAINT ClePrimaireSpecialite PRIMARY KEY (code),
 CONSTRAINT UniqueTitreSpec UNIQUE (titre)
);

CREATE TABLE SALLE
(idSalle INTEGER  NOT NULL,
 nom VARCHAR(45) NOT NULL,
 CONSTRAINT ClePrimaireSalle PRIMARY KEY (idSalle),
 CONSTRAINT UniqueNomSalle UNIQUE (nom)
);

CREATE TABLE TYPECHIRURGIE
(idType INTEGER NOT NULL,
 nom VARCHAR(45) NOT NULL, -- Chirurgie et médicament autorisés
 description VARCHAR(255),
 CONSTRAINT ClePrimaireTypeChirurgie PRIMARY KEY (idType),
 CONSTRAINT UniqueTypeChirurgie UNIQUE (nom)
);

CREATE TABLE CATEGORIES
(idCategorie INTEGER NOT NULL,
 nom VARCHAR(45) NOT NULL,
 description VARCHAR(255),
 CONSTRAINT ClePrimaireCategorie PRIMARY KEY (idCategorie),
 CONSTRAINT UniqueCategorie UNIQUE (nom)
);

CREATE TABLE ORDONNANCE
(numOrd INTEGER NOT NULL,
recommandations LONG,-- PERMET BCP DE TEXTE VS VARCHAR(255)
type VARCHAR(45) NOT NULL,
dateC DATE DEFAULT SYSDATE NOT NULL, -- Date selon le système
CONSTRAINT ClePrimaireNumOrd PRIMARY KEY (numOrd),
CONSTRAINT VerifTypeOrdonnance CHECK (type IN ('Chirurgie','Medicaments'))
);


-- Création des tables avec clés étrangères
CREATE TABLE MEDICAMENT
(idMed INTEGER NOT NULL,
 nomMed VARCHAR(45) NOT NULL,
 prix NUMERIC(8,2) default 0 NOT NULL,-- Le prix varie de 0,00$ à 999 999,00$,
 categorie INTEGER,
 CONSTRAINT ClePrimaireMedicament PRIMARY KEY (idMed),
 CONSTRAINT CleEtrangereCategorie FOREIGN KEY (categorie) REFERENCES CATEGORIES(idCategorie),
 CONSTRAINT UniqueMedCat UNIQUE (nomMed,categorie),
 CONSTRAINT VerifPrixSupEgalZero CHECK (prix >= 0)
);

CREATE TABLE ORDONNANCEMEDICAMENTS
 (numOrd INTEGER NOT NULL,
  idMed INTEGER NOT NULL,
  nbBoites INTEGER default 0 NOT NULL,
 CONSTRAINT ClePrimaireOrdMed PRIMARY KEY (numOrd,idMed),
 CONSTRAINT CleEtrangereMedicament FOREIGN KEY (idMed) REFERENCES MEDICAMENT,
 CONSTRAINT CleEtrangereNumOrdMed FOREIGN KEY (numOrd) REFERENCES ORDONNANCE,
 CONSTRAINT VerifNbBoiteSupEgaleZero CHECK (nbBoites >= 0)
 );

CREATE TABLE SPECIALISATIONSALLE
(idType INTEGER NOT NULL,
 idSalle INTEGER NOT NULL,
 dateC DATE DEFAULT SYSDATE NOT NULL, -- Date selon le système
CONSTRAINT ClePrimaireSpecSalle PRIMARY KEY (idType,idSalle),
CONSTRAINT CleEtrangereSpecType FOREIGN KEY (idType) REFERENCES TYPECHIRURGIE,
CONSTRAINT CleEtrantgereSpecSalle FOREIGN KEY (idSalle) REFERENCES SALLE
);

 CREATE TABLE CHIRURGIE
 (idChir INTEGER NOT NULL,
  idType INTEGER NOT NULL,
  idSalle INTEGER NOT NULL,
  dateChirurgie DATE DEFAULT SYSDATE NOT NULL, -- Date selon le système
  HeureDebut INTEGER DEFAULT 0,
  HeureFin INTEGER DEFAULT 0,
  CONSTRAINT ClePrimaireChirurgie PRIMARY KEY (idChir),
  CONSTRAINT CleEtrangereTypeChirurgieSalle FOREIGN KEY (idType,idSalle) REFERENCES SPECIALISATIONSALLE,
  CONSTRAINT HeureDebutPos CHECK (HeureDebut>=0),
  CONSTRAINT HeureFinPos CHECK (HeureFin>0),
  CONSTRAINT HeureFinVraisemblable CHECK (HeureFin<=2359),
  CONSTRAINT VerifHeureDebutPGHeureFin CHECK ((HeureFin-HeureDebut>0)) -- Heure début < Heure fin
 );

CREATE TABLE ORDONNANCECHIRURGIE
 (numOrd INTEGER NOT NULL,
  idChir INTEGER NOT NULL,
  rang INTEGER DEFAULT 0 NOT NULL,
  CONSTRAINT ClePrimaireOrdChir PRIMARY KEY (numOrd,idChir),
  CONSTRAINT UniqueOrdChirRang UNIQUE (numOrd, rang),
  CONSTRAINT CleEtrangereNumOrdChir FOREIGN KEY (numOrd) REFERENCES ORDONNANCE,
  CONSTRAINT CleEtrangereIdChir FOREIGN KEY (idChir) REFERENCES CHIRURGIE,
  CONSTRAINT VerifRangPos CHECK (rang >=0)
 );

CREATE TABLE DOCTEUR
(matricule	VARCHAR(45) NOT NULL,
 nomM VARCHAR(45) NOT NULL,
 prenomM VARCHAR(45) NOT NULL,
 specialite VARCHAR(45),
 ville VARCHAR(45),
 adresse VARCHAR(255),
 niveau VARCHAR(45),
 nbrPatients INTEGER DEFAULT 0 NOT NULL,
 CONSTRAINT ClePrimaireDocteur PRIMARY KEY 	(matricule),
 CONSTRAINT CleEtrangereSpecialite FOREIGN KEY (specialite) REFERENCES SPECIALITE(code),
 CONSTRAINT VerifNiveauDocteur CHECK (niveau IN ('Etudiant', 'Interne', 'Docteur')),
 CONSTRAINT VerifNbPatientSupEgaleZero CHECK (nbrPatients >= 0)
);

CREATE TABLE DOSSIERPATIENT
(numDos INTEGER NOT NULL,
nomP VARCHAR(45) NOT NULL,
prenomP VARCHAR(45)  NOT NULL,
genre CHAR(1) , -- M, F autorisés
numAS VARCHAR(12), -- Doit être unique
dateNaiss DATE,
dateC DATE DEFAULT SYSDATE NOT NULL, -- Date selon le système
matricule VARCHAR(45),
CONSTRAINT ClePrimaireDossierPatient PRIMARY KEY (numDos),
CONSTRAINT UniqueNumAss UNIQUE (numAS),
CONSTRAINT CleEtrangereMatricule FOREIGN KEY (matricule) REFERENCES DOCTEUR
ON DELETE SET NULL,
CONSTRAINT VerifGenrePatient CHECK (genre IN ('F','M'))
);

CREATE TABLE CONSULTATION
(codeDocteur VARCHAR(45) NOT NULL,
numDos INTEGER NOT NULL,
dateC DATE DEFAULT SYSDATE NOT NULL, -- Date selon le système
diagnostic VARCHAR(255) NOT NULL,
numOrd INTEGER,
CONSTRAINT ClePrimaireConsultation PRIMARY KEY (codeDocteur,numDos,dateC),
CONSTRAINT CleEtrangereConsDoc FOREIGN KEY (codeDocteur) REFERENCES DOCTEUR(matricule)
ON DELETE CASCADE,
CONSTRAINT CleEtrangereConsNumDos FOREIGN KEY (numDos) REFERENCES DOSSIERPATIENT
ON DELETE CASCADE,
CONSTRAINT CleEtrangereConsNumOrd FOREIGN KEY (numOrd) REFERENCES ORDONNANCE
);


Prompt Cas d'utilisation : Afficher le nombre de consultations par docteur
CREATE VIEW NB_CONS_DOCTEUR AS SELECT matricule, count(matricule) "Nbr consultation"
FROM DOCTEUR JOIN CONSULTATION ON matricule = CodeDocteur
GROUP BY matricule
ORDER BY count(matricule) DESC;

Prompt Cas d'utilisation : Afficher le nombre de chirurgie par docteur
CREATE VIEW NB_CHIRURGIE_DOCTEUR AS SELECT t1.matricule, (t1.prenomM ||' '|| t1.nomM) "Docteur",
COUNT(t4.idChir) "Nbr chirurgie"
FROM DOCTEUR t1, CONSULTATION t2, ORDONNANCE t3,
ORDONNANCECHIRURGIE t4
WHERE t1.matricule = t2.CodeDocteur AND t2.numOrd = t3.numOrd AND t3.numOrd = t4.numOrd
GROUP BY t1.matricule, (t1.prenomM ||' '|| t1.nomM)
ORDER BY COUNT(t4.idChir) DESC;

Prompt Cas d'utilisation : Afficher le nombre de consultations par année
CREATE VIEW NB_CONS_AN AS SELECT EXTRACT(year from DATEC) "annee",
COUNT(DATEC) "Nbr de consultation par annee"
FROM CONSULTATION
GROUP BY EXTRACT(year from DATEC); 

Prompt Cas d'utilisation : Afficher le nombre de consultations moyen par mois
CREATE VIEW NB_CONS_MOY_MOIS AS SELECT COUNT(DATEC)/12 "Nbr moyen de consultation/mois"
FROM CONSULTATION; 

Prompt Cas d'utilisation : Afficher le nombre de consultations par specialite
CREATE VIEW NB_CONS_SPECIALITE AS SELECT t2.specialite, COUNT(t2.specialite) "Nbr consultations"
FROM CONSULTATION t1, DOCTEUR t2
WHERE t1.CodeDocteur = t2.matricule
GROUP BY t2.specialite
ORDER BY COUNT(t2.specialite) DESC;

Prompt Cas d'utilisation : Afficher le nombre de médicaments prescrit par docteur
CREATE VIEW NB_MED_DOCTEUR AS SELECT CodeDocteur, (prenomM ||' '||nomM) "Docteur",
count(idMed) "Nbr médicaments prescrits"
FROM CONSULTATION JOIN ORDONNANCEMEDICAMENTS
ON ORDONNANCEMEDICAMENTS.numOrd = CONSULTATION.numOrd
JOIN DOCTEUR
ON DOCTEUR.matricule = CONSULTATION.CodeDocteur
GROUP BY CodeDocteur, (prenomM ||' '||nomM)
ORDER BY count(idMed) DESC;

Prompt Cas d'utilisation : Afficher le nombre de médicaments prescrit par docteur par année
CREATE VIEW NB_MED_DOCTEUR_AN AS SELECT CodeDocteur, (prenomM ||' '||nomM) "Docteur",
EXTRACT(year from DATEC) "annee", count(idMed) "Nbr médicaments prescrits"
FROM CONSULTATION JOIN ORDONNANCEMEDICAMENTS
ON ORDONNANCEMEDICAMENTS.numOrd = CONSULTATION.numOrd
JOIN DOCTEUR
ON DOCTEUR.matricule = CONSULTATION.CodeDocteur
GROUP BY CodeDocteur,(prenomM ||' '||nomM) ,EXTRACT(year from DATEC);

COMMIT;

SPOOL OFF;
