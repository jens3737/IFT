-- -----------------------------------------------
-- Auteur: Jef Anthonissen
-- Beschrijving: Controleren of database bestaat en opnieuw database aamaken.
-- ------------------------------------------------

DROP DATABASE IF EXISTS IFTDB;
SELECT SLEEP(2);
CREATE DATABASE IFTDB;
USE IFTDB;
SET GLOBAL sql_mode='NO_AUTO_VALUE_ON_ZERO';
SET SESSION sql_mode='NO_AUTO_VALUE_ON_ZERO';


-- -----------------------------------------------
-- Beschrijving: Tabellen aanmaken
-- -----------------------------------------------
CREATE TABLE tblPersoon
(
	PER_ID					INT						NOT NULL 	AUTO_INCREMENT	PRIMARY KEY,
	PER_voornaam_naam		NVARCHAR(80)			NOT NULL,
    INDEX(PER_voornaam_naam(80)),
	per_geboortedatum		date								DEFAULT '1970-01-01',
	PER_email				NVARCHAR(50)			NOT NULL,
	PER_geslacht			BIT,
	PER_gsmnummer			NVARCHAR(15),
	PER_adres				NVARCHAR(55)					
);

CREATE TABLE tblStagiair
(
	STA_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	STA_ID_school			INT						NOT NULL,
	STA_ID_persoon			INT						NOT NULL,
	STA_studierichting		VARCHAR(30)				NOT NULL
);

CREATE TABLE tblBegeleiding
(
	BEGL_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	BEGL_ID_begeleider		INT						NOT NULL,
	BEGL_ID_stage			INT						NOT NULL
);

CREATE TABLE tblBegeleider
(
	BEG_ID						INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	BEG_functie_omschrijving	VARCHAR(250),
    BEG_ID_categorie			INT,
	BEG_ID_persoon				INT						NOT NULL
);

CREATE TABLE tblSchool
(
	SCHO_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	SCHO_naam				VARCHAR(70)				NOT NULL,
	SCHO_adres				VARCHAR(50)				NOT NULL,
	SCHO_ID_contactpersoon	INT								,
	SCHO_contactnummer		VARCHAR(20)						
);

CREATE TABLE tblStageopdracht
(
	OPDR_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	OPDR_ID_categorie		INT						NOT NULL,
	OPDR_naam				VARCHAR(100)			NOT NULL,
	OPDR_omschrijving		VARCHAR(5000)			NOT NULL,
	OPDR_scope				VARCHAR(5000)			NOT NULL,
	OPDR_competenties		VARCHAR(5000)			NOT NULL,
	OPDR_locatie			VARCHAR(250)			NOT NULL
);

CREATE TABLE tblStage
(
	STAGE_ID				INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	STAGE_ID_opdracht		INT						NOT NULL,
	STAGE_ID_stagiair		INT						NOT NULL,
	stage_start_datum		DATE					NOT NULL,
	STAGE_eind_datum		DATE					NOT NULL
);

CREATE TABLE tblUseraccount
(
	ACC_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	ACC_ID_persoon			INT						NOT NULL,
	ACC_ID_rol				INT								,
	ACC_creation_date		TIMESTAMP				NOT NULL,
	ACC_update_date			TIMESTAMP				NOT NULL,
	ACC_email				VARCHAR(50)				NOT NULL,
	ACC_wachtwoord			VARCHAR(80)						,
	ACC_secret_question		VARCHAR(100)					,
	ACC_secret_answer		VARCHAR(50)						,
	ACC_active				BIT					NOT NULL
);

CREATE TABLE tblPermission
(
	PERM_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	PERM_naam				INT						NOT NULL,
	PERM_type				VARCHAR(10)				NOT NULL
);

CREATE TABLE tblRol
(
	ROL_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY	,
	ROL_naam				VARCHAR(30)				NOT NULL								,
    ROL_benaming			VARCHAR(30)				NOT NULL								
);

CREATE TABLE tblRolpermission
(
	RPE_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	RPE_ID_rol				INT						NOT NULL,
	RPE_ID_permission		INT						NOT NULL
);

CREATE TABLE tblCategorie
(
	CAT_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	CAT_naam				VARCHAR(50)				NOT NULL
);

CREATE TABLE tblEvaluatieformulier
(
	EVAL_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	EVAL_ID_template		INT								,
	EVAL_ID_stagiair		INT						NOT NULL,
    EVAL_gemiddelde_score	double						,
	EVAL_datum				DATE					NOT NULL,
    EVAL_ID_begeleider		INT						NOT NULL
);


CREATE TABLE tblAntwoord
(
	ANTW_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	ANTW_ID_evalformulier	INT						NOT NULL,
	ANTW_ID_vraag			INT						NOT NULL,
	ANTW_ID_schaal			INT						,
	ANTW_voluit				VARCHAR(500)					
);

CREATE TABLE tblSchaal
(
	SCHAAL_ID				INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	SCHAAL_waarde			VARCHAR(15)				NOT NULL
);


ALTER TABLE tblSchaal AUTO_INCREMENT=0;


CREATE TABLE tblVraag
(
	VRA_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	VRA_ID_template			INT						NOT NULL,
	VRA_definitie			VARCHAR(500)			NOT NULL,
	VRA_type				BIT						NOT NULL

);

CREATE TABLE tblEvaluatietemplate
(
	TEMP_ID					INT						NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	TEMP_naam				VARCHAR(30)				NOT NULL
);

-- -----------------------------------------------
-- Foreign keys
-- -----------------------------------------------

ALTER TABLE tblStagiair
	ADD CONSTRAINT fk_STA_ID_school FOREIGN KEY (STA_ID_school) REFERENCES tblSchool(SCHO_ID),
    ADD CONSTRAINT fk_STA_IF_persoon FOREIGN KEY (STA_ID_persoon) REFERENCES tblPersoon(PER_ID);

