SET ECHO ON
SPOOL script/resultats/insert.out

Prompt Format de la date
ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';


-- Insertion de valeurs initiales pour les tests -- 
-- Insertion de TYPECHIRURGIE (types de chirurgie)

    INSERT INTO TYPECHIRURGIE (idType,nom,description)
	VALUES(SEQUENCE_TYPECHIR.NEXTVAL,'Chirurgie coeur','Chirurgie du système cardiaque');

    INSERT INTO TYPECHIRURGIE (idType,nom,description)
	VALUES(SEQUENCE_TYPECHIR.NEXTVAL,'Chirurgie rein','Chirurgie du système rénale');

    INSERT INTO TYPECHIRURGIE (idType,nom,description)
	VALUES(SEQUENCE_TYPECHIR.NEXTVAL,'Chirurgie poumon','Chirurgie du système respiratoire');
	
	INSERT INTO TYPECHIRURGIE (idType,nom,description)
	VALUES(SEQUENCE_TYPECHIR.NEXTVAL,'Chirurgie cerveau','Chirurgie du cérébrale');    

-- Insertion de SALLE 

    INSERT INTO SALLE (idSalle,nom)
	VALUES(SEQUENCE_SALLE.NEXTVAL,'A-22');

    INSERT INTO SALLE (idSalle,nom)
	VALUES(SEQUENCE_SALLE.NEXTVAL,'A-24');

    INSERT INTO SALLE (idSalle,nom)
	VALUES(SEQUENCE_SALLE.NEXTVAL,'B-12');

Prompt Insertion de SPECIALISATIONSALLE
    -- Coeur,A-22
    INSERT INTO SPECIALISATIONSALLE (idType,idSalle)
    VALUES(1,1);

    -- Rein,A-24
    INSERT INTO SPECIALISATIONSALLE (idType,idSalle)
    VALUES(2,2);

    -- Poumon,B-12
    INSERT INTO SPECIALISATIONSALLE (idType,idSalle)
    VALUES(3,1);

    -- Cerveau, B-12
    INSERT INTO SPECIALISATIONSALLE (idType,idSalle)
    VALUES(4,3);


-- Insertion de CATEGORIES (catégories de médicaments)

    INSERT INTO CATEGORIES (idCategorie,nom,description)
	VALUES(SEQUENCE_CATMED.NEXTVAL,'PRODUITS NATURELS','Produit d''origine végétale');

    INSERT INTO CATEGORIES (idCategorie,nom,description)
	VALUES(SEQUENCE_CATMED.NEXTVAL,'ANALGÉSIQUES','Qui supprime ou atténue la sensibilité à la douleur');

    INSERT INTO CATEGORIES (idCategorie,nom,description)
	VALUES(SEQUENCE_CATMED.NEXTVAL,'VITAMINES','Substance organique indispensable à l''organisme, apportée en petite quantité par l''alimentation');

-- Insertion de MEDICAMENT

    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,'Teinture Achillée',10.99,1);

    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,'Teinture Echinacea',9.99,1);

    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,'Teinture Molène',11.99,1);

    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,'Ibuprofène',5.99,2);

    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,'Acétaminophène',3.99,2);

    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,'Vitamine C',15.45,3);

    INSERT INTO MEDICAMENT (idMed,nomMed,prix,categorie)
    VALUES(SEQUENCE_MED.NEXTVAL,'Vitamine A',5.89,3);
    

-- Insertion de SPECIALITE (spécialités médicales)

    INSERT INTO SPECIALITE (code,titre,description)
	VALUES('IMMUNO','Immunologie','Étude du système immunitaire');

    INSERT INTO SPECIALITE (code,titre,description)
	VALUES('CARDIO','Cardiologie','Étudie le cœur et ses maladies');
    
    INSERT INTO SPECIALITE (code,titre,description)
	VALUES('DERMATO','Dermatologie','Branche de la médecine qui s''occupe de la peau, des muqueuses et des phanères');

    INSERT INTO SPECIALITE (code,titre,description)
	VALUES('GASTRO','Gastro-entérologie','Étudie le système digestif et ses maladies');

