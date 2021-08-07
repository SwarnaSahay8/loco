create table if not exists transactions
(
    id             SERIAL PRIMARY KEY,
    transaction_id BIGINT NOT NULL,
    amount         DOUBLE PRECISION,
    type           VARCHAR(100),
    parent_id      BIGINT
);