ALTER TABLE tblBegeleider
	ADD CONSTRAINT fk_BEG_ID_persoon FOREIGN KEY (BEG_ID_persoon) REFERENCES tblPersoon(PER_ID),
    ADD CONSTRAINT fk_BEG_ID_categorie FOREIGN KEY (BEG_ID_categorie) REFERENCES tblCategorie(CAT_ID);


ALTER TABLE tblBegeleiding
	ADD CONSTRAINT fk_BEGL_ID_begeleider FOREIGN KEY (BEGL_ID_begeleider) REFERENCES tblBegeleider(BEG_ID),
	ADD CONSTRAINT fk_BEGL_ID_stage FOREIGN KEY (BEGL_ID_stage) REFERENCES tblStage(STAGE_ID);


ALTER TABLE tblStageopdracht
	ADD CONSTRAINT fk_OPDR_ID_categorie FOREIGN KEY (OPDR_ID_categorie) REFERENCES tblCategorie(CAT_ID);


ALTER TABLE tblStage
	ADD CONSTRAINT fk_STAGE_ID_opdracht FOREIGN KEY (STAGE_ID_opdracht) REFERENCES tblStageopdracht(OPDR_ID),
	ADD CONSTRAINT fk_STAGE_ID_stagiair FOREIGN KEY (STAGE_ID_stagiair) REFERENCES tblStagiair(STA_ID);


ALTER TABLE tblUseraccount
	ADD CONSTRAINT fk_ACC_ID_persoon FOREIGN KEY (ACC_ID_persoon) REFERENCES tblPersoon(PER_ID),
	ADD CONSTRAINT fk_ACC_ID_rol FOREIGN KEY (ACC_ID_rol) REFERENCES tblRol(ROL_ID);


ALTER TABLE tblRolpermission
	ADD CONSTRAINT fk_RPE_ID_rol FOREIGN KEY (RPE_ID_rol) REFERENCES tblRol(ROL_ID),
	ADD CONSTRAINT fk_RPE_ID_permission FOREIGN KEY (RPE_ID_permission) REFERENCES tblPermission(PERM_ID);


ALTER TABLE tblEvaluatieformulier
	ADD CONSTRAINT fk_EVAL_ID_stagiair FOREIGN KEY (EVAL_ID_stagiair) REFERENCES tblStagiair(STA_ID),
	ADD CONSTRAINT fk_EVAL_ID_template FOREIGN KEY (EVAL_ID_template) REFERENCES tblEvaluatietemplate(TEMP_ID);


ALTER TABLE tblAntwoord
	ADD CONSTRAINT fk_ANTW_ID_evalformulier FOREIGN KEY (ANTW_ID_evalformulier) REFERENCES tblEvaluatieformulier(EVAL_ID),
	ADD CONSTRAINT fk_ANTW_ID_vraag FOREIGN KEY (ANTW_ID_vraag) REFERENCES tblVraag(VRA_ID),
	ADD CONSTRAINT fk_ANTW_ID_schaal FOREIGN KEY (ANTW_ID_schaal) REFERENCES tblSchaal(SCHAAL_ID);


ALTER TABLE tblVraag
	ADD CONSTRAINT fk_VRA_ID_template FOREIGN KEY (VRA_ID_template) REFERENCES tblEvaluatietemplate(TEMP_ID);



ALTER TABLE tblSchool
	ADD CONSTRAINT fk_SCHO_ID_contactpersoon FOREIGN KEY (SCHO_ID_contactpersoon) REFERENCES tblPersoon(PER_ID);






-- -----------------------------------------------
-- Testdata toevoegen
-- -----------------------------------------------
INSERT INTO tblSchaal(SCHAAL_ID, SCHAAL_waarde) 
VALUES	(0, 'N.v.t.'),
		(1, 'Onvoldoende'),
		(2, 'Matig'),
		(3, 'Goed'),
		(4, 'Zeer Goed'),
		(5, 'Excellent');

INSERT INTO tblEvaluatietemplate(TEMP_naam)
VALUES	('Standaard evaluatieformulier'),
		('Leeg evaluatieformulier');


INSERT INTO tblVraag(VRA_definitie, VRA_ID_template,  VRA_type)
VALUES	('Projectmatig en methodisch werken', 1, 0),
		('Pragmatische aanpak', 1, 0),
		('Plannen / organiseren', 1, 0),
		('Resultaatgerichtheid', 1, 0),
		('Nauwkeurigheid', 1, 0),
		('Teamgerichtheid', 1, 0),
		('Zelfstandigheid', 1, 0),
		('Gebruikt toegekende middelen efficiënt', 1, 0),
		('Maken van onderscheid tussen hoofd- en bijzaken', 1, 0),
		('Technologische kennis en inzichten', 1, 0),
		('Technische capaciteiten', 1, 0),
		('Vermogen tot analytisch denken', 1, 0),
		('Conceptueel denken', 1, 0),
		('Probleemoplossend vermogen', 1, 0),
		('Constructief vermogen', 1, 0),
		('Test nauwkeurig', 1, 0),
		('Zin voor uitdagingen', 1, 0),
		('Flexibiliteit', 1, 0),
		('Integriteit', 1, 0),
		('Creativiteit', 1, 0),
		('Verantwoordelijkheidszin', 1, 0),
		('Proactiviteit', 1, 0),
		('Controlleert eigen werk', 1, 0),
		('Stuurt bij na feedback', 1, 0),
		('Stressbestendigheid', 1, 0),
		('Communicatie', 1, 0),
		('Delen van kennis', 1, 0),
		('Inzetbaarheid', 1, 0),
		('Actief luisteren', 1, 0),
		('Pluspunten / meerwaarde:', 1, 1),
		('Aandachtspunten', 1, 1),
		('Algemene opmerkingen', 1, 1);

