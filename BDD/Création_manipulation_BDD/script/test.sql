SET ECHO ON
SPOOL script/resultats/test.out

-- Insertion de valeurs erronnées pour les tests -- 
-- Insertion de TYPECHIRURGIE (types de chirurgie)    

    -- test de violation de la clé primaire
    INSERT INTO TYPECHIRURGIE (idType,nom,description)
	VALUES(1,'Chirurgie yeux','Chirurgie des yeux');

    -- test de violation champ obligatoire (not null)
    INSERT INTO TYPECHIRURGIE (idType,description)
	VALUES(5,'Chirurgie des yeux');

-- Insertion de SALLE 
    
    -- test de violation de la clé primaire
    INSERT INTO SALLE (idSalle,nom)
	VALUES(1,'C-99');

    -- test de violation champ obligatoire (not null)
    INSERT INTO SALLE (idSalle)
	VALUES(99);

Prompt Insertion de SPECIALISATIONSALLE

    -- test de violation de la clé primaire   
    INSERT INTO SPECIALISATIONSALLE (idSalle,idType)
    VALUES('1',1);

    -- test de violation de la clé primaire   
    INSERT INTO SPECIALISATIONSALLE (idSalle,idType)
    VALUES(1,'1');

    -- test de violation de la clé primaire   
    -- Clé déjà existante
    INSERT INTO SPECIALISATIONSALLE (idSalle,idType)
    VALUES(1,1);

    -- test de violation de la clé primaire not null
    INSERT INTO SPECIALISATIONSALLE (idType)
    VALUES(2);
    
    INSERT INTO SPECIALISATIONSALLE (idSalle)
    VALUES(2);


-- Insertion de CATEGORIES (catégories de médicaments)    

    -- test de violation de la clé primaire
    INSERT INTO CATEGORIES (idCategorie,nom)
	VALUES(3,'ANTIBIOTIQUES');

    -- test de violation champ obligatoire (not null)
    INSERT INTO CATEGORIES (idCategorie,description)
	VALUES(99,'Substance naturelle ou synthétique qui détruit ou bloque la croissance des bactéries');

-- Insertion de MEDICAMENT
    -- test de violation de la clé primaire
    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(1,'Vitamine C',15.45,3);

    -- test de violation de la clé étrangère
    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,'Vitamine C',15.45,99);
    
    -- test de violation prix < 0
    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,'Vitamine A',-1.99,3);
    
    -- test de violation champ obligatoire (not null)
    INSERT INTO MEDICAMENT (idMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,1.99,3);
    

-- Insertion de SPECIALITE (spécialités médicales)    

    -- test de violation de la clé primaire
    INSERT INTO SPECIALITE (code,titre,description)
	VALUES('GASTRO','Gastéropode','Classe animale des escargots');

    -- test de violation champ obligatoire (not null)
    INSERT INTO SPECIALITE (code,description)
	VALUES('PODIA','Branche de la médecine qui s''occupe des pieds');

Prompt Insertion de DOCTEUR
    
    -- test de violation de la clé primaire
    INSERT INTO DOCTEUR (matricule,nomM,prenomM,specialite,ville,adresse,niveau)
    VALUES ('KENO','Kenneth','Oppel','GASTRO','Port Alberni','666 rue de Tatooine','Interne');

    -- test de violation de la clé étrangère
    INSERT INTO DOCTEUR (matricule,nomM,prenomM,specialite,ville,adresse,niveau)
    VALUES ('WINM','Windu','Mace','VISION','Coruscant','999 avenue Galactique','Etudiant');

    -- test de violation champ obligatoire (not null) MANQUE LE NOM
    INSERT INTO DOCTEUR (matricule,prenomM,specialite,ville,adresse,niveau)
    VALUES ('WINM','Mace','VISION','Coruscant','999 avenue Galactique','Etudiant');

    -- test de violation champ obligatoire (not null) MANQUE LE PRENOM
    INSERT INTO DOCTEUR (matricule,nomM,specialite,ville,adresse,niveau)
    VALUES ('WINM','Windu','VISION','Coruscant','999 avenue Galactique','Etudiant');

    -- test de violation valeurs définies pour le champ niveau
    INSERT INTO DOCTEUR (matricule,nomM,prenomM,specialite,ville,adresse,niveau,nbrPatients)
    VALUES ('KENU','Kenobi','Obi-Wan','CARDIO','Québec','666 rue de Tatooine','Maitre Jedi',0);

    -- test de violation nbrPatients < 0
    UPDATE DOCTEUR SET nbrPatients = -1 WHERE matricule = 'PALS';


