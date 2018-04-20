DROP TABLE IF EXISTS User ;
CREATE TABLE User (UserId_User INT AUTO_INCREMENT NOT NULL,
FirstName_User TEXT,
LastName_User TEXT,
NickName_User TEXT,
Email_User TEXT,
MemberSince_User TEXT,
Login_User TEXT,
Pass_User TEXT,
EducationLevel_User ENUM,
PRIMARY KEY (UserId_User)) ENGINE=InnoDB;

DROP TABLE IF EXISTS Question ;
CREATE TABLE Question (QuestionId_Question INT AUTO_INCREMENT NOT NULL,
Title_Question TEXT,
Question_Question TEXT,
State_Question BOOLEAN,
Views_Question INT,
likes_Question INT,
UserId_User **NOT FOUND**,
PRIMARY KEY (QuestionId_Question)) ENGINE=InnoDB;

DROP TABLE IF EXISTS Answer ;
CREATE TABLE Answer (AnswerId_Answer INT AUTO_INCREMENT NOT NULL,
Message_Answer TEXT,
Upvotes_Answer INT,
DownVOtes_Answer INT,
PRIMARY KEY (AnswerId_Answer)) ENGINE=InnoDB;

DROP TABLE IF EXISTS Tag ;
CREATE TABLE Tag (TagId_Tag TEXT AUTO_INCREMENT NOT NULL,
Tag_Tag TEXT,
PRIMARY KEY (TagId_Tag)) ENGINE=InnoDB;

DROP TABLE IF EXISTS Contain ;
CREATE TABLE Contain (QuestionId_Question **NOT FOUND** AUTO_INCREMENT NOT NULL,
AnswerId_Answer **NOT FOUND** NOT NULL,
TagId_Tag **NOT FOUND** NOT NULL,
PRIMARY KEY (QuestionId_Question,
 AnswerId_Answer,
 TagId_Tag)) ENGINE=InnoDB;

ALTER TABLE Question ADD CONSTRAINT FK_Question_UserId_User FOREIGN KEY (UserId_User) REFERENCES User (UserId_User);

ALTER TABLE Contain ADD CONSTRAINT FK_Contain_QuestionId_Question FOREIGN KEY (QuestionId_Question) REFERENCES Question (QuestionId_Question);
ALTER TABLE Contain ADD CONSTRAINT FK_Contain_AnswerId_Answer FOREIGN KEY (AnswerId_Answer) REFERENCES Answer (AnswerId_Answer);
ALTER TABLE Contain ADD CONSTRAINT FK_Contain_TagId_Tag FOREIGN KEY (TagId_Tag) REFERENCES Tag (TagId_Tag);
