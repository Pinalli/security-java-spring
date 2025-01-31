INSERT INTO usuarios(nome, email, senha) VALUES ('Joao', 'joao@email.com', 'joao123');
INSERT INTO usuarios(nome, email, senha) VALUES ('Maria', 'maria@email.com', 'maria123');

update usuarios set senha='$2y$10$GE/i8ZvFAPuRU8Av7Bq4sO3/PbSYWon2MG2oscE2B7nxPwCEGI2yu'
where id=1;