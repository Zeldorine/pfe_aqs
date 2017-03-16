----------------------------------------- Suppression des tables et contraintes si existantes ---------------------------------------
DROP TABLE IF EXISTS Entreprise 	CASCADE;
DROP TABLE IF EXISTS Utilisateur 	CASCADE;
DROP TABLE IF EXISTS Audit 			CASCADE; 
DROP TABLE IF EXISTS Formulaire 	CASCADE; 
---------------------------------------------------------------------------------------------------------------------

------------------------------------------------ Creation des tables ------------------------------------------------
CREATE TABLE Entreprise (
	id					serial 			NOT NULL PRIMARY KEY,
	nom					VARCHAR(20) 	NOT NULL,
	mission				text 			NOT NULL,
	dateCreation		timestamp 		NOT NULL,
	niveauApprobation	smallint		NOT NULL
);

CREATE TABLE Utilisateur (
	id					serial 			NOT NULL PRIMARY KEY,
	nom_utilisateur		varchar(100)	NOT NULL,
	nom					varchar(100) 	NOT NULL,
	prenom				varchar(100) 	NOT NULL,
	mot_de_passe		text			,
	courriel			varchar(100)	NOT NULL,
	id_entreprise		integer			NOT NULL,
	actif				bool			NOT NULL,
	role				smallint		NOT NULL
);


CREATE TABLE Audit (
	id				serial 		NOT NULL PRIMARY KEY,
	date			timestamp 	NOT NULL,
	audit_type		integer 	NOT NULL,
	utilisateur		integer 	NOT NULL,
	objet			text		NOT NULL
);


CREATE TABLE Formulaire (
	id				serial 			NOT NULL PRIMARY KEY,
	nom 			varchar(100) 	NOT NULL,
	date_creation	timestamp 		NOT NULL,
	version			varchar(100) 	NOT NULL,
	id_template		integer 		NOT NULL,
	contenu			text			NOT NULL,
	createur		integer			NOT NULL,
	approbation 	smallint		NOT NULL

---------------------------------------------------------------------------------------------------------------------

----------------------------------------------------- FOREGIN KEY ---------------------------------------------------
ALTER TABLE Utilisateur ADD CONSTRAINT fk_id_entreprise
	FOREIGN KEY (id_entreprise) REFERENCES Entreprise(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE Audit ADD CONSTRAINT fk_id_utilisateur
	FOREIGN KEY (utilisateur) REFERENCES Utilisateur(id) ON UPDATE CASCADE ON DELETE CASCADE;


--------------------------------------------------- Creation index --------------------------------------------------
CREATE UNIQUE INDEX UK_FORMULAIRE ON Formulaire (id, version);
CREATE UNIQUE INDEX UK_NOM_UTILISATEUR ON Utilisateur (nom_utilisateur);

---------------------------------------------------------------------------------------------------------------------

--------------------------------------------------- Creation Contrainte ---------------------------------------------
ALTER TABLE foo ADD UNIQUE (courriel);
---------------------------------------------------------------------------------------------------------------------