create table if not exists transactions
(
    id             SERIAL PRIMARY KEY,
    transaction_id BIGINT NOT NULL,
    amount         DOUBLE PRECISION NOT NULL,
    type           VARCHAR(100) NOT NULL,
    parent_id      BIGINT
);