Prompt Insertion de DOSSIERPATIENT

    -- test de violation de la clé primaire 
    INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (1,'Skywalker','Anakin','M','SKYA12345678','01-07-2050','PALS');

    -- test de violation de la clé unique numAS
    INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Skywalker','Anakin','M','SKYA12345678','01-07-2050','PALS');

    -- test de violation de la clé étrangère
    INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Skywalker','Anakin','M','SKYA01234567','01-07-2050','NOOB');

    -- test de violation du type DATE
    INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Skywalker','Anakin','M','SKYA12345678','31-06-2050','PALS');

    -- test de violation champ obligatoire (not null) MANQUE LE NOM
    INSERT INTO DOSSIERPATIENT (numDos,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Anakin','M','SKYA12345678','01-07-2050','PALS');

    -- test de violation champ obligatoire (not null) MANQUE LE PRENOM
    INSERT INTO DOSSIERPATIENT (numDos,nomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Skywalker','M','SKYA12345678','01-07-2050','PALS');

    -- test de violation valeurs définies pour le champ GENRE
    INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Solo','Han','I','SOLH12345678','01-07-2085','KENO');

Prompt Insertion d ORDONNANCE
    
    -- test de violation de la clé primaire 
    INSERT INTO ORDONNANCE (numOrd,recommandations,type)
    VALUES (1,'Pose d''un nouveaux respirateur','Chirurgie');

    -- test de violation valeurs définies pour le champ type
    INSERT INTO ORDONNANCE (numOrd,recommandations,type)
    VALUES (SEQUENCE_NUMORD.NEXTVAL,'3 fois par jour','Midicaman');

    -- test de violation du type DATE
    INSERT INTO ORDONNANCE (numOrd,recommandations,type,dateC)
    VALUES (SEQUENCE_NUMORD.NEXTVAL,'3 fois par jour','Midicaman','31-31-2000');

Prompt Insertion de CONSULTATION
    
    -- test de violation de la clé étrangère codeDocteur
    INSERT INTO CONSULTATION (codeDocteur,numDos,dateC,diagnostic,numOrd)
    VALUES ('XXXX',2,TO_DATE('17/12/2018','DD/MM/YYYY'),'Problème d''anxiété',2);

    -- test de violation de la clé étrangère numDos
    INSERT INTO CONSULTATION (codeDocteur,numDos,dateC,diagnostic,numOrd)
    VALUES ('PALS',0,TO_DATE('17/12/2018','DD/MM/YYYY'),'Souffle au coeur important',3);

    -- test de violation de la clé étrangère numOrd
    INSERT INTO CONSULTATION (codeDocteur,numDos,dateC,diagnostic,numOrd)
    VALUES ('KENO',4,TO_DATE('17/12/2018','DD/MM/YYYY'),'tumeur bénine',99);

    -- test de violation champ obligatoire (not null) diagnostic
    INSERT INTO CONSULTATION (codeDocteur,numDos,dateC,numOrd)
    VALUES ('PALS',2,TO_DATE('17/12/2018','DD/MM/YYYY'),4);
    
COMMIT;
Prompt Insertion CHIRURGIE
    -- test de violation de la clé primaire 
    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (1,3,1,'01-01-2018',1600,2000);

    -- test de violation de la clé étrangère idType
    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,99,1,'01-12-2018',0700,1200);
    
    -- test de violation de la clé étrangère idSalle
    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,1,99,'12-01-2018',1600,2000);

    -- test de violation HeureDebut positif
    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,4,3,'14-01-2018',-1,10);
    
    -- test de violation HeureFin positif et supérieur 0
    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,4,3,'14-01-2018',1600,-1);

    -- test de violation HeureFin positif et supérieur 0
    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,4,3,'14-01-2018',0,0);

    -- test de violation HeureFin >=23h59
    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,4,3,'14-01-2018',1600,2400);

    -- test de violation HeureDebut >= HeureFin
    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,4,3,'14-01-2018',1200,1100);


