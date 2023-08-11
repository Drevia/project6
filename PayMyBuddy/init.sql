-- Création de la base de données "payBuddy"
CREATE DATABASE payBuddy;

-- Utilisation de la base de données "payBuddy"
USE payBuddy;

-- Création de la table "User"
CREATE TABLE User (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      email TEXT(255) NOT NULL,
                      nom TEXT NOT NULL,
                      prenom TEXT NOT NULL ,
                      mot_de_passe TEXT NOT NULL ,
                      balance DECIMAL(5,2) NOT NULL
);

CREATE TABLE Friendship (
                            user_origin_id INT NOT NULL ,
                            friend_id INT NOT NULL ,
                            FOREIGN KEY (user_origin_id) REFERENCES User(id),
                            FOREIGN KEY (friend_id) REFERENCES User(id),
                            PRIMARY KEY(user_origin_id, friend_id)

);
-- Création de la table "Transaction"
CREATE TABLE Transaction (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             giver_id INT NOT NULL,
                             receiver_id INT NOT NULL,
                             amount DECIMAL(5,2) NOT NULL,
                             description TEXT,
                             date DATE NOT NULL,
                             FOREIGN KEY (giver_id) REFERENCES User (id),
                             FOREIGN KEY (receiver_id) REFERENCES User (id)
);