INSERT INTO tblPersoon(PER_voornaam_naam, per_geboortedatum, PER_email, PER_geslacht, PER_gsmnummer, PER_adres)
VALUES	('Jef Anthonissen', '1995-08-19' , 'jef.anthonissen@realdolmen.com', 0, '0478468401', 'Kapellestraat 5 9402 Meerbeke'), 
		('Valon Xhafa', '1995-08-19', 'valon.xhafa@realdolmen.com', 0, '0478652512', 'Beerselsestraat 138 1651 Lot'),
		('Mahmoud Karoui', '1995-08-19', 'mahmoud.karoui@realdolmen.com', 0, '0452126585', 'Kerkstraat 109 1500 Halle'),
		('Jens Hernalsteen', '1995-08-19', 'jens.hernalsteen@realdolmen.com', 0, '0412659586', 'Solheidestraat 37 1653 Dworp'),
		('David Hemmerijckx', '1995-08-19', 'david.hemmerijckx@realdolmen.com', 0, '+32486659587', 'Kerkstraat 109 1500 Halle'),
        ('Joren Uitzetter', '1989-08-19', 'joren.uitzetter@realdolmen.com', 0, '0485545485', 'Kerkstraat 109 1500 Halle'),
		('Erik De Romagnoli', '1995-08-19', 'erik.deromagnoli@realdolmen.com', 0, '0485599538', 'Kerkstraat 109 1500 Halle'), -- 7
		('Yvan Rooseleer', '1995-08-19', 'yvan.rooseleer@odisee.be', 0, '0474564576', 'Kerkstraat 109 1500 Halle'),
        ('Dieter Haerinck', '1944-08-19', 'dieter.haerinck@realdolmen.com', 0, '0474564576', 'Kerkstraat 109 1500 Halle'),
        ('Test Stagiair 1', '1944-08-19', 'test.stagiair1@realdolmen.com', 0, '0451125789', ''), -- id 10
        ('Test Stagiair 2', '1944-08-19', 'test.stagiair2@realdolmen.com', 0, '0451125789', ''),
        ('Test Stagiair 3', '1944-08-19', 'test.stagiair3@realdolmen.com', 0, '0451125789', ''),
        ('Test Stagiair 4', '1944-08-19', 'test.stagiair4@realdolmen.com', 1, '0451125789', ''),
        ('Test Stagiair 5', '1944-08-19', 'test.stagiair5@realdolmen.com', 1, '0451125789', ''),
        ('Test Stagiair 6', '1944-08-19', 'test.stagiair6@realdolmen.com', 1, '0451125789', ''),
        ('Test Stagiair 7', '1944-08-19', 'test.stagiair7@realdolmen.com', 0, '0451125789', ''),
        ('Test Stagiair 8', '1944-08-19', 'test.stagiair8@realdolmen.com', 0, '0451125789', ''),
        ('Test Stagiair 9', '1944-08-19', 'test.stagiair9@realdolmen.com', 0, '0451125789', ''),
        ('Test Stagiair 10', '1944-08-19', 'test.stagiair10@realdolmen.com', 0, '0451125789', ''),
        ('Karolien Emrechts', '1944-08-19', 'karolienemrechts@realdolmen.com', 1, '0451125789', ''), -- id 20
        ('Nicolaas Maartens ', '1944-08-19', 'nicolaasMaartens@realdolmen.com', 0, '0451125789', ''), -- 21
        ('Colson Smith', '1944-08-19', 'colson.smith@realdolmen.com', 0, '0451125789', ''), -- 22
        ('Test Stagiair 14', '1944-08-19', 'test.stagiair10@realdolmen.com', 0, '0451125789', ''),
        ('Test Stagiair 15', '1944-08-19', 'test.stagiair11@realdolmen.com', 0, '0451125789', ''),
        ('Test Stagiair 16', '1944-08-19', 'test.stagiair12@realdolmen.com', 0, '0451125789', ''),
        ('Test Begeleider 1', '1944-08-19', 'test.begeleider1@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 2', '1944-08-19', 'test.begeleider2@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 3', '1944-08-19', 'test.begeleider3realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 4', '1944-08-19', 'test.begeleider4@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 5', '1944-08-19', 'test.begeleider5@realdolmen.com', 0, '0489651247', ''), -- id 30
        ('Test Begeleider 6', '1944-08-19', 'test.begeleider6@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 7', '1944-08-19', 'test.begeleider7@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 8', '1944-08-19', 'test.begeleider8@realdolmen.com', 1, '0489651247', ''),
        ('Test Begeleider 9', '1944-08-19', 'test.begeleider9@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 10', '1944-08-19', 'test.begeleider10@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 11', '1944-08-19', 'test.begeleider11@realdolmen.com', 1, '0489651247', ''),
        ('Test Begeleider 12', '1944-08-19', 'test.begeleider12@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 13', '1944-08-19', 'test.begeleider13@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 14', '1944-08-19', 'test.begeleider14@realdolmen.com', 1, '0489651247', ''),
        ('Test Begeleider 15', '1944-08-19', 'test.begeleider15@realdolmen.com', 0, '0489651247', ''), -- id 40
        ('Test Begeleider 16', '1944-08-19', 'test.begeleider16@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 17', '1944-08-19', 'test.begeleider17@realdolmen.com', 0, '0489651247', ''),
        ('Test Begeleider 18', '1944-08-19', 'test.begeleider18@realdolmen.com', 0, '0489651247', ''), -- 43
        ('Test Begeleider 19', '1942-05-24', 'test.begeleider19@realdolmen.com', 1, '0485566532', ''),
        ('Ruben Walraevens', '1996-05-24', 'ruben.walraevens@realdolmen.com', 0, '0485599538', ''), -- 45
        ('Simon Pollé', '1996-05-24', 'simon.pollé@realdolmen.com', 0, '0485599538', ''), -- 46
        ('Stijn Serruys', '1996-05-24', 'stijn.serruys@realdolmen.com', 0, '0485599538', ''), -- 47
        ('Sophie Moerman', '1996-05-24', 'sophie.moerman@realdolmen.com', 1, '0485599538', ''), -- 48
        ('Olivier Cappelle', '1980-05-24', 'olivier.cappelle@realdolmen.com', 0, '0485599545', ''), -- 49
        ('Wesley Van Malcot', '1993-05-24', 'wesley.vanmalcot@realdolmen.com', 0, '+32486695123' , ''), -- 50 begeleider Ruben
        ('Sasa Berberovic', '1990-05-24', 'sasa.berberovic@realdolmen.com', 0, '+324965874', ''); -- 51 begeleider stijn & simon
        		


