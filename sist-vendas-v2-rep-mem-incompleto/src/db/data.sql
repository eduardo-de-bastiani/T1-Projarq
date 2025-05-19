CREATE TABLE produto (
  id             BIGSERIAL   PRIMARY KEY,
  descricao      VARCHAR,
  preco_unitario NUMERIC
);

CREATE TABLE item_de_estoque (
  id          BIGSERIAL  PRIMARY KEY,
  produto_id  BIGINT     REFERENCES produto(id),
  quantidade  INTEGER,
  estoque_min INTEGER,
  estoque_max INTEGER
);

CREATE TABLE item_pedido (
  id          BIGSERIAL  PRIMARY KEY,
  produto_id  BIGINT     REFERENCES produto(id),
  quantidade  INTEGER,
  orcamento_id BIGINT    NULL,
  pedido_id    BIGINT    NULL
);

CREATE TYPE localidade AS ENUM ('RS','SP','PB');

CREATE TABLE orcamento (
  id              BIGSERIAL   PRIMARY KEY,
  custo_itens     NUMERIC,
  imposto         NUMERIC,
  desconto        NUMERIC,
  custo_consumidor NUMERIC,
  efetivado       BOOLEAN,
  localidade      localidade,
  data            TIMESTAMPTZ
);

CREATE TABLE pedido (
  id       BIGSERIAL   PRIMARY KEY,
  local    localidade,
  data     TIMESTAMPTZ
);

-- 1) Produtos
INSERT INTO produto (descricao, preco_unitario) VALUES
  ('Caneta', 2.0),
  ('Computador', 2000.00),
  ('Mochila', 120.00),
  ('Lapiseira', 5.00),
  ('Teclado', 150.0),
  ('Mosue', 50.0),
  ('Arroz*', 0.50),
  ('Carne*', 55.0);

-- 2) Estoque
-- supondo que produto.id gerado em ordem 1,2,3,4
INSERT INTO item_de_estoque (produto_id, quantidade, estoque_min, estoque_max) VALUES
  (1,  0, 0, 500),
  (2,  150, 30, 300),
  (3,   40, 10, 100),
  (4,  100, 20, 200),
  (5,  200, 50, 500),
  (6,   50, 10, 100),
  (7,   200, 5, 520),
  (8,   10, 2, 210);

-- 3) Pedidos (sem orçamento)
-- cria um pedido com dois itens
INSERT INTO pedido (local, data) VALUES
  ('SP', NOW()),
  ('RS', NOW()),
  ('PB', NOW());

-- 4) Orçamentos
INSERT INTO orcamento (custo_itens, imposto, desconto, custo_consumidor, efetivado, localidade, data) VALUES
  ( 900.0,
    243.0,
    0.0,
    1143.0,
    false,
    'SP',
    CURRENT_TIMESTAMP
  ),
  (
    4400.0,
    1090.0,
    0.0,
    5490.0,
    true,
    'RS',
    CURRENT_TIMESTAMP
  ),
  (
    1307.0,
    375.4,
    65.35000000000001,
    1617.0500000000002,
    false,
    'PB',
    CURRENT_TIMESTAMP
  );

INSERT INTO item_pedido (produto_id, quantidade, pedido_id, orcamento_id) VALUES
  (5,  5, 1, 1),
  (6,  3, 1, 1),
  (2,  1, 2, 2),
  (3, 20, 2, 2),
  (3,  4, 3, 3),
  (4,  2, 3, 3),
  (5,  1, 3, 3),
  (6, 10, 3, 3),
  (7,  4, 3, 3),
  (8,  3, 3, 3);