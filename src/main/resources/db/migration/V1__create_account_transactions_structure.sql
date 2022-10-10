CREATE SEQUENCE accounts_seq;

CREATE TABLE accounts(
    id BIGINT NOT NULL DEFAULT NEXTVAL('accounts_seq'),
    document_number VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_date timestamp not null,
    last_modified_date timestamp,
    CONSTRAINT pk_accounts PRIMARY KEY(id)
);

CREATE SEQUENCE transactions_seq;

CREATE TABLE transactions(
    id BIGINT NOT NULL DEFAULT NEXTVAL('transactions_seq'),
    account_id BIGINT NOT NULL,
    operation_type INTEGER NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    currency VARCHAR(10) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY(id),
    CONSTRAINT fk_accounts FOREIGN KEY (account_id) REFERENCES accounts(id)
);