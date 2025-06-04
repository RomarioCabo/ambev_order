CREATE TABLE IF NOT EXISTS pedidos
(
    id_pedido  UUID                     NOT NULL,
    total      NUMERIC(10, 2)           NOT NULL,
    status     VARCHAR(255)             NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_id_pedido PRIMARY KEY (id_pedido)
);