create table category (id int AUTO_INCREMENT,cor varchar(255),titulo varchar(255),primary key (id));

INSERT INTO category(titulo,cor) VALUES ('FREE','');

create table users (id int AUTO_INCREMENT,log varchar(255) ,pass varchar(255),primary key (id), UNIQUE(log));

CREATE TABLE video (
  id int NOT NULL AUTO_INCREMENT,
  descricao varchar(255) NOT NULL,
  titulo varchar(255) NOT NULL,
  resource_locator varchar(255) NOT NULL,
  categoria_id int NOT NULL ,
  PRIMARY KEY (id),
  FOREIGN KEY (categoria_id) REFERENCES category(id)
  );