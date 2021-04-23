CREATE TABLE Biler (
    merke VARCHAR(10) NOT NULL,
    type VARCHAR(10) NOT NULL,
    PRIMARY KEY (type)
);

CREATE TABLE Motorvogn (
    id SMALLINT NOT NULL AUTO_INCREMENT,
    personnr CHAR(11) NOT NULL,
    navn VARCHAR(30) NOT NULL,
    adresse VARCHAR(50) NOT NULL,
    kjennetegn VARCHAR(10) NOT NULL,
    type VARCHAR(10) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (type) references Biler(type)
);

CREATE TABLE Kunde(
    brukernavn varchar(20) not null primary key,
    passord varchar(20) not null
);
