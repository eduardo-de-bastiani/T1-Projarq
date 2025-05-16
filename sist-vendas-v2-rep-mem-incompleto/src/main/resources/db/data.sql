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
  orcamento_id BIGINT    NULL,          -- FK em Orcamento
  pedido_id    BIGINT    NULL           -- FK em Pedido
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
  ('Caneta Azul',    1.20),
  ('Caderno 100fls', 12.50),
  ('Mochila',       120.00),
  ('Lapiseira',      5.00);

-- 2) Estoque
-- supondo que produto.id gerado em ordem 1,2,3,4
INSERT INTO item_de_estoque (produto_id, quantidade, estoque_min, estoque_max) VALUES
  (1,  0, 50, 500),
  (2,  150, 30, 300),
  (3,   40, 10, 100),
  (4,  100, 20, 200);

-- 3) Pedidos (sem orçamento)
-- cria um pedido com dois itens
INSERT INTO pedido (local, data) VALUES
  ('RS', NOW());

-- vamos supor que esse pedido gerou id = 1
INSERT INTO item_pedido (produto_id, quantidade, pedido_id) VALUES
  (1,  10, 1),
  (3,   1, 1);

-- 4) Orçamentos
INSERT INTO orcamento (custo_itens, imposto, desconto, custo_consumidor, efetivado, localidade, data) VALUES
  ( (12.50 * 2 + 5.00 * 5),  -- custo_itens
    (12.50 * 2 + 5.00 * 5) * 0.12,  -- imposto 12%
    (12.50 * 2 + 5.00 * 5) * 0.05,  -- desconto 5%
    ( (12.50 * 2 + 5.00 * 5) * 1.12 ) - ( (12.50 * 2 + 5.00 * 5) * 0.05 ), -- consumidor
    false,
    'SP',
    CURRENT_TIMESTAMP
);

-- supondo orcamento.id = 1
INSERT INTO item_pedido (produto_id, quantidade, orcamento_id) VALUES
  (2, 2, 1),
  (4, 5, 1);