INSERT INTO tblSchool(SCHO_naam, SCHO_adres, SCHO_ID_contactpersoon, SCHO_contactnummer)
VALUES	('Odisee campus Brussel', 'Warmoesberg 26, 1000 Brussel', 8, '0474564576'),
		('Erasmus Hogeschool Brussel', 'Nijverheidskaai 170, 1070 Anderlecht', NULL, '+32 (0)2 523 37 37'),
        ('Artesis Hogeschool Antwerpen', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Katholieke Universiteit Leuven', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Universiteit Hasselt', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Universiteit Antwerpen', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Universiteit Gent', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Koninklijke Universiteit Leuven', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Universiteit Kent', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Thomas More Mechelen', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Vlerick Business School', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Orpheus instituut', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Lucas School Of Arts', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Katholieke Hogeschool Vives', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Hogeschool Gent', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Hogeschool PXL', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Hogeschol Sint-Lukas Brussel', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Karel de Grote Hogeschool', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Europacollege', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Hogeschool West-Vlaanderen', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Katholieke Hogeschool Sint-Lieven', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Koninklijke Militaire School', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Transnationale Universiteit Limburg', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37'),
        ('Vesalius College', 'eenstraat 25, 6666 eenstad ', NULL, '+32 (0)2 523 37 37');
        


INSERT INTO tblRol(ROL_naam, ROL_benaming)
VALUES	('ROLE_STAGECOORDINATOR', 'Stagecoördinator'),
		('ROLE_BEGELEIDER', 'Begeleider'),
		('ROLE_HR', 'Human Resources'),
		('ROLE_GUEST', 'Guest');


INSERT INTO tblUseraccount(ACC_ID_persoon, ACC_ID_rol, ACC_creation_date, ACC_update_date, ACC_email, ACC_wachtwoord, ACC_secret_question, ACC_secret_answer, ACC_active)
VALUES	(5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'david.hemmerijckx@realdolmen.com', '$2a$10$OloJMSDRUYW/.3S0TtFs6.zs/l//Z2gmEEvjdPrhEIDxyxU3uHtZW', '', '', 1),
		(7, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'erik.deromagnoli@realdolmen.com', '$2a$10$OloJMSDRUYW/.3S0TtFs6.zs/l//Z2gmEEvjdPrhEIDxyxU3uHtZW', '', '', 1),
        (6, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'joren.uitzetter@realdolmen.com', '$2a$10$xmo/DosmQNyr6BVTOPoqaODyCh698jap/UdqY5fstA6Uqp6HD8t7S', '', '', 1),
        (49, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ollivier.cappelle@realdolmen.com', '$2a$10$OloJMSDRUYW/.3S0TtFs6.zs/l//Z2gmEEvjdPrhEIDxyxU3uHtZW', '', '', 1), -- begeleider Sophie
        (50, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'wesley.vanmalcot@realdolmen.com', '$2a$10$OloJMSDRUYW/.3S0TtFs6.zs/l//Z2gmEEvjdPrhEIDxyxU3uHtZW', '', '', 1), -- begeleider Ruben
        (51, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'sasa.berberovic@realdolmen.com', '$2a$10$OloJMSDRUYW/.3S0TtFs6.zs/l//Z2gmEEvjdPrhEIDxyxU3uHtZW', '', '', 1); -- begeleider Stijn & simon



INSERT INTO tblStagiair(STA_ID_school, STA_ID_persoon, STA_studierichting)
VALUES	(1, 1, 'Toegepaste Informatica'), -- Jef
		(1, 2, 'Toegepaste Informatica'), -- Valon
		(1, 3, 'Toegepaste Informatica'), -- Mahmoud
		(1, 4, 'Toegepaste Informatica'), -- Jens
        (2, 10, 'ICT'),
        (3, 11, 'Handelswetenschappen'),
        (4, 12, 'Communicatiewetenschappen'),
        (5, 13, 'Informatica'),
        (6, 14, '/'),
        (7, 16, 'Informaticabeheer'), -- 10
        (8, 17, 'Lerarenopleiding Informatica'),
        (9, 18, 'Toegepaste Informatica'),
        (10, 19, 'ICT'),
        (4, 20, 'ICT'), -- 14 Karolien
        (4, 21, 'ICT'), -- 15 nicolaas
        (4, 22, 'ICT'), -- 16 Jefferson Bikerboy 
        (14, 23, 'ICT'),
        (15, 24, 'ICT'),
        (16, 25, 'ICT'),
        (2, 45, 'Toegepaste Informatica' ), -- 20 ruben
        (2, 46, 'Toegepaste Informatica'), -- 21 simon
        (2, 47, 'Toegepaste Informatica'), -- 22 stijn stino
        (2, 48, 'Toegepaste Informatica'); -- 23 sophie
        
        

INSERT INTO tblCategorie(CAT_naam)
VALUES	('Java Competence Center'),
		('.NET - Microsoft Competence Center'),
		('Engaged Workplace'),
		('Big Data - Microsoft Data Insights');
        
INSERT INTO tblStageopdracht(OPDR_ID_categorie, OPDR_naam, OPDR_omschrijving, OPDR_scope, OPDR_competenties, OPDR_locatie)
VALUES 	(1, 'Internship Follow-up Tool', '<p>Een stage is voor een student de ideale gelegenheid om de nodige projectervaring op te doen, kennis te verwerven en zijn/haar competenties aan te scherpen. Elk jaar opnieuw biedt Realdolmen tal van stageopdrachten aan die door studenten van scholen over het hele land uitgewerkt worden. &nbsp;</p><p><br></p><p>Om de opvolging van onze stages te optimaliseren, dient een tool uitgewerkt te worden die de gegevens op een centrale plek bewaart. Deze tool zal uitgewerkt worden in de vorm van een webapplicatie die onder meer informatie zal weergeven over:</p><ul><li>Stageopdrachten</li><li>Stagebegeleiders</li><li>Stagiairs</li><li>Scholen</li></ul><p>Bovenvermelde entiteiten moeten onderling ook gerelateerd kunnen worden. Zo kan een stagiair aan een school studeren, kan hij een opdracht uitwerken en kan hij begeleid worden door een stagebegeleider. De begeleider kan op zijn beurt dan weer andere stagiairs begeleiden of begeleid hebben in het verleden. Bovendien moet de begeleider een evaluatieformulier over de stagiair en het verloop van de stage kunnen invullen.</p><p><br></p><p>Op basis van de ingevoerde gegevens en hun onderlinge relaties, dienen er statistieken opgesteld te worden die aan de hand van interactieve grafieken en diagrammen weergegeven worden in de webapplicatie. Verder moet de applicatie zichzelf aanpassen aan verschillende soorten resoluties die eigen zijn aan het type client. Zo zal op een desktop meer getoond kunnen worden dan op een smartphone.</p><p><br></p><p><u>Functioneel</u></p><ul><li>Aanmaken, beheren en rekateren van:&nbsp;<ul><li data-empty="true">Stageopdrachten</li><li data-empty="true">Stagebegeleiders</li><li data-empty="true">Stagiairs</li><li data-empty="true">Scholen</li></ul></li><li>Evaluatieformulier voor stagiair, ingevuld door begeleider</li><li>Opstellen van statistieken en deze weergeven aan de hand van interactieve grafieken en diagrammen</li><li>Responsive web design ter ondersteuning van verschillende soorten clients (desktop, smartphone, tablet)</li></ul>'
		, '<ul><li>Opstellen functionele en technische documenten<ul><li>Functionele analyse</li><li>Technische analyse</li><li>Architectuur document</li></ul></li><li>Ontwikkelen van de applicatie<ul><li>Java Enterprise Edition 7 back en front end</li></ul></li><li>Presentatie van het resultaat</li></ul>'
        , '<p><strong>Welke kennis en competenties ontwikkel je met deze opdracht?</strong></p><ul><li>Analyse</li><li>Project planning, uitvoering en opvolging</li><li>Documentatie(technisch schrijven)</li><li>Testing</li><li>Java EE7</li><li>Primefaces</li><li>Maven</li><li>MySQL</li><li>Wildfly</li></ul><p><br></p><p><strong>Welke competenties heb je nodig?</strong>&nbsp;</p><ul><li>Goede kennis van Java&nbsp;</li><li>Brede technologische interesse&nbsp;</li><li>Voldoende analytische skills&nbsp;</li><li>Energie halen uit het oplossen van problemen&nbsp;</li><li>Zowel zelfstandig als in teamverband kunnen werken</li></ul>'
        , 'Huizingen, Kontich'),
		(1, 'Road pricing calculator', 'Een tool voor het berekenen van de kostprijs van bepaalde trajecten', 'back-end + front-end', 'Zelfstandig werken', 'Huizingen'),
        (2
        ,'cTracker'
        , 'Realdolmen heeft een webapplicatie cTracker die intern gebruikt wordt voor opvolging van certificaten van onze consultants. Via de applicatie kan op ieder moment gezocht worden naar profielen die in het bezit zijn van een bepaald certificaat. Na een succesvolle stage hebben wij hier reeds een back-end systeem voor ontwikkeld die data gaat ophalen van verschillende vendoren en in ons systeem inbrengt via “Workers”. Het volgende luik van deze applicatie bestaat uit het ontwikkelen van een website die inplugt op de bestaande API. Ook willen we enkele nieuwe features implementeren/verder uitwerken tijdens de ontwikkeling van de websites.'
        , 'Opstellen technische documenten (Technische analyse, Architectuur document). Het bouwen van een secure, goed ogende en gebruiksvriendelijke (UI) webapplicatie (HTML5 & Angular). Het verder uitbouwen van Azure webjobs die data scrapen en de databank opvullen. Leren werken in aan real-world project context, volgens de Agile methodologie'
        , 'Brede technologische interesse & leergierig. Kennis van .NET & C#. Kennis van ASP.NET WebAPI. Kennis van HTML/CSS & javascript. Kennis van Angular en bootstrap. Goeie UI-skills (gebruiksvriendelijke en mooi ogende UI kunnen maken). Zelfstandig en in team kunnen werken. Beschikken over analytische skills'
        , 'Huizingen'),
        (3, 'Stageopdracht dummy 1', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (4, 'Stageopdracht dummy 2', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (4, 'Stageopdracht dummy 3', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (3, 'Stageopdracht dummy 4', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (4, 'Stageopdracht dummy 5', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (3, 'Stageopdracht dummy 6', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (3, 'Stageopdracht dummy 7', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'), -- 10
        (3, 'Stageopdracht dummy 8', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (4, 'Stageopdracht dummy 9', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (4, 'Stageopdracht dummy 10', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (4, 'Stageopdracht dummy 11', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (3, 'Stageopdracht dummy 12', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'), -- 15
        (3, 'Stageopdracht dummy 13', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (4, 'Stageopdracht dummy 14', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (4, 'Stageopdracht dummy 15', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (3, 'Stageopdracht dummy 16', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (4, 'Stageopdracht dummy 17', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'), -- 20
        (4, 'Stageopdracht dummy 18', 'Een stageopdracht dummy', 'analyse, back-end en front-end', 'zelfstandig werken, ...', 'Huizingen'),
        (1, 'Mobiele tijdsregistratie gebruikmakende van geofencing en iBeacons'
        , '<p>Een correct registratie van de gepresteerde uren is voor veel bedrijven, waaronder Realdolmen, een zeer belangrijke kwestie. Deze uren zijn de basis om facturatie te kunnen doen en op deze manier inkomsten te vergaren uit geleverde diensten. Onder deze tijdsregistratie verstaan we het proces van het registreren, ook wel bijhouden, van uren die werknemers aan een bepaalde taak besteden met als doelen:</p><ul><li>Inzichtelijk maken hoe lang een bepaalde taak gemiddeld duurt</li><li>Inzichtelijk maken hoe duur het uitvoeren van een bepaald proces of taak is</li><li>Inzichtelijk maken waaraan een medewerker (of medewerkers in het algemeen) hun tijd besteden</li><li>Registreren van de aanwezig- en afwezigheid van medewerkers</li><li>Verantwoorden van werkuren ten behoeve van een subsidie of facturatie</li><li>Hoe vaak en hoe lang is iemand op vakantie geweest</li><li>Berekenen van het (uur)loon</li></ul><p><br></p><p>De nauwkeurigheid van de ingaven door de werknemers is dus van groot belang voor zowel de facturatie maar ook voor de opvolging door het management of project verantwoordelijken. Om deze gegevens op een correcte manier te gaan capteren, dachten wij eraan om een smartphone app te ontwikkelen op het Android besturingssysteeem.</p><p>Naast het manueel kunnen aanvullen van tijdsregistraties is het de bedoeling dat de gebruiker zoveel mogelijk geholpen wordt. Zo zal aan de hand van geofencing en/of iBeacon technologie getrackt kunnen worden wanneer de smartphone gebruiker op het werk arriveert en terug vertrekt. Deze tijdstippen kunnen uitgelezen worden in de app en als suggestie gebruikt worden.</p><p>De ingegeven timesheets zullen na goedkeuring van de gebruiker via een beveiligde verbinding doorgestuurd worden naar een centrale server. Via een te ontwikkelen back end applicatie zullen de projectverantwoordelijken en het management deze uren kunnen verifi&euml;ren en rapportering opvragen.</p><p>Volgende opsomming van taken is niet-exhaustief en geeft bijgevolg enkel een aantal van de meest elementaire functionaliteiten weer. Een verdere functionele analyse kan mogelijks bijkomende requirements en functionaliteiten aankaarten.</p>'
        , '<ul><li>Opstellen en uitbreiden van functionele en technische documenten<ul><li>Analyse</li><li>Korte studie over de gebruikte technieken en beschrijving van de gemaakte technische keuzes &nbsp;</li></ul></li><li>Software ontwikkeling<ul><li>Android applicatie<ul><li>Login</li><li>Toevoegen/verwijderen van projecten aan eigen user profiel</li><li>Ingeven/aanpassen tijdregistraties</li><li>Suggesties op basis van geofencing/iBeacon data</li><li>Bevestigen van geleverde prestaties</li><li>Overzicht eigen prestaties per maand/week/dag</li></ul></li><li>Java Enterprise Edition back end<ul><li>RESTful web service die de doorgestuurde timesheet capteert</li><li>Login module en user beheer</li><li>Beheer projecten en ingegeven prestaties</li><li>Rapportering per project en user</li></ul></li></ul></li><li>Presentatie van het resultaat / sales pitch</li></ul>'
        , '<p><strong>Welke kennis en competenties ontwikkel je met deze opdracht?</strong></p><ul><li>Analyse</li><li>Project planning, uitvoering en opvolging (Agile/Scrum project approach)</li><li>Testing (Unit, integratie, performantie, &hellip;)&nbsp;</li><li>Java back end en front end technologie&euml;n (JEE: JSF, JPA, JAX-RS, &hellip;)&nbsp;</li><li>Android applicatieontwikkeling</li><li>Back end integratie (door middel van RESTful web services)</li><li>Data interchange formats (XML, JSON)</li></ul><p><br></p><p><strong>Welke competenties heb je nodig?</strong></p><ul><li>Basiskennis van Java Enterprise Edition (front end en back end)</li><li>Basiskennis van Android ontwikkeling</li><li>Zowel zelfstandig als in teamverband kunnen werken</li><li>Voldoende analytische skills  Stage termijn van minstens 2 maanden</li><li>Bezit van een moderne Android smartphone (versie 4+) is een plu</li></ul>'
        , 'Huizingen'), -- stijn en simon 22
        (1, 'Digital personal development plan '
        , '<p>Iedereen heeft een bepaald carri&egrave;repad voor ogen waarin hij/zij bepaalde doelstellingen op korte en/of lange termijn wilt verwezenlijken. Deze doelstellingen gaan vaak hand in hand met bepaalde ambities die men wenst waar te maken. Binnen Realdolmen juichen we dit ten zeerste toe: je kan investeren in je eigen carri&egrave;re en je manager steunt je daar ook in. Groei is een belangrijk onderdeel van onze visie en je krijgt ook de kansen en ruimte om dit te doen.</p><p><br></p><p>Om aan te geven wat je ambities zijn en welke doelstellingen je wenst te realiseren, maken we bij Realdolmen gebruik van een persoonlijk ontwikkelingsplan dat vertrekt vanuit je huidige situatie: wat doe je graag, wat kan je goed, waar liggen je verbeterpunten? Van daaruit kan je beginnen nadenken over hoe je de toekomst ziet, zoals de functie naar waar je wilt doorgroeien. Met het oog op de verwezenlijking van je ambities kan je dan aangeven welke opleidingen je wilt volgen, welke certificaten je wilt behalen, welke conferenties of events je wilt bijwonen, etc. Dit ontwikkelingsplan vormt vervolgens een goede basis om minstens &eacute;&eacute;n keer per jaar het gesprek aan te gaan met je manager over je evolutie.</p><p><br></p><p>Momenteel bestaat dit persoonlijk ontwikkelingsplan in de vorm van een Word template die elke werknemer invult en vervolgens doorstuurt naar zijn/haar manager. Om dit proces te optimaliseren dient een webapplicatie uitgewerkt te worden. Dit laat ons ineens toe om een aantal functionaliteiten te voorzien die momenteel niet mogelijk zijn.</p><p><br></p><p><u>Functioneel</u></p><ul><li>Digitaliseren van het persoonlijk ontwikkelingsplan</li><li>Bouwen van extra functionaliteiten</li><li>Opstellen van statistieken en deze weergeven aan de hand van interactieve grafieken en diagrammen&nbsp;</li><li>Responsive web design ter ondersteuning van verschillende soorten clients (desktop, smartphone, tablet)</li></ul>'
        , '<ul><li>Opstellen functionele en technische documenten<ul><li>Functionele analyse</li><li>Technische analyse</li><li>Architectuur document</li></ul></li><li>Ontwikkelen van de applicatie<ul><li>Angular 2 front end</li><li>Java Enterprise Edition 7 back end</li></ul></li><li>Presentatie van het resultaat</li></ul>'
        , '<p><strong>Welke kennis en competenties ontwikkel je met deze opdracht?</strong></p><ul><li>Analyse</li><li>Project planning, uitvoering en opvolging (Scrum)</li><li>Java back end technologie&euml;n (Java Enterprise Edition 7)</li><li>JavaScript front end technologie&euml;n (Angular 2)</li><li>Testing (JUnit, Mockito)</li><li>Performantie testing</li><li>Documentatie schrijven</li><li>Back end integratie</li><li>D3.js/C3.js</li><li>Web services (REST)</li><li>Data interchange formats (JSON</li></ul><p><br></p><p><strong>Welke competenties heb je nodig?</strong></p><ul><li>Brede technologische interesse</li><li>Goede kennis van Java (back end) en Javascript (front end)</li><li>Zelfstandig kunnen werken</li><li>Voldoende analytische skills</li></ul>'
        , 'Huizingen'), -- ruben 23 
        (1, 'Internal corporate news web app'
        , '<p>Vandaag communiceert een bedrijf via allerlei kanalen met zijn werknemers: mail, interne (web)applicaties, intranet site, etc. De bedoeling van deze nieuw te ontwikkelen &ldquo;nieuws hub webapp&rdquo; is dat deze kan dienen als centraal communicatiemedium. Via deze applicatie zal een werknemer niet alleen algemeen nieuws over de organisatie kunnen opzoeken maar ook gepersonaliseerde boodschappen ontvangen. Verder kan hij ook op de hoogte gehouden worden van events die doorgaan of interne vacatures die geopend worden.</p><p>Deze nieuwe manier van werken moet een gebruiker in staat stellen om via verschillende toestellen (desktop, smartphone, tablet, ...) aan zijn informatie te komen. Daarom kijken we naar een cross-platform oplossing.</p><p>Concreet zullen we deze applicatie uitwerken voor Realdolmen, maar het is de bedoeling dat het opzet herbruikbaar is voor eventuele andere klanten.</p><p><br></p><p><u>Functioneel</u></p><p>De te ontwikkelen applicatie zal onder meer over volgende eigenschappen beschikken:</p><ul><li>Interne nieuwsaankondigingen, flashes weergeven</li><li>Calendar tonen met interne events</li><li>Weergeven van interne vacatures</li><li>Eventueel persoonlijke berichten laten zien</li><li>Te gebruiken op allerlei soorten toestellen (desktop, smartphone, tablet)</li></ul>'
        , '<ul><li>Opstellen functionele en technische documenten<ul><li>Functionele analyse</li><li>Technische analyse</li><li>Architectuur document</li></ul></li><li>Ontwikkelen van de applicatie<ul><li>Angular 2 front end&nbsp;</li><li>Java Enterprise Edition 7 back end</li></ul></li><li>Presentatie van het resultaat</li></ul>'
        , '<p><strong>Welke kennis en competenties ontwikkel je met deze opdracht?</strong></p><ul><li>Analyse</li><li>Project planning, uitvoering en opvolging (Scrum project approach)</li><li>Java back end technologie&euml;n (Java Enterprise Edition 7)</li><li>Web technologie&euml;n (Angular, jQuery)  Testing&nbsp;</li><li>Performantie testing&nbsp;</li><li>Documentatie schrijven</li><li>Back end integratie&nbsp;</li><li>Web services (REST, SOAP)</li><li>Data interchange formats (XML, JSON)</li></ul><p><br></p><p><strong>Welke competenties heb je nodig?</strong></p><ul><li>Brede technologische interesse</li><li>Goede kennis van Java (back end) en Javascript (front end)</li><li>Zelfstandig kunnen werken</li><li>Voldoende analytische skills</li></ul>'
        , 'Huizingen'); -- sophie 24
        
        
INSERT INTO tblStage(STAGE_ID_opdracht, STAGE_ID_stagiair, STAGE_start_datum, STAGE_eind_datum )
VALUES	(1, 1, '2017-02-13' , '2017-05-24' ), /* Jef */
		(1, 2, '2017-02-20' , '2017-05-31'), /* Valon */
		(1, 4, '2017-02-20', '2017-05-31'), /* Jens */
		(3, 3, '2017-02-13', '2017-05-12'), /* mag moet */
        (5, 5, '2017-02-13', '2017-05-12'), /* Johns */
        (5, 6, '2017-02-13', '2017-05-12'),
        (5, 7, '2017-02-13', '2017-05-12'),
        (5, 8, '2017-02-13', '2017-05-12'),
        (5, 9, '2017-02-13', '2017-05-12'),
        (5, 10, '2017-02-13', '2017-05-12'), -- 10
        (5, 11, '2017-02-13', '2017-05-12'),
        (5, 12, '2017-02-13', '2017-05-12'),
        (5, 13, '2017-02-13', '2017-05-12'),
        (5, 14, '2017-02-13', '2017-05-12'),
        (5, 15, '2017-02-13', '2017-05-12'), -- 15
        (5, 16, '2017-02-13', '2017-05-12'),
        (5, 17, '2017-02-13', '2017-05-12'),
        (5, 18, '2017-02-13', '2017-05-12'),
        (5, 19, '2017-02-13', '2017-05-12'),
        (23, 20, '2017-02-13', '2017-05-24'), -- 20 Ruben / IFT
        (22, 21, '2017-02-13', '2017-05-24'), -- 21 simon / IFT
        (22, 22, '2017-02-13', '2017-05-24'), -- 22 stijn / IFT
        (24, 23, '2017-02-13', '2017-05-24'), -- 23 Sophfie / IFT
        (1, 14, '2017-02-13', '2017-05-12'), -- 24 kareloins / IFT
        (1, 15, '2017-02-13', '2017-05-12'), -- 25 nicolaas / IFT
        (1, 16, '2017-02-13', '2017-05-12'); -- 26 bikerboi / IFT
	
        

 INSERT INTO tblBegeleider(BEG_ID_categorie, BEG_functie_omschrijving, BEG_ID_persoon)
 VALUES (1, 'Java consultant, Stagecoördinator', 5),
		(1, 'Java consultant, Stagebegeleider', 6),
        (2, '.NET consultant, Stagebegeleider', 9),
        (2, '.NET consultant, Stagebegeleider', 26),
        (2, '.NET consultant, Stagebegeleider', 27),
        (2, '.NET consultant, Stagebegeleider', 28),
        (2, '.NET consultant, Stagebegeleider', 29),
        (2, '.NET consultant, Stagebegeleider', 30),
        (2, '.NET consultant, Stagebegeleider', 31),
        (2, '.NET consultant, Stagebegeleider', 32), -- 10
        (2, '.NET consultant, Stagebegeleider', 33),
        (2, '.NET consultant, Stagebegeleider', 34),
        (2, '.NET consultant, Stagebegeleider', 35),
        (2, '.NET consultant, Stagebegeleider', 36),
        (2, '.NET consultant, Stagebegeleider', 37), -- 15
        (2, '.NET consultant, Stagebegeleider', 38),
        (2, '.NET consultant, Stagebegeleider', 39),
        (2, '.NET consultant, Stagebegeleider', 40),
        (2, '.NET consultant, Stagebegeleider', 41),
        (2, '.NET consultant, Stagebegeleider', 42), -- 20
        (2, '.NET consultant, Stagebegeleider', 43),
        (4, 'Microsoft Data Insights Big Data consultant, Stagebegeleider', 44),
        (1, 'Java consultant, Stagebegeleider', 49), -- 23 ollivier
        (1, 'Java consultant, Stagebegeleider', 50), -- 24 Wesley Van Malcot
        (1, 'Java consultant, Stagebegeleider', 51); -- 25 Sasa Berberovic 
        
INSERT INTO tblBegeleiding(BEGL_ID_begeleider, BEGL_ID_stage)
VALUES 	(2, 1),
		(2, 2),
        (2, 3),
        (3, 4),
        (23, 23), -- Ollivier
        (24, 20), -- Wesley
        (25, 21), -- Sasa
        (25, 22); -- Sasa


INSERT INTO tblEvaluatieformulier(EVAL_ID_template, EVAL_ID_stagiair, EVAL_gemiddelde_score, EVAL_ID_begeleider ,EVAL_datum)
VALUES (1, 1, 4, 2, now()), -- Jef JAVA / Odisee
		(1, 2, 3, 2, now()), -- Valon JAVA / Odisee
        (1, 3, 4.5, 1, now()), -- Mahmoud .NET / Odisee
        (1, 4, 5, 2, now()), -- Jens Java / Odisee
        (1, 20, 5, 1, now()), -- Ruben JAVA / ERasmus 5
        (1, 21, 2, 1, now()), -- Simon Java * erasmus
        (1, 22, 3, 1, now()), -- stijn java / erasmus
        (1, 23, 4.5, 1, now()), -- sophie java / erasmus
        (1, 14, 4, 1, now()), -- karolien java / KUL
        (1, 15, 2.5, 1, now()), -- Nicolaas java / KUL
        (1 , 16, 1, 1, now()); -- Jefferson Bikerboy java / KUl
        
		
        
INSERT INTO tblantwoord(ANTW_ID_evalformulier, ANTW_ID_vraag, ANTW_ID_schaal, ANTW_voluit)
VALUES 	(1, 1, 4, ""),
		(1, 2, 3, ""),
        (1, 3, 3, ""),
        (1, 4, 5, ""),
        (1, 5, 5, ""),
        (1, 6, 3, ""),
        (1, 7, 5, ""),
        (1, 8, 4, ""),
        (1, 9, 4, ""),
        (1, 10, 3, ""),
        (1, 11, 5, ""),
        (1, 12, 5, ""),
        (1, 13, 4, ""),
        (1, 14, 2, ""),
        (1, 15, 5, ""),
        (1, 16, 4, ""), 
        (1, 17, 3, ""),
        (1, 18, 5, ""),
        (1, 19, 4, ""),
        (1, 20, 5, ""),
        (1, 21, 3, ""),
        (1, 22, 5, ""),
        (1, 23, 2, ""),
        (1, 24, 4, ""),
        (1, 25, 5, ""),
        (1, 26, 5, ""),
        (1, 27, 3, ""),
        (1, 28, 4, ""),
        (1, 29, 5, ""),
        (1, 30, null, "Goede en rappe werker"),
        (1, 31, null, "Geen"),
        (1, 32, null, "Mooi eindresultaat");
        

        

