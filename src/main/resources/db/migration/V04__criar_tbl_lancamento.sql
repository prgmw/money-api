CREATE TABLE lancamento (
   codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
   categoria_codigo BIGINT(20) NOT NULL,
   pessoa_codigo BIGINT(20) NOT NULL,
   descricao VARCHAR(100) NOT NULL,
   data_vencimento DATE NOT NULL,
   data_pagamento DATE,
   valor DECIMAL(10,2) NOT NULL,
   observacao VARCHAR(100),
   tipo VARCHAR(20) NOT NULL,
   FOREIGN KEY (categoria_codigo) REFERENCES categoria(codigo),
   FOREIGN KEY (pessoa_codigo) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

