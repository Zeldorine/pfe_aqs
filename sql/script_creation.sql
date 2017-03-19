----------------------------------------- Suppression des tables et contraintes si existantes ---------------------------------------
DROP TABLE IF EXISTS Entreprise 	CASCADE;
DROP TABLE IF EXISTS Utilisateur 	CASCADE;
DROP TABLE IF EXISTS Audit 			CASCADE; 
DROP TABLE IF EXISTS Formulaire 	CASCADE; 
---------------------------------------------------------------------------------------------------------------------

------------------------------------------------ Creation des tables ------------------------------------------------
CREATE TABLE public.Entreprise (
	id					serial 			NOT NULL PRIMARY KEY,
	nom					VARCHAR(20) 	NOT NULL,
	mission				text 			NOT NULL,
	dateCreation		timestamp 		NOT NULL,
	niveauApprobation	smallint		NOT NULL
);

CREATE TABLE public.Utilisateur (
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


CREATE TABLE public.Audit (
	id				serial 		NOT NULL PRIMARY KEY,
	date			timestamp 	NOT NULL,
	audit_type		integer 	NOT NULL,
	utilisateur		integer 	NOT NULL,
	objet			text		NOT NULL
);


CREATE TABLE public.Formulaire (
	id				serial 			NOT NULL PRIMARY KEY,
	nom 			varchar(100) 	NOT NULL,
	date_creation	timestamp 		NOT NULL,
	version			varchar(100) 	NOT NULL,
	id_template		integer 		NOT NULL,
	contenu			text			NOT NULL,
	createur		integer			NOT NULL,
	approbation 	smallint		NOT NULL
);

---------------------------------------------------------------------------------------------------------------------

----------------------------------------------------- FOREGIN KEY ---------------------------------------------------
ALTER TABLE public.Utilisateur ADD CONSTRAINT fk_id_entreprise
	FOREIGN KEY (id_entreprise) REFERENCES public.Entreprise(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE public.Audit ADD CONSTRAINT fk_id_utilisateur
	FOREIGN KEY (utilisateur) REFERENCES public.Utilisateur(id) ON UPDATE CASCADE ON DELETE CASCADE;


--------------------------------------------------- Creation index --------------------------------------------------
CREATE UNIQUE INDEX UK_FORMULAIRE ON public.Formulaire (id, version);
CREATE UNIQUE INDEX UK_NOM_UTILISATEUR ON public.Utilisateur (nom_utilisateur);

---------------------------------------------------------------------------------------------------------------------

--------------------------------------------------- Creation Contrainte ---------------------------------------------
ALTER TABLE public.Utilisateur ADD UNIQUE (courriel);
---------------------------------------------------------------------------------------------------------------------





















--------------------------------------------------- SCHEMA TEST ---------------------------------------------
CREATE TABLE test.Entreprise (
	id					serial 			NOT NULL PRIMARY KEY,
	nom					VARCHAR(20) 	NOT NULL,
	mission				text 			NOT NULL,
	dateCreation		timestamp 		NOT NULL,
	niveauApprobation	smallint		NOT NULL
);

CREATE TABLE test.Utilisateur (
	id					serial 			NOT NULL PRIMARY KEY,
	nom_utilisateur		varchar(100)	NOT NULL,
	nom					varchar(100) 	NOT NULL,
	prenom				varchar(100) 	NOT NULL,
	mot_de_passe		text			,
	courriel			varchar(100)	NOT NULL,
	id_entreprise		integer			NOT NULL,
	actif				integer			NOT NULL,
	role				smallint		NOT NULL
);


CREATE TABLE test.Audit (
	id				serial 		NOT NULL PRIMARY KEY,
	date			timestamp 	NOT NULL,
	audit_type		integer 	NOT NULL,
	utilisateur		integer 	NOT NULL,
	objet			text		NOT NULL
);


CREATE TABLE test.Formulaire (
	id				serial 			NOT NULL PRIMARY KEY,
	nom 			varchar(100) 	NOT NULL,
	date_creation	timestamp 		NOT NULL,
	version			varchar(100) 	NOT NULL,
	id_template		integer 		NOT NULL,
	contenu			text			NOT NULL,
	createur		integer			NOT NULL,
	approbation 	smallint		NOT NULL
);

ALTER TABLE test.Utilisateur ADD CONSTRAINT fk_id_entreprise
	FOREIGN KEY (id_entreprise) REFERENCES test.Entreprise(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE test.Audit ADD CONSTRAINT fk_id_utilisateur
	FOREIGN KEY (utilisateur) REFERENCES test.Utilisateur(id) ON UPDATE CASCADE ON DELETE CASCADE;
    
    CREATE UNIQUE INDEX UK_FORMULAIRE ON test.Formulaire (id, version);
CREATE UNIQUE INDEX UK_NOM_UTILISATEUR ON test.Utilisateur (nom_utilisateur);
ALTER TABLE test.Utilisateur ADD UNIQUE (courriel);
