CREATE TABLE usuarios(
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         senha VARCHAR(100) NOT NULL,

                         PRIMARY KEY(id)
);