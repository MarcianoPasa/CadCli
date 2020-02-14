CREATE TABLE IF NOT EXISTS CLIENTES (
    CODIGO_CLIENTE INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    NOME_CLIENTE VARCHAR (256) NOT NULL,
    RUA VARCHAR (256),
    NUMERO VARCHAR (15),
    COMPLEMENTO VARCHAR (100),
    CEP INTEGER,
    BAIRRO VARCHAR (40),
    CIDADE VARCHAR (80),
    UF VARCHAR (2)
);

CREATE TABLE CONFIGURACOES (
    CODIGO_CONFIGURACAO INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
    MODULO VARCHAR (50) NOT NULL,
    CONFIGURACAO VARCHAR (50) NOT NULL,
    VALOR_CONFIGURACAO VARCHAR (50) NOT NULL
);

INSERT INTO CONFIGURACOES (
  MODULO,
  CONFIGURACAO,
  VALOR_CONFIGURACAO
)
VALUES (
  'Clientes',
  'UFPadrao',
  ''
);

INSERT INTO CONFIGURACOES (
  MODULO,
  CONFIGURACAO,
  VALOR_CONFIGURACAO
)
VALUES (
  'Clientes',
  'CidadePadrao',
  ''
);