CREATE SCHEMA biblioteca_owner;

CREATE TABLE biblioteca_owner.usuarios (
	id SERIAL PRIMARY KEY,
	nome TEXT NOT NULL,
	email TEXT NOT NULL,
	data_cadastro TIMESTAMP NOT NULL, 
	telefone TEXT NOT NULL
);

CREATE TABLE biblioteca_owner.livros (
	id SERIAL PRIMARY KEY,
	titulo TEXT NOT NULL,
	autor TEXT NOT NULL,
	isbn TEXT NOT NULL,
	data_publicacao DATE NOT NULL,
	categoria TEXT NOT NULL -- Categoria principal do livro
);

CREATE TABLE biblioteca_owner.emprestimos (
	id SERIAL PRIMARY KEY,
	usuario_id INTEGER REFERENCES biblioteca_owner.usuarios NOT NULL,
	livro_id INTEGER REFERENCES biblioteca_owner.livros NOT NULL,
	data_emprestimo TIMESTAMP NOT NULL,
	data_devolucao TIMESTAMP NOT NULL,
	status TEXT NOT NULL
);