Prompt Insertion ORDONNANCECHIRURGIE 
    -- test de violation de la clé primaire 
    INSERT INTO ORDONNANCECHIRURGIE (idChir,rang)
    VALUES (1,2);
    
    INSERT INTO ORDONNANCECHIRURGIE (numOrd,rang)
    VALUES (1,2);

    -- test de violation de la clé étrangère numOrd
    INSERT INTO ORDONNANCECHIRURGIE (numOrd,idChir,rang)
    VALUES (99,1,2);

    -- test de violation de la clé étrangère idChir
    INSERT INTO ORDONNANCECHIRURGIE (numOrd,idChir,rang)
    VALUES (1,99,2);

    -- test de violation Unique Ordonnance Chirurgie Rang        
    INSERT INTO ORDONNANCECHIRURGIE (numOrd,idChir,rang)
    VALUES (1,2,1);        

Prompt Insertion ORDONNANCEMEDICAMENTS
    
    -- test de violation de la clé primaire 
    INSERT INTO ORDONNANCEMEDICAMENTS (numOrd,idMed,nbBoites)
    VALUES (2,1,10);

    -- test de violation de la clé étrangère numOrd
    INSERT INTO ORDONNANCEMEDICAMENTS (numOrd,idMed,nbBoites)
    VALUES (99,2,1);

    -- test de violation de la clé étrangère iMed
    INSERT INTO ORDONNANCEMEDICAMENTS (numOrd,idMed,nbBoites)
    VALUES (1,99,1);

    -- test de violation nbBoites < 0
    INSERT INTO ORDONNANCEMEDICAMENTS (numOrd,idMed,nbBoites)
    VALUES (4,2,-1);

Prompt suppression consultation interdite si ordonnance
    SELECT * FROM CONSULTATION WHERE numOrd = 1;
    DELETE FROM ORDONNANCE WHERE numOrd = 1;
    SELECT * FROM CONSULTATION WHERE numOrd = 1;

Prompt suppression medicament interdite si ordonnance
    SELECT * FROM ORDONNANCE WHERE numOrd = 1;
    DELETE FROM MEDICAMENT WHERE idMed = 2;
    SELECT * FROM ORDONNANCE WHERE numOrd = 1;

Prompt dossierpatient.matricule = null sur suppression docteur

    SELECT * FROM DOSSIERPATIENT WHERE  matricule = 'PALS';
    DELETE FROM DOCTEUR WHERE matricule = 'PALS';
    SELECT * FROM DOSSIERPATIENT WHERE  numDos = 1;

Prompt suppression des consultations sur suppression dossierpatient
    SELECT * FROM CONSULTATION WHERE numDos = 2;
    DELETE FROM DOSSIERPATIENT WHERE nomP = 'Amidala';
    SELECT * FROM CONSULTATION WHERE numDos = 2;

Prompt suppression des consultations sur suppression DOCTEUR
    SELECT * FROM CONSULTATION WHERE numDos = 4;
    DELETE FROM DOCTEUR WHERE matricule = 'KENO';
    SELECT * FROM CONSULTATION WHERE numDos = 4;


COMMIT;

SPOOL OFF;    