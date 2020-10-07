CREATE TABLE endereco (
   codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
   pessoa_codigo BIGINT(20) NOT NULL,
   logradouro VARCHAR(100) NOT NULL,
   numero VARCHAR(10) NOT NULL,
   complemento VARCHAR(100),
   bairro VARCHAR(100) NOT NULL,
   cep VARCHAR(10) NOT NULL,
   cidade VARCHAR(100) NOT NULL,
   estado VARCHAR(2) NOT NULL,
   FOREIGN KEY (pessoa_codigo) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

