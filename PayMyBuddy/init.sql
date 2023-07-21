-- Création de la base de données "payBuddy"
CREATE DATABASE payBuddy;

-- Utilisation de la base de données "payBuddy"
USE payBuddy;

-- Création de la table "User"
CREATE TABLE User (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      email TEXT,
                      nom TEXT,
                      prenom TEXT,
                      motDePasse TEXT,
                      BankAccountKey INT,
                      friends TEXT,
                      sold INT
);

-- Création de la table "BankAccount"
CREATE TABLE BankAccount (
                             id INT PRIMARY KEY,
                             Titutaire TEXT,
                             Rib TEXT
);

-- Création de la table "Transaction"
CREATE TABLE Transaction (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             giver INT,
                             receiver INT,
                             amount INT,
                             description TEXT,
                             date DATE,
                             FOREIGN KEY (giver) REFERENCES User (id),
                             FOREIGN KEY (receiver) REFERENCES User (id)
);

-- Ajout de la clé étrangère dans la table "User" vers la table "Account"
ALTER TABLE User
    ADD CONSTRAINT FK_User_Account
        FOREIGN KEY (BankAccountKey) REFERENCES BankAccount (id);