Prompt Insertion de DOCTEUR

    INSERT INTO DOCTEUR (matricule,nomM,prenomM,specialite,ville,adresse,niveau,nbrPatients)
    VALUES ('KENO','Kenobi','Obi-Wan','CARDIO','Laval','666 rue de Tatooine','Docteur',0);
    
    --- Étudiant
    INSERT INTO DOCTEUR (matricule,nomM,prenomM,ville,adresse,niveau)
    VALUES ('TREJ','Tremblay','Jean','Saguenay','100 rue des bleuets','Etudiant');

    -- Vérification du nombre de patients initialisé à zéro
    INSERT INTO DOCTEUR (matricule,nomM,prenomM,specialite,ville,adresse,niveau)
    VALUES ('PALS','Palpatine','Sheev','IMMUNO','Coruscant','999 avenue Galactique','Docteur');  

Prompt Insertion de DOSSIERPATIENT

    INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Skywalker','Anakin','M','SKYA12345678','01-07-2050','PALS');

    INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Amidala','Padme','F','AMIP12345678','01-07-2055','KENO');

    INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Solo','Han','M','SOLH12345678','01-07-2085','KENO');

    INSERT INTO DOSSIERPATIENT (numDos,nomP,prenomP,genre,numAS,dateNaiss,matricule)
    VALUES (SEQUENCE_NUMDOS.NEXTVAL,'Buzz','Eclair','M','BUZE12345354','01-07-1992','KENO');

Prompt Insertion d ORDONNANCE
    INSERT INTO ORDONNANCE (numOrd,recommandations,type)
    VALUES (SEQUENCE_NUMORD.NEXTVAL,'Pose d''un nouveaux respirateur','Chirurgie');

    INSERT INTO ORDONNANCE (numOrd,recommandations,type)
    VALUES (SEQUENCE_NUMORD.NEXTVAL,'3 fois par jour','Medicaments');

    INSERT INTO ORDONNANCE (numOrd,recommandations,type)
    VALUES (SEQUENCE_NUMORD.NEXTVAL,'Greffe du coeur','Chirurgie');

    INSERT INTO ORDONNANCE (numOrd,recommandations,type)
    VALUES (SEQUENCE_NUMORD.NEXTVAL,'2 fois par semaine','Medicaments');

    INSERT INTO ORDONNANCE (numOrd,recommandations,type)
    VALUES (SEQUENCE_NUMORD.NEXTVAL,'opération tumeur','Chirurgie');

Prompt Insertion de CONSULTATION
    INSERT INTO CONSULTATION (codeDocteur,numDos,diagnostic,numOrd)
    VALUES ('PALS',1,'Insufisance pulmonaire',1);

    INSERT INTO CONSULTATION (codeDocteur,numDos,diagnostic,numOrd)
    VALUES ('KENO',2,'Problème d''anxiété',2);

    INSERT INTO CONSULTATION (codeDocteur,numDos,diagnostic,numOrd)
    VALUES ('PALS',3,'Souffle au coeur important',3);

    INSERT INTO CONSULTATION (codeDocteur,numDos,diagnostic,numOrd)
    VALUES ('PALS',2,'Problème peau',4);

    INSERT INTO CONSULTATION (codeDocteur,numDos,diagnostic,numOrd)
    VALUES ('KENO',4,'tumeur bénine',5);
    

Prompt Insertion CHIRURGIE
    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,3,1,'01-01-2018',1600,2000);

    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,1,1,'01-12-2018',0700,1200);

    INSERT INTO CHIRURGIE (idChir,idType,idSalle,dateChirurgie,HeureDebut,HeureFin)
    VALUES (SEQUENCE_CHIRURGIE.NEXTVAL,4,3,'12-01-2018',1600,2000);

Prompt Insertion ORDONNANCECHIRURGIE 
    INSERT INTO ORDONNANCECHIRURGIE (numOrd,idChir,rang)
    VALUES (1,1,1);

    INSERT INTO ORDONNANCECHIRURGIE (numOrd,idChir,rang)
    VALUES (3,2,1);

    INSERT INTO ORDONNANCECHIRURGIE (numOrd,idChir,rang)
    VALUES (5,1,3);

Prompt Insertion ORDONNANCEMEDICAMENTS
    INSERT INTO ORDONNANCEMEDICAMENTS (numOrd,idMed,nbBoites)
    VALUES (2,1,10);

    INSERT INTO ORDONNANCEMEDICAMENTS (numOrd,idMed,nbBoites)
    VALUES (4,2,2);

    INSERT INTO ORDONNANCEMEDICAMENTS (numOrd,idMed,nbBoites)
    VALUES (1,2,1);

    INSERT INTO ORDONNANCEMEDICAMENTS (numOrd,idMed,nbBoites)
    VALUES (1,3,100);

COMMIT;

SPOOL OFF;
