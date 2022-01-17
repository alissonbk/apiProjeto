INSERT INTO endereco(id, cep, numero, rua, bairro, cidade, estado)
VALUES(1, '96990000', 10, 'Av. Teste', 'Centro', 'Estrela Velha', 'RS');
INSERT INTO usuario(email, senha, ativo, nome, cpf, endereco_id, tipo)
VALUES('admin@email.com', '$2a$12$mrI/C.5ZyMUB9VmETKX/r.IGT2jyiJK41gc0snfHnKQ1dr9T78VmW', true, 'Alisson', '93804787126', 1, 'ADMIN');